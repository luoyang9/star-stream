package xyz.charliezhang.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureManager 
{
	//buttons
	public static Texture ACT = new Texture(Gdx.files.internal("act.png"));
	
	//background
	public static Texture BACKGROUND = new Texture(Gdx.files.internal("background.png"));
	public static Texture LOGO = new Texture(Gdx.files.internal("logo.png"));
	
	//health
	public static Texture HEALTH = new Texture(Gdx.files.internal("health.png"));
	public static Texture HEALTHFILL = new Texture(Gdx.files.internal("healthFill.png"));
}
