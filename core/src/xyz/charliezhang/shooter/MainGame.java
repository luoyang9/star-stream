package xyz.charliezhang.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
		GameData.init();
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

		//clear screen
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		super.render();
	}
}

