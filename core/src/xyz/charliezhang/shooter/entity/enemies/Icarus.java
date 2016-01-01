package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Icarus extends Enemy
{
	private int stop;
	private boolean intro;
	public Icarus(EntityManager manager, int stop) {
		super();
		
		textureAtlas = manager.getGame().manager.get("data/textures/icarus.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(100, 100);
		
		health = maxHealth = 30;
		damage = 2;
		score = 150;
		this.manager = manager;
		this.stop = stop;

		intro = true;
	}

	@Override
	public void update() {
		if(intro && sprite.getY() <= MainGame.HEIGHT - stop)
		{
			setDirection(2, -2);
			intro = false;
		}

		if(!intro)
		{
			if (sprite.getY() < MainGame.HEIGHT - stop) direction.y += 0.04f;
			if (sprite.getY() >= MainGame.HEIGHT - stop) direction.y -= 0.04f;
			if (sprite.getX() >= MainGame.WIDTH / 2) direction.x -= 0.01f;
			if (sprite.getX() < MainGame.WIDTH / 2) direction.x += 0.01f;
		}
		
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);

		if(sprite.getY() < MainGame.HEIGHT)
		{
			if(System.currentTimeMillis() - lastFire >= 2500)
			{
				EnemyLaser g1 = new EnemyLaser(this, 1);
				EnemyLaser g2 = new EnemyLaser(this, 1);
				EnemyLaser g3 = new EnemyLaser(this, 1);
				EnemyLaser g4 = new EnemyLaser(this, 1);
				EnemyLaser g5 = new EnemyLaser(this, 1);
				g1.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g4.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				g5.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				
				g1.setDirection(0,  -2.3f);
				manager.spawnEnemyLaser(g1);
				g2.setDirection(1f, -1.8f);
				manager.spawnEnemyLaser(g2);
				g3.setDirection(-1f, -1.8f);
				manager.spawnEnemyLaser(g3);
				g4.setDirection(2f, -0.2f);
				manager.spawnEnemyLaser(g4);
				g5.setDirection(-2f, -0.2f);
				manager.spawnEnemyLaser(g5);
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
