package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.TextureManager;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Icarus extends Enemy
{
	private int stop;
	public Icarus(EntityManager manager, int stop) {
		super();
		
		textureAtlas = new TextureAtlas(Gdx.files.internal("icarus.atlas"));
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(60, 60);
		
		health = maxHealth = 100;
		damage = 2;
		this.manager = manager;
		this.stop = stop;
	}

	@Override
	public void update() {
		if(sprite.getY() <= MainGame.HEIGHT - stop && direction.y < 0)
		{
			modifyDirection(0, 0.05f);
		}
		
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);

		if(sprite.getY() < MainGame.HEIGHT)
		{
			if(System.currentTimeMillis() - lastFire >= 2000)
			{
				EnemyLaser o1 = new EnemyLaser(this, 1);
				EnemyLaser o2 = new EnemyLaser(this, 1);
				EnemyLaser o3 = new EnemyLaser(this, 1);
				EnemyLaser o4 = new EnemyLaser(this, 1);
				EnemyLaser g1 = new EnemyLaser(this, 2);
				EnemyLaser g2 = new EnemyLaser(this, 2);
				EnemyLaser g3 = new EnemyLaser(this, 2);
				EnemyLaser g4 = new EnemyLaser(this, 2);
				EnemyLaser g5 = new EnemyLaser(this, 2);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				o1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				o2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				o3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g4.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				o4.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g5.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				
				g1.setDirection(0,  -4.8f);
				manager.spawnEnemyLaser(g1);
				o1.setDirection(0.5f, -4.7f);
				manager.spawnEnemyLaser(o1);
				o2.setDirection(-0.5f, -4.7f);
				manager.spawnEnemyLaser(o2);
				g2.setDirection(1, -4.3f);
				manager.spawnEnemyLaser(g2);
				g3.setDirection(-1, -4.3f);
				manager.spawnEnemyLaser(g3);
				o3.setDirection(1.5f, -3.5f);
				manager.spawnEnemyLaser(o3);
				o4.setDirection(-1.5f, -3.5f);
				manager.spawnEnemyLaser(o4);
				g4.setDirection(2, -2.2f);
				manager.spawnEnemyLaser(g4);
				g5.setDirection(-2, -2.2f);
				manager.spawnEnemyLaser(g5);
				lastFire = System.currentTimeMillis();
			}
		}
	}

	@Override 
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		sb.draw(TextureManager.HEALTH, sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
		sb.draw(TextureManager.HEALTHFILL, sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
		super.render(sb);
	}
	
}
