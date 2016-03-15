package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.background.Background;
import xyz.charliezhang.shooter.entity.enemies.Enemy;
import xyz.charliezhang.shooter.entity.enemies.EnemyLaser;
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.MissilePowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;
import xyz.charliezhang.shooter.entity.powerup.ShieldPowerUp;
import xyz.charliezhang.shooter.music.MusicPlayer;
import xyz.charliezhang.shooter.screen.MenuScreen;
import xyz.charliezhang.shooter.screen.WinScreen;

public class EntityManager 
{
	private final Array<Enemy> enemies = new Array<Enemy>();
	private final Array<PlayerLaser> lasers = new Array<PlayerLaser>();
	private final Array<EnemyLaser> enemyLasers = new Array<EnemyLaser>();
	private final Array<PowerUp> powerups = new Array<PowerUp>();
	private final Array<Explosion> explosions = new Array<Explosion>();
	private Player player;

	private Background background;
	private HUD hud;
	private Viewport viewport;
	private int level;

	private int nextATT;
	private int currATT;
	private int nextMIS;
	private int currMIS;
	private int nextSHD;
	private int currSHD;

	private boolean deathProcedure;

	private int score;
	private long time;

	private boolean win;

	private MainGame game;
	
	public EntityManager(Viewport viewport, MainGame game, Background background, int level)
	{
		this.game = game;
		this.background = background;
		this.viewport = viewport;
		this.level = level;

		player = new Player(this);
		hud = new HUD(this);

		score = 0;
		time = System.nanoTime();
		deathProcedure = false;

		nextATT = (int) (Math.random()*2) + 3;
		nextMIS = (int) (Math.random()*2) + 4;
		nextSHD = (int) (Math.random()*2) + 2;
		currATT = 0;
		currMIS = 0;
		currSHD = 0;

		win = false;
	}

	public void updateViewport(Viewport viewport)
	{
		this.viewport = viewport;
	}
	
	public void update(float delta)
	{
		hud.update(delta);

		if(win)
		{
			if(player.getPosition().y > viewport.getWorldHeight() + 500)
			{
				game.setScreen(new WinScreen(game, score, player.getLives(), (int)((System.nanoTime() - time) / 1000000000), level));
			}
		}

		//update entities
		player.update();
		if(player.isDead() && !deathProcedure)
		{
			MusicPlayer.stop("game");
			deathProcedure = true;
			spawnExplosion(new Explosion(game, 2));
			hud.death();
		}
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
			if (e.isDead()) {
				score += e.getScore();

				Explosion exp = new Explosion(game, 2);
				exp.setPosition(e.getPosition().x+e.getSprite().getWidth()/2, e.getPosition().y+e.getSprite().getHeight()/2);
				spawnExplosion(exp);

				e.dispose();
				enemies.removeValue(e, false);
				game.manager.get("data/sounds/explosion.wav", Sound.class).play(); //explosion

				currATT++;
				currSHD++;
				currMIS++;
				if(currMIS >= nextMIS) {
					MissilePowerUp a = new MissilePowerUp(game);
					a.setPosition(e.getPosition().x, e.getPosition().y);
					a.setDirection(-2, -2);
					spawnPowerUp(a);
					currMIS = 0;
					nextMIS = (int)(Math.random()*3 + 6);
				}
				if (currATT >= nextATT) {
					AttackPowerUp a = new AttackPowerUp(game);
					a.setPosition(e.getPosition().x, e.getPosition().y);
					a.setDirection(2, 2);
					spawnPowerUp(a);
					currATT = 0;
					nextATT = (int)(Math.random()*5 + 8);

				}
				if (currSHD >= nextSHD) {
					ShieldPowerUp a = new ShieldPowerUp(game);
					a.setPosition(e.getPosition().x, e.getPosition().y);
					a.setDirection(-2, 2);
					spawnPowerUp(a);
					currSHD = 0;
					nextSHD = (int)(Math.random()*6 + 8);
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
		if(!player.isDead()) player.render(sb);

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

		hud.render(sb, game.font);
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
					//explosion
					Explosion exp = new Explosion(game, 1);
					exp.setPosition(m.getPosition().x, m.getPosition().y);
					spawnExplosion(exp);

					//do damage
					if(m instanceof Missile) e.modifyHealth(-player.getDamage()*4);
					else e.modifyHealth(-player.getDamage());

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
						if(!player.isShieldOn()) {
							player.modifyHealth(-e.getDamage());
							player.setFlinching(true);
						}
						else
						{
							player.setShield(false);
						}

					}
				}
			}
		}
		if(player.isControllable())
		{
			for(EnemyLaser el : enemyLasers) //check enemy laser-player collision
			{
				if(player.getBounds().overlaps(el.getBounds()))
				{
					if(!player.isFlinching())
					{
						enemyLasers.removeValue(el, false);
						if(!player.isShieldOn()) {
							player.modifyHealth(-el.getEnemy().getDamage());
							player.setFlinching(true);
						}
						else
						{
							player.setShield(false);
						}
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

	public void win()
	{
		player.setDirection(0, 4);
		player.setControllable(false);
		win = true;
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
	public long getScore() {return score;}
	public Viewport getViewport() {return viewport;}
}

