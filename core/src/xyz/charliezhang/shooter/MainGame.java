package xyz.charliezhang.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.charliezhang.shooter.screen.LoadScreen;

public class MainGame extends Game {
	
	public static int WIDTH = 480, HEIGHT = 800;

	public SpriteBatch batch;
	public BitmapFont font;
	public AssetManager manager;

	@Override
	public void create () {

		batch = new SpriteBatch();
		font = new BitmapFont();
		manager = new AssetManager();

		this.setScreen(new LoadScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		manager.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}

