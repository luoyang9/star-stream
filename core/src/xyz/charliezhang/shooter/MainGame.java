package xyz.charliezhang.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import xyz.charliezhang.shooter.music.MusicPlayer;
import xyz.charliezhang.shooter.screen.LoadScreen;

public class MainGame extends Game {

	//game constants
	public static final int WIDTH = 480, HEIGHT = 800;
	public static final int NUM_LEVELS = 9;

	//public stuff that we later access
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create () {

		batch = new SpriteBatch();
		font = new BitmapFont();

		Assets.init();
		MusicPlayer.init();
		FileHandler.init();
		this.setScreen(new LoadScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		Assets.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}

