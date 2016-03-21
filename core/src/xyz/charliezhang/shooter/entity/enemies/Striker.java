package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Striker extends Enemy
{
	private boolean intro;
	public Striker(EntityManager manager) {
		super();

		textureAtlas = Assets.manager.get("data/textures/striker.atlas", TextureAtlas.class);
		animation = new Animation(1/20f, textureAtlas.getRegions());
		
		sprite.setSize(50, 58);
		
		health = maxHealth = 25;
		damage = 2;
		score = 75;
		this.manager = manager;
		intro = true;
	}

	@Override
	public void update() {
		super.update();

		if(sprite.getY() <= manager.getViewport().getWorldHeight() - stop && intro)
		{
			setDirection(4, 0);
			intro = false;
		}

		if(sprite.getX() <= 0)
		{
			setDirection(4,  0);
		}
		else if(sprite.getX() >= manager.getViewport().getWorldWidth() - 50)
		{
			setDirection(-4, 0);
		}
		
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
		

		if(sprite.getY() < manager.getViewport().getWorldHeight())
		{
			if(System.currentTimeMillis() - lastFire >= 1000)
			{
				EnemyLaser g1 = new EnemyLaser(this, 2);
				g1.setDirection(0, -7);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 5);
				manager.spawnEnemyLaser(g1);
				lastFire = System.currentTimeMillis();
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
