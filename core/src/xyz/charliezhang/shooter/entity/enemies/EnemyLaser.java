package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.entity.Entity;

public class EnemyLaser extends Entity
{
	private Enemy enemy;
	public EnemyLaser(Enemy enemy, int colour) {
		super();

		this.enemy = enemy;
		
		switch(colour)
		{
		case 1: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserO.atlas", TextureAtlas.class);
				sprite.setSize(12, 12);
				break;
		case 2: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserF.atlas", TextureAtlas.class);
				sprite.setSize(10, 34);
				break;
		case 3: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserR.atlas", TextureAtlas.class);
				sprite.setSize(12, 12);
				break;
		default: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserO.atlas", TextureAtlas.class);
				sprite.setSize(12, 12);
		}
		
		animation = new Animation(1/20f, textureAtlas.getRegions());


	}

	@Override
	public void update() {
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
	}
	
	@Override
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		super.render(sb);
	}

	public Enemy getEnemy(){return enemy;}
	
	public boolean checkEnd()
	{
		return sprite.getY() < 0;
	}
}
