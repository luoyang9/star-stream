package xyz.charliezhang.shooter.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.charliezhang.shooter.TextureManager;

public class Background 
{
	private Texture image;
	private Sprite sprite1;
	private Sprite sprite2;
	private float dx;
	private float dy;
	
	public Background()
	{
		image = TextureManager.BACKGROUND;
		sprite1 = new Sprite(image);
		sprite2 = new Sprite(image);
	}
	public void setSize(float width, float height)
	{
		sprite1.setSize(width, height);
		sprite2.setSize(width, height);
	}

	public void setPosition(float x, float y)
	{
		sprite1.setPosition(x, y);
		sprite2.setPosition(x, y + sprite2.getHeight());
	}
	
	public void setVector(float dx, float dy)
	{
		this.dx = dx;
		this.dy = dy;
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
			sprite1.setPosition(0, sprite1.getHeight());
		}
		if(sprite2.getY() < -sprite2.getHeight())
		{
			sprite2.setPosition(0, sprite2.getHeight());
		}
	}
}
