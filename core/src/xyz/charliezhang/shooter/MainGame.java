package xyz.charliezhang.shooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.charliezhang.shooter.audio.AudioPlayer;
import xyz.charliezhang.shooter.screen.GameScreen;
import xyz.charliezhang.shooter.screen.ScreenManager;

public class MainGame extends ApplicationAdapter {
	
	public static int WIDTH = 480, HEIGHT = 800;
	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		AudioPlayer.init();
		ScreenManager.setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() {
		if(ScreenManager.getCurrentScreen() != null)
		{
			ScreenManager.getCurrentScreen().dispose();
		}
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(ScreenManager.getCurrentScreen() != null)
		{
			ScreenManager.getCurrentScreen().update();
		}

		if(ScreenManager.getCurrentScreen() != null)
		{
			ScreenManager.getCurrentScreen().render(batch, font);
		}
	}
	
	@Override
	public void resize(int width, int height)
	{
		if(ScreenManager.getCurrentScreen() != null)
		{
			ScreenManager.getCurrentScreen().resize(width, height);
		}
	}
	
	@Override
	public void pause()
	{
		if(ScreenManager.getCurrentScreen() != null)
		{
			ScreenManager.getCurrentScreen().pause();
		}
	}
	
	@Override
	public void resume()
	{
		if(ScreenManager.getCurrentScreen() != null)
		{
			ScreenManager.getCurrentScreen().resume();
		}
	}
}
