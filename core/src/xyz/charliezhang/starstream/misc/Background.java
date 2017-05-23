package xyz.charliezhang.starstream.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.charliezhang.starstream.Assets;

import static xyz.charliezhang.starstream.Config.GAME_BACKGROUND_PATH;

public class Background 
{
	private Texture image;
	private Sprite sprite1;
	private Sprite sprite2;
	private float dx;
	private float dy;
	private boolean paused;
	
	public Background()
	{
		image = Assets.manager.get(GAME_BACKGROUND_PATH, Texture.class);
		sprite1 = new Sprite(image);
		sprite2 = new Sprite(image);
		sprite1.setPosition(-50, 0);
		paused = false;
	}

	public void setSize(float worldWidth)
	{
		if(paused) return;
		sprite1.setSize(worldWidth + 100, (worldWidth + 100)/(float)image.getWidth()*image.getHeight());
		sprite2.setSize(worldWidth + 100, (worldWidth + 100)/(float)image.getWidth()*image.getHeight());
		sprite2.setPosition(-50, sprite1.getHeight());
	}
	
	public void setVector(float dx, float dy)
	{
		this.dx = dx;
		this.dy = dy;
	}

	public void translate(float xdir) {
		if(paused) return;
		if (xdir > 10) xdir = 10;
		if (xdir < -10) xdir = -10;
		if (xdir < 0 && sprite1.getX() >= -100) sprite1.translateX(xdir / 10);
		if (xdir > 0 && sprite1.getX() <= 0) sprite1.translateX(xdir / 10);
		if (xdir < 0 && sprite2.getX() >= -100) sprite2.translateX(xdir / 10);
		if (xdir > 0 && sprite2.getX() <= 0) sprite2.translateX(xdir / 10);
	}

	public void update()
	{
		if(paused) return;
		sprite1.setPosition(sprite1.getX() + dx, sprite1.getY() + dy);
		sprite2.setPosition(sprite2.getX() + dx, sprite2.getY() + dy);
	}

	public void pause() {
		paused = true;

	}
	public void unpause() {
		paused = false;

		float x = sprite1.getX();
		float y = sprite1.getY();
		float width = sprite1.getWidth();
		float height = sprite1.getHeight();
		sprite1 = new Sprite(image);
		sprite1.setSize(width, height);
		sprite1.setPosition(x, y);

		x = sprite2.getX();
		y = sprite2.getY();
		width = sprite2.getWidth();
		height = sprite2.getHeight();
		sprite2 = new Sprite(image);
		sprite2.setPosition(x, y);
		sprite2.setSize(width, height);
	}
	
	public void render(SpriteBatch sb)
	{
		if(paused) return;
		sprite1.draw(sb);
		sprite2.draw(sb);
		if(sprite1.getY() < -sprite1.getHeight())
		{
			sprite1.setPosition(-50, sprite2.getY()+sprite2.getHeight());
		}
		if(sprite2.getY() < -sprite2.getHeight())
		{
			sprite2.setPosition(-50, sprite1.getY() + sprite1.getHeight());
		}
	}
}
