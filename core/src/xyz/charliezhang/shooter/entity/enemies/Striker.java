package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Striker extends Enemy
{
	private int stop;
	private boolean intro;
	public Striker(EntityManager manager, int s) {
		super();

		textureAtlas = manager.getGame().manager.get("data/textures/striker.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(40, 40);
		
		health = maxHealth = 5;
		damage = 2;
		this.manager = manager;
		stop = s;
		intro = true;
	}

	@Override
	public void update() {

		if(sprite.getY() <= MainGame.HEIGHT - stop && intro)
		{
			setDirection(4, 0);
			intro = false;
		}

		if(sprite.getX() <= 0)
		{
			setDirection(4,  0);
		}
		else if(sprite.getX() >= MainGame.WIDTH - 60)
		{
			setDirection(-4, 0);
		}
		
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
		

		if(sprite.getY() < MainGame.HEIGHT)
		{
			if(System.currentTimeMillis() - lastFire >= 1000)
			{
				EnemyLaser g1 = new EnemyLaser(this, 2);
				EnemyLaser g2 = new EnemyLaser(this, 2);
				g1.setDirection(0, -7);
				g2.setDirection(0, -7);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + 5);
				manager.spawnEnemyLaser(g1);
				g2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 10, sprite.getY() + 5);
				manager.spawnEnemyLaser(g2);
				lastFire = System.currentTimeMillis();
			}
		}
	}
	
	@Override 
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		sb.draw(manager.getGame().manager.get("data/textures/health.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
		sb.draw(manager.getGame().manager.get("data/textures/healthFill.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
		super.render(sb);
	}
	
}
