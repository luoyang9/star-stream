package xyz.charliezhang.shooter.entity.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.Entity;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Projectile;

public class EnemyLaser extends Projectile implements Pool.Poolable
{
	private Enemy enemy;

	public EnemyLaser() {
		super();
	}

	public void init(EntityManager manager, Enemy enemy, int colour) {
		super.init(manager);
		this.enemy = enemy;
		switch(colour) {
			case 1: textureAtlas = Assets.manager.get("data/textures/laserG.atlas", TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
				break;
			case 2: textureAtlas = Assets.manager.get("data/textures/laserF.atlas", TextureAtlas.class);
				sprite.setSize(7, 38);
				sprite.setOrigin(3.5f, 19);
				break;
			case 3: textureAtlas = Assets.manager.get("data/textures/laserR.atlas", TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
				break;
			default: textureAtlas = Assets.manager.get("data/textures/laserG.atlas", TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
		}
		animation = new Animation(1/20f, textureAtlas.getRegions());
	}

	@Override
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime, true));
		super.render(sb);
	}

	public Enemy getEnemy(){return enemy;}

	@Override
	public void reset() {
		enemy = null;
		super.reset();
	}
}
