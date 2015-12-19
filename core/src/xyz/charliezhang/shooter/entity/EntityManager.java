package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.background.Background;
import xyz.charliezhang.shooter.entity.enemies.Enemy;
import xyz.charliezhang.shooter.entity.enemies.EnemyLaser;
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.MissilePowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;

public class EntityManager 
{
	private final Array<Enemy> enemies = new Array<Enemy>();
	private final Array<PlayerLaser> lasers = new Array<PlayerLaser>();
	private final Array<EnemyLaser> enemyLasers = new Array<EnemyLaser>();
	private final Array<PowerUp> powerups = new Array<PowerUp>();
	private final Array<Explosion> explosions = new Array<Explosion>();
	private Player player;
	private OrthographicCamera camera;
	private Background background;

	private MainGame game;
	
	public EntityManager(OrthographicCamera camera, MainGame game, Background background)
	{
		this.game = game;
		this.camera = camera;
		this.background = background;

		player = new Player(this, camera);
	}
	
	public void update()
	{
		//update entities
		player.update();
		for(Enemy e : enemies)
		{
			e.update();
		}
		for(PlayerLaser l : lasers)
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
		for(Explosion e : explosions)
		{
			e.update();
			if(e.isDone()) explosions.removeValue(e, false);
		}


		//remove lasers
		for(PlayerLaser l : lasers)
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
				game.manager.get("data/sounds/explosion.wav", Sound.class).play(); //explosion
				if(MathUtils.random() <= 1) {
					MissilePowerUp a = new MissilePowerUp(game);
					a.setPosition(e.getPosition().x, e.getPosition().y);
					a.setDirection(-2, -2);
					spawnPowerUp(a);
				}
				if (MathUtils.random() <= 1) {
					AttackPowerUp a = new AttackPowerUp(game);
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
		for(PlayerLaser e : lasers)
		{
			e.render(sb);
		}

		for(Explosion e : explosions)
		{
			e.render(sb);
		}
		
	}
	
	private void checkCollisions()
	{
		for(Enemy e : enemies)
		{
			//check laser-enemy collision
			for(PlayerLaser m : lasers)
			{
				if(e.getBounds().overlaps(m.getBounds()))
				{
					Explosion exp = new Explosion(game);
					exp.setPosition(m.getPosition().x, m.getPosition().y);
					spawnExplosion(exp);
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
	public void spawnLaser(PlayerLaser l) {lasers.add(l);}
	public void spawnEnemyLaser(EnemyLaser el) {enemyLasers.add(el);}
	public void spawnPowerUp(PowerUp p) {powerups.add(p);}
	public void spawnExplosion(Explosion e) {explosions.add(e);}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}

	private Array<PlayerLaser> getLasers() {
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
	public MainGame getGame() {return game;}
	public Background getBackground() {return background;}
}

