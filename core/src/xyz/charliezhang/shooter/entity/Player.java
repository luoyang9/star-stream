package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.sun.org.apache.regexp.internal.RE;
import org.w3c.dom.css.Rect;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.MissilePowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;
import xyz.charliezhang.shooter.entity.powerup.ShieldPowerUp;

public class Player extends Entity
{
	//initial touch
	private Vector2 iniTouch = new Vector2();
	private Vector2 syncPos = new Vector2();

	//directions
	float xdir, ydir;

	//player assets
	private Sound shootSound;

	//player data
	private int health, maxHealth;
	private int numLives, maxLives;
	private int damage;
	private int attLevel;
	private boolean flinching;
	private boolean controllable;
	private boolean justControllable;
	private boolean justSpawned;
	private boolean dead;

	//powerup tasks
	private Task missileTask;
	private boolean shieldOn;
	private boolean superAttOn;
	private long superAttTimer;
	private long superAttDuration;

	private Sprite shield;

	//timers
	private long lastFire, flinchTimer, shootDelay;
	
	//manager and camera
	private final EntityManager manager;
	private final OrthographicCamera camera;
	
	//TODO: different planes with different shooting patterns
	public Player(EntityManager manager, OrthographicCamera camera) {
		//manager
		this.manager = manager;
		this.camera = camera;
		
		//set texture atlas and animation to player
		textureAtlas = manager.getGame().manager.get("data/textures/playerspritesheet.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());

		shootSound = manager.getGame().manager.get("data/sounds/playershoot.wav", Sound.class);
		
		//set sprite size
		sprite.setSize(75, 50);
		
		//set player starting data
		shootDelay = 100;
		attLevel = 1;
		numLives = maxLives = 3;
		flinching = false;
		controllable = false;
		justControllable = false;
		justSpawned = true;
		setDirection(0, 4);
		setPosition(MainGame.WIDTH/2 - sprite.getWidth() / 2, -500);
		syncPos.x = getPosition().x;
		syncPos.y = getPosition().y;

		initializePowerups();

		//read player stats
		health = maxHealth = 10;
		damage = 1;
	}

	private void initializePowerups()
	{
		missileTask = new Timer.Task() {
			@Override
			public void run() {
				Missile m1 = new Missile(manager.getGame());
				Missile m2 = new Missile(manager.getGame());
				m1.setPosition(sprite.getX(), sprite.getY() + sprite.getHeight());
				m2.setPosition(sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());
				manager.spawnLaser(m1);
				manager.spawnLaser(m2);
			}
		};

		shieldOn = false;
		shield = new Sprite(manager.getGame().manager.get("data/textures/shield.png", Texture.class));
		shield.setSize(100, 100);

		superAttOn = false;
		superAttDuration = 3000;
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
		}


		//if controllable
		if(controllable)
		{
			//movement
			move();
			
			//fire lasers
			shoot();

			//check death
			checkDeath();
		}
		
		//add direction to position
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);

