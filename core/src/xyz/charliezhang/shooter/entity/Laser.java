package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;

public class Laser extends PlayerLaser
{
	public Laser(MainGame game) {
		super();
		
		textureAtlas = game.manager.get("data/textures/laser.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(5, 10);
	}

	@Override
	public void update() {
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
	}
}
