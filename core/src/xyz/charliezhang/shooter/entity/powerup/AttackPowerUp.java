package xyz.charliezhang.shooter.entity.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.entity.Entity;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Player;

public class AttackPowerUp extends PowerUp
{
	
	public AttackPowerUp(MainGame game) {
		super();
		
		textureAtlas = game.manager.get("data/textures/attpowerup.atlas", TextureAtlas.class);
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
