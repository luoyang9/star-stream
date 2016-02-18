package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import xyz.charliezhang.shooter.entity.Entity;

public class EnemyLaser extends Entity
{
	private float currentRotation;
	private Enemy enemy;
	public EnemyLaser(Enemy enemy, int colour) {
		super();

		this.enemy = enemy;
		
		switch(colour)
		{
		case 1: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserG.atlas", TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
				break;
		case 2: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserF.atlas", TextureAtlas.class);
				sprite.setSize(7, 38);
				sprite.setOrigin(3.5f, 19);
				break;
		case 3: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserR.atlas", TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
				break;
		default: textureAtlas = enemy.getManager().getGame().manager.get("data/textures/laserG.atlas", TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
		}
		
		animation = new Animation(1/20f, textureAtlas.getRegions());

		currentRotation = 0;

	}

	@Override
	public void update() {
		currentRotation = -MathUtils.radiansToDegrees*MathUtils.atan2(direction.x , direction.y);
		if(sprite.getRotation() != currentRotation)sprite.rotate(currentRotation - sprite.getRotation());
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
