package xyz.charliezhang.starstream.entity.player;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.GameData;
import xyz.charliezhang.starstream.GameInput;
import xyz.charliezhang.starstream.entity.Entity;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.entity.Explosion;
import xyz.charliezhang.starstream.entity.powerup.AttackPowerUp;
import xyz.charliezhang.starstream.entity.powerup.MissilePowerUp;
import xyz.charliezhang.starstream.entity.powerup.PowerUp;
import xyz.charliezhang.starstream.entity.powerup.ShieldPowerUp;
import xyz.charliezhang.starstream.music.MusicPlayer;
import xyz.charliezhang.starstream.shop.Upgrade;
import xyz.charliezhang.starstream.shop.UpgradeManager;

import static xyz.charliezhang.starstream.Config.*;
import static xyz.charliezhang.starstream.entity.powerup.PowerUp.PowerUps.*;

public class Player extends Entity
{
	//initial touch
	private Vector2 iniTouch = new Vector2();
	private Vector2 syncPos = new Vector2();
	GameInput playerInput;
	private boolean justTouched;

	//directions
	private float xdir, ydir;

	//player assets
	Sound shootSound;
	private Sound shieldUpSound;
	private Sound shieldDownSound;

	//player data
	protected int health, maxHealth;
	private int numLives, maxLives;
	protected int damage;
	int attLevel;
	private boolean flinching;
	private boolean controllable;
	private boolean justControllable;
	private boolean justSpawned;
	private boolean dead;

	//powerup tasks
	private Task missileTask;
	private boolean missileOn;
	private boolean shieldOn;
	private boolean invincibleOn;
	private long invincibleDuration;
	private long invincibleTimer;
	boolean superAttOn;
	long superAttTimer;
	long superAttDuration;

	private Sprite shield;

	//timers
	private long flinchTimer;
	long shootDelay, lastFire;
	
	//manager and camera
	protected final EntityManager manager;
	private final OrthographicCamera camera;

	Player(EntityManager manager) {
		//manager
		this.manager = manager;
		this.camera = (OrthographicCamera)manager.getViewport().getCamera();

		shootSound = Assets.manager.get(SHOOT_SOUND_PATH, Sound.class);
		shieldUpSound = Assets.manager.get(SHIELD_UP_SOUND_PATH, Sound.class);
		shieldDownSound = Assets.manager.get(SHIELD_DOWN_SOUND_PATH, Sound.class);

		attLevel = PLAYER_ATT_LEVEL;
		numLives = maxLives = PLAYER_MAX_LIVES;
		flinching = PLAYER_INITIAL_FLINCHING;
		controllable = PLAYER_INITIAL_CONTROLLABLE;
		justControllable = PLAYER_INITIAL_JUST_CONTROLLABLE;
		justSpawned = PLAYER_INITIAL_JUST_SPAWNED;
		justTouched = PLAYER_INITIAL_JUST_TOUCHED;
		setDirection(PLAYER_INITIAL_DIRECTION.x, PLAYER_INITIAL_DIRECTION.y);
		setPosition(manager.getViewport().getWorldWidth()/2 - sprite.getWidth() / 2, PLAYER_INITIAL_Y);
		syncPos.x = getPosition().x;
		syncPos.y = getPosition().y;

		initializePowerups();

		playerInput = new GameInput(this);
	}

	private void initializePowerups()
	{
		missileTask = new Timer.Task() {
			@Override
			public void run() {
				Missile m1 = manager.getMissilePool().obtain();
				Missile m2 = manager.getMissilePool().obtain();
				m1.init(manager, MIS_INITIAL_ACCEL);
				m1.setDirection(MIS_INITIAL_DIRECTION.x, MIS_INITIAL_DIRECTION.y);
				m1.setPosition(sprite.getX(), sprite.getY() + sprite.getHeight());
				m2.init(manager, MIS_INITIAL_ACCEL);
				m2.setDirection(MIS_INITIAL_DIRECTION.x, MIS_INITIAL_DIRECTION.y);
				m2.setPosition(sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());
				manager.spawnLaser(m1);
				manager.spawnLaser(m2);
			}
		};
		missileOn = MIS_INITIAL_ON;

		invincibleOn = INVINCIBLE_INITIAL_ON;
		invincibleDuration = INVINCIBLE_DURATION;

		shieldOn = SHIELD_INITIAL_ON;
		shield = new Sprite(Assets.manager.get(SHIELD_PATH, Texture.class));
		shield.setSize(100, 100);

		superAttOn = ATT_INITIAL_ON;
		superAttDuration = ATT_DURATION;
	}

