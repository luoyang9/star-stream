package xyz.charliezhang.shooter.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.charliezhang.shooter.Assets;

public class Background 
{
	private Texture image;
	private Sprite sprite1;
	private Sprite sprite2;
	private float dx;
	private float dy;
	
	public Background()
	{
		image = Assets.manager.get("data/textures/background.png", Texture.class);
		sprite1 = new Sprite(image);
		sprite2 = new Sprite(image);
		sprite1.setPosition(-50, 0);
	}

	public void setSize(float worldWidth)
	{
		sprite1.setSize(worldWidth + 100, (worldWidth + 100)/(float)image.getWidth()*image.getHeight());
		sprite2.setSize(worldWidth + 100, (worldWidth + 100)/(float)image.getWidth()*image.getHeight());
		sprite2.setPosition(-50, sprite1.getHeight());
	}
	
	public void setVector(float dx, float dy)
	{
		this.dx = dx;
		this.dy = -4;
	}

	public void translate(float xdir)
	{
		if(xdir > 10) xdir = 10;
		if(xdir < -10) xdir = -10;
		if(xdir < 0 && sprite1.getX() >= -100) sprite1.translateX(xdir / 10);
		if(xdir > 0 && sprite1.getX() <= 0) sprite1.translateX(xdir / 10);
		if(xdir < 0 && sprite2.getX() >= -100) sprite2.translateX(xdir / 10);
		if(xdir > 0 && sprite2.getX() <= 0) sprite2.translateX(xdir / 10);
	}

	public void update()
	{
		sprite1.setPosition(sprite1.getX() + dx, sprite1.getY() + dy);
		sprite2.setPosition(sprite2.getX() + dx, sprite2.getY() + dy);
	}
	
	public void render(SpriteBatch sb)
	{
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
