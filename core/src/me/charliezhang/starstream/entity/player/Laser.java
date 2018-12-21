package me.charliezhang.starstream.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import me.charliezhang.starstream.Assets;
import me.charliezhang.starstream.entity.EntityManager;
import me.charliezhang.starstream.entity.Projectile;

import static me.charliezhang.starstream.Config.LASER_B_PATH;
import static me.charliezhang.starstream.Config.LASER_RR_PATH;

public class Laser extends Projectile implements Pool.Poolable
{
	static final int BLUE = 0;
	static final int RED = 1;

	public Laser() {
		super();
	}

	public void init(EntityManager manager, int color) {
		super.init(manager);

		switch(color) {
			case Laser.BLUE:
				textureAtlas = Assets.manager.get(LASER_B_PATH, TextureAtlas.class);
				animation = new Animation<TextureRegion>(1 / 15f, textureAtlas.getRegions());
				sprite.setSize(10.5f, 22.5f);
				sprite.setOrigin(5.25f, 11.25f);
				break;
			case Laser.RED:
				textureAtlas = Assets.manager.get(LASER_RR_PATH, TextureAtlas.class);
				animation = new Animation<TextureRegion>(1 / 15f, textureAtlas.getRegions());
				sprite.setSize(10, 10);
				sprite.setOrigin(5, 5);
				break;
			default:
				textureAtlas = Assets.manager.get(LASER_B_PATH, TextureAtlas.class);
				animation = new Animation<TextureRegion>(1 / 15f, textureAtlas.getRegions());
				sprite.setSize(6, 28);
				sprite.setOrigin(3, 14);
		}
	}

	public void reset() {
		super.reset();
	}
}
