package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Striker extends Enemy
{
	private boolean intro;
	private int stop;

	public Striker() {
		super();

		textureAtlas = Assets.manager.get("data/textures/striker.atlas", TextureAtlas.class);
		animation = new Animation(1/20f, textureAtlas.getRegions());
		
		sprite.setSize(50, 58);
		
		health = maxHealth = 25;
		damage = 2;
		score = 75;

		intro = true;
	}

	//json read method
	@Override
	public void read (Json json, JsonValue jsonMap) {
		super.read(json, jsonMap);
		stop = jsonMap.getInt("stop");
	}

	@Override
	public void update() {
		if(intro && sprite.getY() <= MainGame.HEIGHT - stop)
		{
			intro = false;
			setDirection(4, 0);
		}

		if(sprite.getX() <= 0)
		{
			setDirection(4,  0);
		}
		else if(sprite.getX() >= MainGame.WIDTH - sprite.getWidth())
		{
			setDirection(-4, 0);
		}

		if(sprite.getY() < MainGame.HEIGHT)
		{
			if(System.currentTimeMillis() - lastFire >= 1000)
			{
				EnemyLaser g1 = manager.getEnemyLaserPool().obtain();
				g1.init(manager, this, 2);
				g1.setDirection(0, -10);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 5);
				manager.spawnEnemyLaser(g1);
				lastFire = System.currentTimeMillis();
			}
		}
		super.update();
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
