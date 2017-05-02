package xyz.charliezhang.starstream.entity.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import xyz.charliezhang.starstream.Assets;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.entity.Projectile;

import static xyz.charliezhang.starstream.Config.*;

public class EnemyLaser extends Projectile implements Pool.Poolable
{
	private Enemy enemy;

	public void init(EntityManager manager, Enemy enemy, int colour) {
		super.init(manager);
		this.enemy = enemy;
		switch(colour) {
			case 1: textureAtlas = Assets.manager.get(LASER_O_PATH, TextureAtlas.class);
				sprite.setSize(10, 10);
				sprite.setOrigin(5, 5);
				break;
			case 2: textureAtlas = Assets.manager.get(LASER_F_PATH, TextureAtlas.class);
				sprite.setSize(7, 38);
				sprite.setOrigin(3.5f, 19);
				break;
			case 3: textureAtlas = Assets.manager.get(LASER_R_PATH, TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
				break;
			default: textureAtlas = Assets.manager.get(LASER_G_PATH, TextureAtlas.class);
				sprite.setSize(6, 18);
				sprite.setOrigin(3, 9);
		}
		animation = new Animation<TextureRegion>(1/20f, textureAtlas.getRegions());
	}

	public Enemy getEnemy(){return enemy;}

	@Override
	public void reset() {
		enemy = null;
		super.reset();
	}
}