		//update shield
		shield.setPosition(sprite.getX()-(shield.getWidth()-sprite.getWidth())/2, sprite.getY()-(shield.getHeight()-sprite.getHeight())/2);
	}

	private void checkDeath()
	{
		if(health <= 0)
		{
			numLives--;
			Explosion explosion = new Explosion(manager.getGame(), 2);
			explosion.setPosition(getPosition().x + getSprite().getWidth()/2, getPosition().y + getSprite().getHeight()/2);
			manager.spawnExplosion(explosion);
			manager.getGame().manager.get("data/sounds/explosion.wav", Sound.class).play(); //explosion
			if(numLives <= 0)
			{
				dead = true;
				controllable = false;
				//game over
			}
			else //moar lives
			{
				setPosition(MainGame.WIDTH/2 - sprite.getWidth() / 2, -500);
				setDirection(0, 4);
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

		Vector2 touch = new Vector2(Gdx.input.getX(), -Gdx.input.getY());
		Vector3 unprojected = camera.unproject(new Vector3(touch.x, -touch.y, 0));

		//check if just touched
		if(Gdx.input.justTouched() || justControllable)
		{
			iniTouch.set(unprojected.x, unprojected.y);
			syncPos.set(iniTouch.x - getPosition().x, iniTouch.y - getPosition().y);
			justControllable = false;
		}
		else if(Gdx.input.isTouched())
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
		if(sprite.getX() + xdir >= MainGame.WIDTH-25 || sprite.getX() + xdir <= 0)
			xdir = 0;
		else if(sprite.getY() + ydir > MainGame.HEIGHT-50 || sprite.getY() + ydir < 0)
			ydir = 0;

		//set direction
		direction.set(xdir, ydir);
	}

	private void shoot()
	{
		if(Gdx.input.isTouched()) //if touching
		{
			if(System.currentTimeMillis() - lastFire >= shootDelay) //if its time to shoot
			{
				shootSound.play(); //play pew
				Laser l1 = new Laser(manager.getGame());
				Laser l2 = new Laser(manager.getGame());
				Laser l3 = new Laser(manager.getGame());
				Laser l4 = new Laser(manager.getGame());
				Laser l5 = new Laser(manager.getGame());
				Laser l6 = new Laser(manager.getGame());
				Laser l7 = new Laser(manager.getGame());


				if(attLevel == 2) {
					l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()*1.5f, sprite.getY() + sprite.getHeight());
					l1.setDirection(0, 15);
					manager.spawnLaser(l1);
					l2.setPosition(sprite.getX() + sprite.getWidth() / 2 + l1.getSprite().getWidth()*0.5f, sprite.getY() + sprite.getHeight());
					l2.setDirection(0, 15);
					manager.spawnLaser(l2);
				}
				else
				{
					l1.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
					l1.setDirection(0, 15);
					manager.spawnLaser(l1);
				}

				if(attLevel >= 3) {
					l2.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()*2.5f, sprite.getY() + sprite.getHeight());
					l2.setDirection(0, 15);
					manager.spawnLaser(l2);
					l3.setPosition(sprite.getX() + sprite.getWidth() / 2 + l1.getSprite().getWidth()*1.5f, sprite.getY() + sprite.getHeight());
					l3.setDirection(0, 15);
					manager.spawnLaser(l3);
				}

				if(attLevel >= 4) {
					l4.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
					l4.setDirection(-2f, 15);
					manager.spawnLaser(l4);
					l5.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
					l5.setDirection(2f, 15);
					manager.spawnLaser(l5);
				}

				if(superAttOn)
				{
					long elapsed = (System.nanoTime() - superAttTimer) / 1000000;
					if(elapsed > superAttDuration) superAttOn = false;
					l6.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
					l6.setDirection(-4f, 15);
					manager.spawnLaser(l6);
					l7.setPosition(sprite.getX() + sprite.getWidth() / 2 - l1.getSprite().getWidth()/2, sprite.getY() + sprite.getHeight());
					l7.setDirection(4f, 15);
					manager.spawnLaser(l7);
				}

				lastFire = System.currentTimeMillis(); //set new last fire
			}
		}
	}

	@Override
	public void render(SpriteBatch sb)
	{
        //if flinching
		if(flinching)
		{
			long elapsed = System.currentTimeMillis() - flinchTimer;
			if(elapsed % 200 <= 100) //every 0.5 seconds, blink
			{
				return;
			}
			if(elapsed > 1500) flinching = false; //if 1 sec passed
		}
		
		//set sprite to current animation region
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		
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
		if(powerUp instanceof AttackPowerUp) activateAttackPowerUp((AttackPowerUp)powerUp);
		if(powerUp instanceof MissilePowerUp) activateMissilePowerUp((MissilePowerUp)powerUp);
		if(powerUp instanceof ShieldPowerUp) activateShieldPowerUp((ShieldPowerUp) powerUp);
	}
	public void activateAttackPowerUp(AttackPowerUp powerUp)
	{
		if (attLevel < 4) //if att lvl < 3
			attLevel++; //increase att lvl
		else
		{
			superAttOn = true;
			superAttTimer = System.nanoTime();
		}
	}

	public void activateMissilePowerUp(MissilePowerUp powerUp)
	{
		missileTask.cancel();
		Timer.schedule(missileTask, powerUp.getDelay(), powerUp.getInterval(), powerUp.getNumRepeats());
	}

	public void activateShieldPowerUp(ShieldPowerUp powerUp) {
		shieldOn = true;
	}

	public void modifyHealth(int h) {health += h;} //modify health
	public void modifyLives(int l) //modify Lives
	{
		numLives += l;
		if(numLives > maxLives) numLives = maxLives; //can't go over max lives
	}
	public int getHealth() { return health; } //get health
	public int getMaxHealth() { return maxHealth; } //get max health
	public int getDamage() {return damage;} //get damage
	public int getLives() {return numLives;} //get lives
	public int getAttLevel() {return attLevel;}
	public void setAttLevel(int l) {attLevel = 1;}
	public boolean isFlinching() {return flinching; } //is flinching?
	public boolean isControllable() {return controllable;} //is controllable?
	public boolean isDead() {return dead;} //is dead?
	public Timer.Task getMissileTask() {return missileTask;}
	public boolean isShieldOn() {return shieldOn;}
	public boolean isSuperAttOn() {return superAttOn;}
	public void setShield(boolean b) {shieldOn = b;}

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