	public void applyUpgrades() {
		Array<Upgrade> upgrades = UpgradeManager.getPlayerUpgrades();
		for(Upgrade u : upgrades) {
			if(u.getName().equals("health")) {
				this.health += u.getValue();
				this.maxHealth += u.getValue();
			} else if(u.getName().equals("damage")) {
				this.damage += u.getValue();
			}
		}
	}

	@Override
	public void update()
	{
		if(justSpawned && getPosition().y > 200)
		{
			setDirection(0, 0);
			controllable = true;
			justControllable = true;
			justSpawned = false;
			setFlinching(true);
		}


		//if controllable
		if(controllable)
		{
			//movement
			move();
			
			//fire lasers
			shoot();

			//check powerups
			checkPowerUps();

			//check death
			checkDeath();
		}

		//update pos
		super.update();

		//update shield
		shield.setPosition(sprite.getX()-(shield.getWidth()-sprite.getWidth())/2, sprite.getY()-(shield.getHeight()-sprite.getHeight())/2);
	}

	private void checkPowerUps()
	{
		long elapsed;
		if(!missileTask.isScheduled() && missileOn) {
			missileOn = false;
			manager.deactivatePowerUp(MISSILE);
		}
		if(superAttOn)
		{
			elapsed = (System.nanoTime() - superAttTimer) / 1000000;
			if(elapsed > superAttDuration) {
				superAttOn = false;
				manager.deactivatePowerUp(ATTACK);
			}
		}
		if(invincibleOn)
		{
			System.out.println("invincible");
			elapsed = (System.nanoTime() - invincibleTimer) / 1000000;
			if(elapsed > invincibleDuration) invincibleOn = false;
		}
	}

	private void checkDeath()
	{
		if(health <= 0)
		{
			numLives--;
			Explosion explosion = manager.getExplosionPool().obtain();
			explosion.init(2);
			explosion.setPosition(getPosition().x + getSprite().getWidth()/2, getPosition().y + getSprite().getHeight()/2);
			manager.spawnExplosion(explosion);
			Assets.manager.get(EXPLOSION_SOUND_PATH, Sound.class).play(MusicPlayer.VOLUME); //explosion
			if(numLives <= 0)
			{
				dead = true;
				controllable = false;
				//game over
			}
			else //moar lives
			{
				setPosition(manager.getViewport().getWorldWidth()/2 - sprite.getWidth() / 2, -500);
				setDirection(0, 8);
				health = maxHealth;
				attLevel = 1;

				//cancel powerups
				missileTask.cancel();
				shieldOn = false;

				controllable = false;
				justSpawned = true;
			}
		}
	}

	private void move()
	{
		xdir = 0;
		ydir = 0;

		Vector2 touch = new Vector2(playerInput.getX(), -playerInput.getY());
		Vector3 unprojected = camera.unproject(new Vector3(touch.x, -touch.y, 0));

		//check if just touched
		if(justTouched || justControllable)
		{
			iniTouch.set(unprojected.x, unprojected.y);
			syncPos.set(iniTouch.x - getPosition().x, iniTouch.y - getPosition().y);
			justControllable = false;
			justTouched = false;
		}
		else if(playerInput.isTouching())
		{
			//calculate drag, set direction to drag
			Vector2 newTouch = new Vector2(unprojected.x, unprojected.y);
			Vector2 drag = newTouch.cpy().sub(iniTouch);
			xdir = drag.x;
			ydir = drag.y;
			iniTouch = newTouch;

			//translate background
			manager.getBackground().translate(drag.x);

			//sync player with finger
			if(Math.abs(xdir) < 0.01f && Math.abs(ydir) < 0.01f)
			{
				xdir = (newTouch.x - getPosition().x) - syncPos.x;
				ydir = (newTouch.y - getPosition().y) - syncPos.y;
			}
		}

		//limit movements to screen
		if(sprite.getX() + xdir >= manager.getViewport().getWorldWidth()-sprite.getWidth() || sprite.getX() + xdir <= 0)
			xdir = 0;
		else if(sprite.getY() + ydir > manager.getViewport().getWorldHeight()-sprite.getHeight() || sprite.getY() + ydir < 0)
			ydir = 0;

		//set direction
		direction.set(xdir, ydir);
	}

