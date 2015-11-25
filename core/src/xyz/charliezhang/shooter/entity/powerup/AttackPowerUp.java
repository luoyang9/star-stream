package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.Entity;

public class AttackPowerUp extends PowerUp
{
	
	public AttackPowerUp() {
		super();
		
		textureAtlas = new TextureAtlas(Gdx.files.internal("attpowerup.atlas"));
		animation = new Animation(1/15f, textureAtlas.getRegions());

		sprite.setSize(50, 50);

		duration = 1000;
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
