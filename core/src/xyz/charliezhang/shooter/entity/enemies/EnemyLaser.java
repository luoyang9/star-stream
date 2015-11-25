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
		case 1: textureAtlas = new TextureAtlas(Gdx.files.internal("laserO.atlas"));
		break;
		case 2: textureAtlas = new TextureAtlas(Gdx.files.internal("laserG.atlas"));
		break;
		case 3: textureAtlas = new TextureAtlas(Gdx.files.internal("laserR.atlas"));
		break;
		default: textureAtlas = new TextureAtlas(Gdx.files.internal("laserO.atlas"));
		}
		
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(10, 10);

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
