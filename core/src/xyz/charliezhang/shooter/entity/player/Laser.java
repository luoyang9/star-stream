package xyz.charliezhang.shooter.entity.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.player.PlayerLaser;

public class Laser extends PlayerLaser
{
	private float currentRotation;
	public static final int BLUE = 0;
	public static final int ORANGE = 1;

	public Laser(EntityManager manager, int color) {
		super(manager);

		switch(color) {
			case Laser.BLUE:
				textureAtlas = Assets.manager.get("data/textures/laserB.atlas", TextureAtlas.class);
				animation = new Animation(1 / 15f, textureAtlas.getRegions());
				sprite.setSize(6, 28);
				sprite.setOrigin(3, 14);
				break;
			case Laser.ORANGE:
				textureAtlas = Assets.manager.get("data/textures/laserO.atlas", TextureAtlas.class);
				animation = new Animation(1 / 15f, textureAtlas.getRegions());
				sprite.setSize(18, 18);
				sprite.setOrigin(9, 9);
				break;
			default:
				textureAtlas = Assets.manager.get("data/textures/laserB.atlas", TextureAtlas.class);
				animation = new Animation(1 / 15f, textureAtlas.getRegions());
				sprite.setSize(6, 28);
				sprite.setOrigin(3, 14);
		}
		currentRotation = 0;
	}

	@Override
	public void update() {
		currentRotation = -MathUtils.radiansToDegrees*MathUtils.atan2(direction.x , direction.y);
		if(sprite.getRotation() != currentRotation)sprite.rotate(currentRotation - sprite.getRotation());
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
	}
}
