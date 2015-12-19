package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity 
{
	protected TextureAtlas textureAtlas;
	protected Animation animation;
	protected float animationTime;
	protected Sprite sprite;
	protected Vector2 direction;
	
	public Entity()
	{
		sprite = new Sprite();	
		direction = new Vector2();
		
		//start animation time
		animationTime = 0;
	}

	public void dispose()
	{
	}

	public void render(SpriteBatch sb)
	{
		//add animation time
        animationTime += Gdx.graphics.getDeltaTime();
        
		sprite.draw(sb);
	}
	
	public Rectangle getBounds()
	{
		return sprite.getBoundingRectangle();
	}
	
	public abstract void update();
	
	public Vector2 getPosition() {return new Vector2(sprite.getX(), sprite.getY());}
	
	public void setPosition(float x, float y)
	{
		sprite.setPosition(x, y);
	}
	
	public void setDirection(float x, float y)
	{
		direction.set(x, y);
	}
	
	public void modifyDirection(float x, float y)
	{
		direction.add(x, y);
	}
	
	public Sprite getSprite() {return sprite;}
}
