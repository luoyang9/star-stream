package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.shooter.audio.AudioPlayer;
import xyz.charliezhang.shooter.entity.enemies.Enemy;
import xyz.charliezhang.shooter.entity.enemies.EnemyLaser;
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;

public class EntityManager 
{
	private final Array<Enemy> enemies = new Array<Enemy>();
	private final Array<Laser> lasers = new Array<Laser>();
	private final Array<EnemyLaser> enemyLasers = new Array<EnemyLaser>();
	private final Array<PowerUp> powerups = new Array<PowerUp>();
	private Player player;
	private OrthographicCamera camera;
	private boolean playerSpawned;
	
	public EntityManager(OrthographicCamera camera)
	{
		this.camera = camera;

		player = new Player(this, camera);
		player.setControllable(false);
		player.setPosition(200 - player.getSprite().getWidth() / 2, -500);
		player.setDirection(0 , 3);

		AudioPlayer.load("explosion.wav", "explosion", 1);
		playerSpawned = true;
	}
	
	public void update()
	{
		//new player move into place
		if(player.getPosition().y > 300 && playerSpawned)
		{
			playerSpawned = false;
			player.setDirection(0, 0);
			player.setControllable(true);
		}

		//check death
		if(player.getHealth() <= 0)
		{
			player.modifyLives(-1);
			if(player.getLives() <= 0)
			{
				AudioPlayer.stop("level1-1", 2);
				//game over
			}
			else //moar lives
			{
				player.setPosition(230,  -400);
				player.setDirection(0, 4);
				player.modifyHealth(player.getMaxHealth());
				player.setControllable(false);
				playerSpawned = true;
			}
		}
		
		//update entities
		player.update();
		for(Enemy e : enemies)
		{
			e.update();
		}
		for(Laser l : lasers)
		{
			l.update();
		}
		for(EnemyLaser el : enemyLasers)
		{
			el.update();
		}
		for(PowerUp p : powerups)
		{
			p.update();
		}

		//remove lasers
		for(Laser l : lasers)
		{
			if(l.checkEnd())
			{
				l.dispose();
				lasers.removeValue(l, false);
			}
		}
		//remove enemy lasers
		for(EnemyLaser el : enemyLasers)
		{
			if(el.checkEnd())
			{
				el.dispose();
				enemyLasers.removeValue(el, false);
			}
		}
		//remove enemies
		for(Enemy e : enemies) {
			if (e.getHealth() <= 0) {
				e.dispose();
				enemies.removeValue(e, false);
				AudioPlayer.play("explosion", 1);
				if (MathUtils.random() <= 0.1) {
					AttackPowerUp a = new AttackPowerUp();
					a.setPosition(e.getPosition().x, e.getPosition().y);
					a.setDirection(2, 2);
					spawnPowerUp(a);
				}
			}
		}
		
		//check collisions
		checkCollisions();
	}
	
	public void render(SpriteBatch sb)
	{
		//render enemies
		for(Enemy e : enemies)
		{
			e.render(sb);
		}

		for(PowerUp p : powerups)
		{
			p.render(sb);
		}

		//render player
		player.render(sb);

		//render enemy lasers
		for(EnemyLaser e : enemyLasers)
		{
			e.render(sb);
		}
		//render lasers
		for(Laser e : lasers)
		{
			e.render(sb);
		}
		
	}
	
	private void checkCollisions()
	{
		for(Enemy e : enemies)
		{
			//check laser-enemy collision
			for(Laser m : lasers)
			{
				if(e.getBounds().overlaps(m.getBounds()))
				{
					e.modifyHealth(-player.getDamage());
					lasers.removeValue(m, false);
				}
			}

			//check enemy-player collision
			if(player.isControllable())
			{
				if(e.getBounds().overlaps(player.getBounds()))
				{
					if(!player.isFlinching())
					{
						player.modifyHealth(-e.getDamage());
						player.setFlinching(true);

					}
				}
			}
		}
		if(player.isControllable())
		{
			for(EnemyLaser el : enemyLasers) //check enemy laser-player collision
			{
				//check player-enemy laser collision
				if(player.getBounds().overlaps(el.getBounds()))
				{
					if(!player.isFlinching())
					{
						player.modifyHealth(-el.getEnemy().getDamage());
						player.setFlinching(true);
						enemyLasers.removeValue(el, false);
					}
				}
			}
			for(PowerUp ap : powerups) //check player-powerup collision
			{
				if(player.getBounds().overlaps(ap.getBounds()))
				{
					player.activatePowerUp(ap);
					powerups.removeValue(ap, false);
				}
			}
		}
	}

	public void spawnEnemy(Enemy enemy) {enemies.add(enemy);}
	public void spawnLaser(Laser l) {lasers.add(l);}
	public void spawnEnemyLaser(EnemyLaser el) {enemyLasers.add(el);}
	public void spawnPowerUp(PowerUp p) {powerups.add(p);}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	private Array<Laser> getLasers() {
		return lasers;
	}
	private Array<PowerUp> getPowerUps()
	{
		return powerups;
	}
	private Array<EnemyLaser> getEnemyLasers() {
		return enemyLasers;
	}
	
	public Player getPlayer() {return player;}
}
