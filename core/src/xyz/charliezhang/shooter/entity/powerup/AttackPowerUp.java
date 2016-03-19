package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.Assets;

public class AttackPowerUp extends PowerUp
{
	
	public AttackPowerUp() {
		super();
		
		textureAtlas = Assets.manager.get("data/textures/attpowerup.atlas", TextureAtlas.class);
		animation = new Animation(1/15f, textureAtlas.getRegions());

		sprite.setSize(16, 24);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(SpriteBatch sb)
	{
		super.render(sb);
	}

}
