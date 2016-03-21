package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Skullinator extends Enemy
{
	private boolean entered, lastSpecial2Active;
	private long lastSpecial1, lastSpecial2, lastSpecial2Duration;
	public Skullinator(EntityManager manager) {
		super();

		textureAtlas = Assets.manager.get("data/textures/skullinator.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(120, 120);
		
		health = maxHealth = 600;
		damage = 2;
		score = 300;
		this.manager = manager;
		entered = false;
		lastSpecial1 = lastSpecial2 = System.currentTimeMillis();
	}

	@Override
	public void update() {
		super.update();

		if(sprite.getY() <= manager.getViewport().getWorldHeight() - stop && !entered)
		{
			direction.set(2, 0);
			entered = true;
		}
		
		if(entered)
		{
			if(sprite.getX() <= 0)
			{
				direction.set(2, 0);
			}
			else if(sprite.getX() >= manager.getViewport().getWorldWidth() - sprite.getWidth())
			{
				direction.set(-2, 0);
			}
		}
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);

		if(sprite.getY() < manager.getViewport().getWorldHeight())
		{
			if(System.currentTimeMillis() - lastFire >= 1000)
			{
				EnemyLaser r1 = new EnemyLaser(this, 3);
				EnemyLaser r2 = new EnemyLaser(this, 3);
				EnemyLaser r3 = new EnemyLaser(this, 3);
				r1.setDirection(0,  -5);
				r2.setDirection(0,  -5);
				r3.setDirection(0,  -5);
				r1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() +  5);
				manager.spawnEnemyLaser(r1);
				r2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 55, sprite.getY() +  5);
				manager.spawnEnemyLaser(r2);
				r3.setPosition(sprite.getX() + sprite.getWidth() / 2 + 45, sprite.getY() +  5);
				manager.spawnEnemyLaser(r3);
				lastFire = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - lastSpecial1 >= 5000)
			{
				EnemyLaser g1 = new EnemyLaser(this, 2);
				EnemyLaser g2 = new EnemyLaser(this, 2);
				EnemyLaser g3 = new EnemyLaser(this, 2);
				EnemyLaser g4 = new EnemyLaser(this, 2);
				EnemyLaser g5 = new EnemyLaser(this, 2);
				EnemyLaser g6 = new EnemyLaser(this, 2);
				EnemyLaser g7 = new EnemyLaser(this, 2);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g4.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g5.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g6.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g7.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g1.setDirection(0,  -5);
				manager.spawnEnemyLaser(g1);
				g2.setDirection(1,  -4);
				manager.spawnEnemyLaser(g2);
				g3.setDirection(-1,  -4);
				manager.spawnEnemyLaser(g3);
				g4.setDirection(2,  -3);
				manager.spawnEnemyLaser(g4);
				g5.setDirection(-2,  -3);
				manager.spawnEnemyLaser(g5);
				g6.setDirection(3,  -2);
				manager.spawnEnemyLaser(g6);
				g7.setDirection(-3,  -2);
				manager.spawnEnemyLaser(g7);
				lastSpecial1 = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - lastSpecial2 >= 13000)
			{
				lastSpecial2Active = true;
				EnemyLaser o1 = new EnemyLaser(this, 1);
				EnemyLaser o2 = new EnemyLaser(this, 1);
				EnemyLaser o3 = new EnemyLaser(this, 1);
				o1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
				o2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
				o3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
				o1.setDirection(0, -5);
				manager.spawnEnemyLaser(o1);
				o2.setDirection(1, -5);
				manager.spawnEnemyLaser(o2);
				o3.setDirection(-1, -5);
				manager.spawnEnemyLaser(o3);
				lastSpecial2 = System.currentTimeMillis();
				lastSpecial2Duration = System.currentTimeMillis();
			}
			if(lastSpecial2Active)
			{
				if(System.currentTimeMillis() - lastSpecial2 > 3000)
					lastSpecial2Active = false;
				if(System.currentTimeMillis() - lastSpecial2Duration > 500)
				{
					EnemyLaser o1 = new EnemyLaser(this, 1);
					EnemyLaser o2 = new EnemyLaser(this, 1);
					EnemyLaser o3 = new EnemyLaser(this, 1);
					o1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
					o2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
					o3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
					o1.setDirection(0, -5);
					manager.spawnEnemyLaser(o1);
					o2.setDirection(1, -5);
					manager.spawnEnemyLaser(o2);
					o3.setDirection(-1, -5);
					manager.spawnEnemyLaser(o3);
					lastSpecial2Duration = System.currentTimeMillis();
				}
			}
		}
	}
	
	@Override 
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		sb.draw(Assets.manager.get("data/textures/health.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
		sb.draw(Assets.manager.get("data/textures/healthFill.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
		super.render(sb);
	}
	
}