	protected void shoot(){}

	@Override
	public void render(SpriteBatch sb)
	{
        //if flinching
		if(flinching) {
			long elapsed = System.currentTimeMillis() - flinchTimer;
			if (elapsed % 200 <= 100) //every 0.5 seconds, blink
			{
				return;
			}
			if (elapsed > 1500) flinching = false; //if 1 sec passed
		}
		
		//render player
		super.render(sb);

		if(shieldOn) shield.draw(sb);
	}
	
	public void setFlinching(boolean b) 
	{
		flinching = b;
		if(b) flinchTimer = System.currentTimeMillis(); //if flinching, start timer
	}
	public void setControllable(boolean b){controllable = justControllable= b;} //set controllable and justControllable to true
	public void setDamage(int d){damage = d;} //set damage
	public void activatePowerUp(PowerUp powerUp)
	{
		manager.activatePowerUp(powerUp.getType());
		if(powerUp instanceof AttackPowerUp) activateAttackPowerUp();
		if(powerUp instanceof MissilePowerUp) activateMissilePowerUp((MissilePowerUp)powerUp);
		if(powerUp instanceof ShieldPowerUp) activateShieldPowerUp();
	}
	private void activateAttackPowerUp()
	{
		if (attLevel < 4) //if att lvl < 3
			attLevel++; //increase att lvl
		else
		{
			superAttOn = true;
			superAttTimer = System.nanoTime();
		}
	}

	public void activateInvinciblePowerUp()
	{
		invincibleOn = true;
		invincibleTimer = System.nanoTime();
	}

	private void activateMissilePowerUp(MissilePowerUp powerUp)
	{
		missileOn = true;
		missileTask.cancel();
		Timer.schedule(missileTask, powerUp.getDelay(), powerUp.getInterval(), powerUp.getNumRepeats());
	}

	private void activateShieldPowerUp() {
		shieldUpSound.play(MusicPlayer.VOLUME);
		shieldOn = true;
	}

	public void modifyHealth(int h){ health += h; } //modify health
	public void modifyLives(int l) //modify Lives
	{
		numLives += l;
		if(numLives > maxLives) numLives = maxLives; //can't go over max lives
	}
	public int getHealth() { return health; } //get health
	public int getMaxHealth() { return maxHealth; } //get max health
	public int getDamage() {return damage;} //get damage
	public int getMaxLives() {return maxLives;} //get max lives
	public int getLives() {return numLives;} //get lives
	public InputProcessor getInputProcessor() {return playerInput;}
	public boolean isInvincible() {return invincibleOn;}
	public boolean isFlinching() {return flinching; } //is flinching?
	public boolean isControllable() {return controllable;} //is controllable?
	public boolean isDead() {return dead;} //is dead?
	public boolean isShieldOn() {return shieldOn;}
	public void removeShield() {
		manager.deactivatePowerUp(SHIELD);
		shieldOn = false;
		shieldDownSound.play(MusicPlayer.VOLUME);
	}

	public void setJustTouched(boolean b) {justTouched = b;}

	@Override
	public Rectangle getBounds()
	{
		Rectangle bounds = new Rectangle();
		bounds.x = sprite.getX() + sprite.getWidth() * 0.2f;
		bounds.y = sprite.getY() + sprite.getHeight() * 0.2f;
		bounds.width = sprite.getWidth() * 0.6f;
		bounds.height = sprite.getHeight() * 0.6f;
		return bounds;
	}
}
