package xyz.charliezhang.shooter.entity;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.StatData;
import xyz.charliezhang.shooter.TextureManager;
import xyz.charliezhang.shooter.audio.AudioPlayer;
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;

public class Player extends Entity
{
	//initial touch
	private Vector2 iniTouch = new Vector2();

	//directions
	float xdir, ydir;
	
	//player data
	private int health, maxHealth;
	private int numLives, maxLives;
	private int damage;
	private int attLevel;
	private boolean flinching;
	private boolean controllable;
	private boolean justControllable;
	
	//timers
	private long lastFire, flinchTimer, powerUpTimer;
	private int powerUpDuration;
	
	//manager and camera
	private final EntityManager manager;
	private final OrthographicCamera camera;
	

	public Player(EntityManager manager, OrthographicCamera camera) {
		//manager
		this.manager = manager;
		this.camera = camera;
		
		//set texture atlas and animation to player
		textureAtlas = new TextureAtlas(Gdx.files.internal("playerspritesheet.atlas"));
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		//set sprite size
		sprite.setSize(25, 50);
		
		//set player starting data
		attLevel = 3;
		numLives = maxLives = 3;
		flinching = false;
		controllable = true;
		justControllable = false;
		
		//load laser shoot sound
		AudioPlayer.load("playershoot.wav", "pshoot", 1);
		
		//check if player is new
		try {
			StatData.checkNewPlayer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//read player stats
		StatData.readStats();
		health = maxHealth = StatData.playerHealth;
		damage = StatData.playerDamage;
	}

	@Override
	public void update()
	{
		//if controllable
		if(controllable)
		{
			xdir = 0;
			ydir = 0;
			
			//Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
			Vector2 touch = new Vector2(Gdx.input.getX(), -Gdx.input.getY());
			Vector3 unprojected = camera.unproject(new Vector3(touch.x, -touch.y, 0));
			
			//check if just touched
			if(Gdx.input.justTouched() || justControllable)
			{
				iniTouch.set(unprojected.x, unprojected.y);
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
					AudioPlayer.play("pshoot", 1); //play pew
					Laser l1 = new Laser();
					Laser l2 = new Laser();
					Laser l3 = new Laser();
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
		sb.draw(TextureManager.HEALTH, 50, 50, 15*maxHealth, 10);
		//draw points of health
		for(int i = 0; i < health; i++)
		{
			sb.draw(TextureManager.HEALTHFILL,50+i*15, 50, 13, 10);
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
		if(powerUp instanceof AttackPowerUp) {
			if (attLevel < 3) //if att lvl < 3
				attLevel++; //increase att lvl
			else //else active super special att timer
			{
				powerUpTimer = System.currentTimeMillis();
				powerUpDuration = powerUp.getDuration();
			}
		}
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
	public int getPowerUpDuration() {return powerUpDuration;} //get power up duration
	public int getLives() {return numLives;} //get lives
	public boolean isFlinching() {return flinching; } //is flinching?
	public boolean isControllable() {return controllable;} //is controllable?

}
