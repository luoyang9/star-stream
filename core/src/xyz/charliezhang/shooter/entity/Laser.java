package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.entity.player.PlayerLaser;

public class Laser extends PlayerLaser
{
	private float currentRotation;
	public Laser(EntityManager manager) {
		super(manager);
		
		textureAtlas = Assets.manager.get("data/textures/laserB.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(6, 28);
		sprite.setOrigin(3, 14);
		currentRotation = 0;
	}

	@Override
	public void update() {
		currentRotation = -MathUtils.radiansToDegrees*MathUtils.atan2(direction.x , direction.y);
		if(sprite.getRotation() != currentRotation)sprite.rotate(currentRotation - sprite.getRotation());
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
	}
}
