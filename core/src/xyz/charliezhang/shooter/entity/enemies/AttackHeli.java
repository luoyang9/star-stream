package xyz.charliezhang.shooter.entity.enemies;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class AttackHeli extends Enemy
{
	public AttackHeli(EntityManager manager) {
		super();
		
		textureAtlas = manager.getGame().manager.get("data/textures/attackheli.atlas", TextureAtlas.class);
		animation = new Animation(1/30f, textureAtlas.getRegions());
		
		sprite.setSize(75, 75);
		
		//set enemy data
		health = maxHealth = 15;
		damage = 1;
		score = 100;
		this.manager = manager;
	}

	@Override
	public void update() {
		super.update();

		if(sprite.getY() <= MainGame.HEIGHT - stop && direction.y < 0)
		{
			setDirection(0, 0);
		}


		if(sprite.getY() < MainGame.HEIGHT)
		{
			if(System.currentTimeMillis() - lastFire >= 2000)
			{
				EnemyLaser l = new EnemyLaser(this, 1);
				l.setDirection(0, -3);
				l.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l);
				EnemyLaser l2 = new EnemyLaser(this, 1);
				l2.setDirection(1.5f, -3);
				l2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l2);
				EnemyLaser l3 = new EnemyLaser(this, 1);
				l3.setDirection(-1.5f, -3);
				l3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l3);
				lastFire = System.currentTimeMillis();
			}
		}

		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
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
