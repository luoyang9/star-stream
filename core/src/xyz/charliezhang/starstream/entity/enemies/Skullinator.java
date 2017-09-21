package xyz.charliezhang.starstream.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import xyz.charliezhang.starstream.Assets;

import static xyz.charliezhang.starstream.Config.*;

public class Skullinator extends Enemy
{
	private boolean entered, lastSpecial2Active;
	private long lastSpecial1, lastSpecial2, lastSpecial2Duration;
	private int stop;

	public Skullinator() {
		super();

		textureAtlas = Assets.manager.get(SKULLINATOR_PATH, TextureAtlas.class);
		animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(120, 120);
		
		health = maxHealth = SKULLINATOR_HEALTH;
		damage = SKULLINATOR_DAMAGE;
		score = SKULLINATOR_SCORE;
		coin = SKULLINATOR_COIN;
		entered = SKULLINATOR_INITIAL_ENTERED;
		isBoss = true;
		lastSpecial1 = lastSpecial2 = System.currentTimeMillis();
	}

	//json read method
	@Override
	public void read (Json json, JsonValue jsonMap) {
		super.read(json, jsonMap);
		stop = jsonMap.getInt("stop");
	}

	@Override
	public void update() {
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

		if(sprite.getY() < manager.getViewport().getWorldHeight())
		{
			if(System.currentTimeMillis() - lastFire >= 1000)
			{
				EnemyLaser r1 = manager.getEnemyLaserPool().obtain();
				EnemyLaser r2 = manager.getEnemyLaserPool().obtain();
				EnemyLaser r3 = manager.getEnemyLaserPool().obtain();
				r1.init(manager, this, 3);
				r2.init(manager, this, 3);
				r3.init(manager, this, 3);
				r1.setDirection(0,  -8);
				r2.setDirection(0,  -8);
				r3.setDirection(0,  -8);
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
				EnemyLaser g1 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g2 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g3 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g4 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g5 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g6 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g7 = manager.getEnemyLaserPool().obtain();
				g1.init(manager, this, 2);
				g2.init(manager, this, 2);
				g3.init(manager, this, 2);
				g4.init(manager, this, 2);
				g5.init(manager, this, 2);
				g6.init(manager, this, 2);
				g7.init(manager, this, 2);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g4.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g5.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g6.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g7.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g1.setDirection(0,  -8);
				manager.spawnEnemyLaser(g1);
				g2.setDirection(1,  -7);
				manager.spawnEnemyLaser(g2);
				g3.setDirection(-1,  -7);
				manager.spawnEnemyLaser(g3);
				g4.setDirection(2,  -6);
				manager.spawnEnemyLaser(g4);
				g5.setDirection(-2,  -6);
				manager.spawnEnemyLaser(g5);
				g6.setDirection(3,  -5);
				manager.spawnEnemyLaser(g6);
				g7.setDirection(-3,  -5);
				manager.spawnEnemyLaser(g7);
				lastSpecial1 = System.currentTimeMillis();
			}
			if(System.currentTimeMillis() - lastSpecial2 >= 13000)
			{
				lastSpecial2Active = true;
				EnemyLaser o1 = manager.getEnemyLaserPool().obtain();
				EnemyLaser o2 = manager.getEnemyLaserPool().obtain();
				EnemyLaser o3 = manager.getEnemyLaserPool().obtain();
				o1.init(manager, this, 1);
				o2.init(manager, this, 1);
				o3.init(manager, this, 1);
				o1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
				o2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
				o3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + + 5);
				o1.setDirection(0, -10);
				manager.spawnEnemyLaser(o1);
				o2.setDirection(1, -10);
				manager.spawnEnemyLaser(o2);
				o3.setDirection(-1, -10);
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
					EnemyLaser o1 = manager.getEnemyLaserPool().obtain();
					EnemyLaser o2 = manager.getEnemyLaserPool().obtain();
					EnemyLaser o3 = manager.getEnemyLaserPool().obtain();
					o1.init(manager, this, 1);
					o2.init(manager, this, 1);
					o3.init(manager, this, 1);
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

		super.update();
	}
	
	@Override 
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		sb.draw(Assets.manager.get(HEALTH_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
		double healthRatio = (double)health / maxHealth;
		if(healthRatio < 0 || healthRatio > 1) healthRatio = 0;
		sb.draw(Assets.manager.get(HEALTH_FILL_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * healthRatio), 5);
		super.render(sb);
	}
	
}
