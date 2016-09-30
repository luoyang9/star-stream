package xyz.charliezhang.shooter.entity.enemies;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.EntityManager;

public class UFO extends Enemy
{
	public UFO(EntityManager manager, int color) {
		super();

		switch(color)
		{
			case 1:
				textureAtlas = Assets.manager.get("data/textures/ufoB.atlas", TextureAtlas.class);
				break;
			case 2:
				textureAtlas = Assets.manager.get("data/textures/ufoG.atlas", TextureAtlas.class);
				break;
			case 3:
				textureAtlas = Assets.manager.get("data/textures/ufoR.atlas", TextureAtlas.class);
				break;
			case 4:
				textureAtlas = Assets.manager.get("data/textures/ufoY.atlas", TextureAtlas.class);
				break;
			default:
		}
		animation = new Animation(1/30f, textureAtlas.getRegions());
		
		sprite.setSize(75, 75);
		
		//set enemy data
		health = maxHealth = 30;
		damage = 1;
		score = 100;
		this.manager = manager;
	}

	@Override
	public void update() {
		super.update();

		if(sprite.getY() <= manager.getViewport().getWorldHeight() - stop && direction.y < 0)
		{
			setDirection(0, 0);
		}


		if(sprite.getY() < manager.getViewport().getWorldHeight())
		{
			if(System.currentTimeMillis() - lastFire >= 2000)
			{
				EnemyLaser l = manager.getEnemyLaserPool().obtain();
				l.init(manager, this, 1);
				l.setDirection(0, -3);
				l.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l);
				EnemyLaser l2 = manager.getEnemyLaserPool().obtain();
				l2.init(manager, this, 1);
				l2.setDirection(1.5f, -3);
				l2.setPosition(sprite.getX() + sprite.getWidth() / 2 - 5, sprite.getY() + 5);
				manager.spawnEnemyLaser(l2);
				EnemyLaser l3 = manager.getEnemyLaserPool().obtain();
				l3.init(manager, this, 1);
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
		sb.draw(Assets.manager.get("data/textures/health.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
		sb.draw(Assets.manager.get("data/textures/healthFill.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
		super.render(sb);
	}
	
}
