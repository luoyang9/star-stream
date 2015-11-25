package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import xyz.charliezhang.shooter.MainGame;

public class Laser extends Entity
{
	public Laser() {
		super();
		
		textureAtlas = new TextureAtlas(Gdx.files.internal("laser.atlas"));
		animation = new Animation(1/15f, textureAtlas.getRegions());
		
		sprite.setSize(10, 10);
	}

	@Override
	public void update() {
		sprite.setPosition(sprite.getX() + direction.x, sprite.getY() + direction.y);
	}
	
	public boolean checkEnd()
	{
		return sprite.getY() >= MainGame.HEIGHT;
	}
	
	public void render(SpriteBatch sb)
	{
		sprite.setRegion(animation.getKeyFrame(animationTime));
		super.render(sb);
	}
}
