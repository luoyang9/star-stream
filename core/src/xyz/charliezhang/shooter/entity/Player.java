package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.MissilePowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;

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
	private Array<Timer.Task> powerupTasks;

	//timers
	private long lastFire, flinchTimer;
	
	//manager and camera
	private final EntityManager manager;
	private final OrthographicCamera camera;
	

	public Player(EntityManager manager, OrthographicCamera camera) {
		//manager
		this.manager = manager;
		this.camera = camera;
		
		//set texture atlas and animation to player
		textureAtlas = manager.getGame().manager.get("data/textures/playerspritesheet.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());

		shootSound = manager.getGame().manager.get("data/sounds/playershoot.wav", Sound.class);
		
		//set sprite size
		sprite.setSize(25, 50);
		
		//set player starting data
		attLevel = 1;
		numLives = maxLives = 3;
		flinching = false;
		controllable = false;
		justControllable = false;
		justSpawned = true;
		setDirection(0, 3);
		setPosition(MainGame.WIDTH/2 - sprite.getWidth() / 2, -500);
		syncPos.x = getPosition().x;
		syncPos.y = getPosition().y;

				powerupTasks = new Array<Timer.Task>();
		
		//read player stats
		health = maxHealth = 10;
		damage = 1;
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
			//check death
			if(health <= 0)
			{
				numLives--;
				if(numLives <= 0)
				{
					dead = true;
					//game over
				}
				else //moar lives
				{
					setPosition(MainGame.WIDTH/2 - sprite.getWidth() / 2, -500);
					setDirection(0, 3);
					health = maxHealth;
					attLevel = 1;
					for(Timer.Task task : powerupTasks)
					{
						task.cancel();
					}
					controllable = false;
					justSpawned = true;
					return;
				}
			}


			//movement
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
			
			//fire lasers
			if(Gdx.input.isTouched()) //if touching
			{
				if(System.currentTimeMillis() - lastFire >= 200) //if its time to shoot
				{
					shootSound.play(); //play pew
					Laser l1 = new Laser(manager.getGame());
					Laser l2 = new Laser(manager.getGame());
					Laser l3 = new Laser(manager.getGame());
					switch(attLevel) //depending on att level
					{
					case 1:
							l1.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight()); 
							l1.setDirection(0, 10);
							manager.spawnLaser(l1);
							break;
					case 2:
							l1.setPosition(sprite.getX(), sprite.getY() + sprite.getHeight());
							l1.setDirection(0, 10);
							manager.spawnLaser(l1);
							l2.setPosition(sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());
							l2.setDirection(0, 10);
							manager.spawnLaser(l2);
							break;
					case 3: l1.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
							l1.setDirection(0, 10);
							manager.spawnLaser(l1);
							l2.setPosition(sprite.getX() - 2, sprite.getY() + sprite.getHeight());
							l2.setDirection(0, 10);
							manager.spawnLaser(l2);
							l3.setPosition(sprite.getX() + sprite.getWidth() + 2, sprite.getY() + sprite.getHeight());
							l3.setDirection(0, 10);
							manager.spawnLaser(l3);
							break;
					default:l1.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight());
							l1.setDirection(0, 10);
							manager.spawnLaser(l1);
					}
					lastFire = System.currentTimeMillis(); //set new last fire
				}
			}
		}
		
		//add direction to position
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
	}
	
	@Override
	public void render(SpriteBatch sb)
	{
		//draw health bar
		sb.draw(manager.getGame().manager.get("data/textures/health.png", Texture.class), 50, 50, 15*maxHealth, 10);
		//draw points of health
		for(int i = 0; i < health; i++)
		{
			sb.draw(manager.getGame().manager.get("data/textures/healthFill.png", Texture.class),50+i*15, 49, 14, 9);
		}
		
        
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
	}
	public void activateAttackPowerUp(AttackPowerUp powerUp)
	{
		if (attLevel < 3) //if att lvl < 3
			attLevel++; //increase att lvl
	}

	public void activateMissilePowerUp(MissilePowerUp powerUp)
	{
		powerupTasks.add(
			Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					Missile m1 = new Missile(manager.getGame());
					Missile m2 = new Missile(manager.getGame());
					m1.setPosition(sprite.getX(), sprite.getY() + sprite.getHeight());
					m2.setPosition(sprite.getX() + sprite.getWidth(), sprite.getY() + sprite.getHeight());
					manager.spawnLaser(m1);
					manager.spawnLaser(m2);
				}
			}, 0, 2, 6));
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

}
