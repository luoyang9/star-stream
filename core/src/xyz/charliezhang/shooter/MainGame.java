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
	
	public static int WIDTH = 480, HEIGHT = 800;

	public SpriteBatch batch;
	public BitmapFont font;
	public AssetManager manager;

	@Override
	public void create () {

		batch = new SpriteBatch();
		font = new BitmapFont();
		manager = new AssetManager();

		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		this.setScreen(new LoadScreen(this));
		MusicPlayer.init();
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

