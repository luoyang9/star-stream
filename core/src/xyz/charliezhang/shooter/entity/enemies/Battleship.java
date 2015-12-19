package xyz.charliezhang.shooter.entity.enemies;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Battleship extends Enemy
{
	private int stop;
	public Battleship(EntityManager manager, int stop) {
		super();
		
		textureAtlas = manager.getGame().manager.get("data/textures/battleship.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(45, 45);
		
		//set enemy data
		health = maxHealth = 15;
		damage = 1;
		this.manager = manager;
		this.stop = stop;
	}

	@Override
	public void update() {
		if(sprite.getY() <= MainGame.HEIGHT - stop && direction.y < 0)
		{
			setDirection(0, 0);
		}

		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);

		if(sprite.getY() < MainGame.HEIGHT)
		{
			if(System.currentTimeMillis() - lastFire >= 2000)
			{
				EnemyLaser l = new EnemyLaser(this, 1);
				l.setDirection(0, -3);
				l.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l);
				EnemyLaser l2 = new EnemyLaser(this, 1);
				l2.setDirection((float)Math.random()+1.5f, -3);
				l2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l2);
				EnemyLaser l3 = new EnemyLaser(this, 1);
				l3.setDirection(-((float)Math.random()+1.5f), -3);
				l3.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l3);
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
