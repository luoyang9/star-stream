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

public class Icarus extends Enemy
{
	private boolean intro;
	private int stop;

	public Icarus() {
		super();

		textureAtlas = Assets.manager.get(ICARUS_PATH, TextureAtlas.class);
		animation = new Animation<TextureRegion>(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(50, 50);
		
		health = maxHealth = ICARUS_HEALTH;
		damage = ICARUS_DAMAGE;
		score = ICARUS_SCORE;
		coin = ICARUS_COIN;

		intro = ICARUS_INITIAL_INTRO;
	}

	@Override
	public void applyUpgrades() {
		this.health += manager.getEnemyModifier();
		this.maxHealth += manager.getEnemyModifier();
		this.damage += manager.getEnemyModifier() / 2;
	}

	//json read method
	@Override
	public void read (Json json, JsonValue jsonMap) {
		super.read(json, jsonMap);
		stop = jsonMap.getInt("stop");
	}

	@Override
	public void update() {

		if(intro && sprite.getY() <= manager.getViewport().getWorldHeight() - stop)
		{
			if(Math.random() >= 0.5) setDirection(2, -2);
			else setDirection(-2, 2);
			intro = false;
		}

		if(!intro) {
			if (sprite.getY() < manager.getViewport().getWorldHeight() - stop) direction.y += 0.04f;
			if (sprite.getY() >= manager.getViewport().getWorldHeight() - stop) direction.y -= 0.04f;
			if (sprite.getX() >= manager.getViewport().getWorldWidth() / 2) direction.x -= 0.01f;
			if (sprite.getX() < manager.getViewport().getWorldWidth() / 2) direction.x += 0.01f;
		}

		if(sprite.getY() < manager.getViewport().getWorldHeight())
		{
			if(System.currentTimeMillis() - lastFire >= 2500)
			{
				EnemyLaser g1 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g2 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g3 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g4 = manager.getEnemyLaserPool().obtain();
				EnemyLaser g5 = manager.getEnemyLaserPool().obtain();
				g1.init(manager, this, 1);
				g2.init(manager, this, 1);
				g3.init(manager, this, 1);
				g4.init(manager, this, 1);
				g5.init(manager, this, 1);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g4.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g5.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				
				g1.setDirection(0,  -8f);
				manager.spawnEnemyLaser(g1);
				g2.setDirection(1f, -7f);
				manager.spawnEnemyLaser(g2);
				g3.setDirection(-1f, -7f);
				manager.spawnEnemyLaser(g3);
				g4.setDirection(2f, -5f);
				manager.spawnEnemyLaser(g4);
				g5.setDirection(-2f, -5f);
				manager.spawnEnemyLaser(g5);
				lastFire = System.currentTimeMillis();
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
