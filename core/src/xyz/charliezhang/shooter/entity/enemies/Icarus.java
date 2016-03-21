package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;

public class Icarus extends Enemy
{
	private boolean intro;
	public Icarus(EntityManager manager, int color) {
		super();

		switch(color)
		{
			case 1:
				textureAtlas = Assets.manager.get("data/textures/icarusB.atlas", TextureAtlas.class);
				break;
			case 2:
				textureAtlas = Assets.manager.get("data/textures/icarusG.atlas", TextureAtlas.class);
				break;
			case 3:
				textureAtlas = Assets.manager.get("data/textures/icarusR.atlas", TextureAtlas.class);
				break;
			case 4:
				textureAtlas = Assets.manager.get("data/textures/icarusB.atlas", TextureAtlas.class);
				break;
			default:
		}
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(93, 84);
		
		health = maxHealth = 60;
		damage = 2;
		score = 150;
		this.manager = manager;

		intro = true;
	}

	@Override
	public void update() {
		super.update();

		if(intro && sprite.getY() <= manager.getViewport().getWorldHeight() - stop)
		{
			setDirection(2, -2);
			intro = false;
		}

		if(!intro)
		{
			if (sprite.getY() < manager.getViewport().getWorldHeight() - stop) direction.y += 0.04f;
			if (sprite.getY() >= manager.getViewport().getWorldHeight() - stop) direction.y -= 0.04f;
			if (sprite.getX() >= manager.getViewport().getWorldWidth() / 2) direction.x -= 0.01f;
			if (sprite.getX() < manager.getViewport().getWorldWidth() / 2) direction.x += 0.01f;
		}
		
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);

		if(sprite.getY() < manager.getViewport().getWorldHeight())
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
		sb.draw(Assets.manager.get("data/textures/health.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), sprite.getWidth(), 5);
		sb.draw(Assets.manager.get("data/textures/healthFill.png", Texture.class), sprite.getX(), sprite.getY() + sprite.getHeight(), (int)(sprite.getWidth() * ((double)health / maxHealth)), 5);
		super.render(sb);
	}
	
}
