package xyz.charliezhang.starstream.entity.enemies;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import xyz.charliezhang.starstream.Assets;

import static xyz.charliezhang.starstream.Config.*;

public class UFO extends Enemy
{
	private int stop;
	private float rotation;

	public UFO() {
		super();

		textureAtlas = Assets.manager.get(UFO_PATH, TextureAtlas.class);
		animation = new Animation(1/5f, textureAtlas.getRegions());

		//set enemy data
		health = maxHealth = UFO_HEALTH;
		rotation = UFO_INITIAL_ROTATION;
		damage = UFO_DAMAGE;
		score = UFO_SCORE;

		sprite.setSize(40, 40);
		sprite.setOrigin(20, 20);
		sprite.setRotation(rotation);
	}

	//json read method
	@Override
	public void read (Json json, JsonValue jsonMap) {
		super.read(json, jsonMap);
		stop = jsonMap.getInt("stop");
	}

	@Override
	public void update() {
		if(sprite.getY() <= manager.getViewport().getWorldHeight() - stop && direction.y < 0)
		{
			setDirection(0, 0);
		}


		if(sprite.getY() < manager.getViewport().getWorldHeight() - stop)
		{
			if(System.currentTimeMillis() - lastFire >= 1000)
			{
				EnemyLaser l = manager.getEnemyLaserPool().obtain();
				l.init(manager, this, 1);
				l.setDirection(-MathUtils.sinDeg(rotation) * 8, MathUtils.cosDeg(rotation) * 8);
				l.setPosition(sprite.getX() + sprite.getOriginX() - l.getSprite().getOriginX(), sprite.getY() + sprite.getOriginY() - l.getSprite().getOriginY());
				manager.spawnEnemyLaser(l);
				EnemyLaser l2 = manager.getEnemyLaserPool().obtain();
				l2.init(manager, this, 1);
				l2.setDirection(-MathUtils.sinDeg(rotation + 90) * 8, MathUtils.cosDeg(rotation + 90) * 8);
				l2.setPosition(sprite.getX() + sprite.getOriginX() - l.getSprite().getOriginX(), sprite.getY() + sprite.getOriginY() - l.getSprite().getOriginY());
				manager.spawnEnemyLaser(l2);
				EnemyLaser l3 = manager.getEnemyLaserPool().obtain();
				l3.init(manager, this, 1);
				l3.setDirection(-MathUtils.sinDeg(rotation + 180) * 8, MathUtils.cosDeg(rotation + 180) * 8);
				l3.setPosition(sprite.getX() + sprite.getOriginX() - l.getSprite().getOriginX(), sprite.getY() + sprite.getOriginY() - l.getSprite().getOriginY());
				manager.spawnEnemyLaser(l3);
				EnemyLaser l4 = manager.getEnemyLaserPool().obtain();
				l4.init(manager, this, 1);
				l4.setDirection(-MathUtils.sinDeg(rotation + 270) * 8, MathUtils.cosDeg(rotation + 270) * 8);
				l4.setPosition(sprite.getX() + sprite.getOriginX() - l.getSprite().getOriginX(), sprite.getY() + sprite.getOriginY() - l.getSprite().getOriginY());
				manager.spawnEnemyLaser(l4);
				lastFire = System.currentTimeMillis();
			}
		}

		rotation++;
		if(rotation > 360) rotation = 0;
		sprite.setRotation(rotation - 50);

		super.update();
	}

	@Override 
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		sb.draw(Assets.manager.get(HEALTH_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
		sb.draw(Assets.manager.get(HEALTH_FILL_PATH, Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
		super.render(sb);
	}
	
}
