package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.background.Background;
import xyz.charliezhang.shooter.entity.*;
import xyz.charliezhang.shooter.entity.wave.WaveManager;
import xyz.charliezhang.shooter.music.MusicPlayer;

class GameScreen implements Screen
{
	private OrthographicCamera camera;
	private Viewport viewport;
	private EntityManager manager;
	private WaveManager waveManager;
	private Background background;
	private int level;
	private boolean win;

	private MainGame game;

	GameScreen(MainGame game, int level)
	{
		this.game = game;
		this.level = level;
	}

	@Override
	public void show() {
		camera = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);
		viewport = new ExtendViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		camera.update();

		background = new Background();
		background.setVector(0, -2f);

		manager = new EntityManager(viewport, game, background, level);
		waveManager = new WaveManager(level, manager);
		waveManager.spawnNextWave();

		win = false;

		MusicPlayer.loop("game");
	}

	public void update(float delta) {

		camera.update();
		manager.update(delta);
		waveManager.update();

		//if game is paused
		if(manager.isPaused()) return;

		background.update();

		if(win)
		{
			if(manager.getPlayer().getPosition().y > viewport.getWorldHeight() + 500)
			{
				game.setScreen(new WinScreen(game, manager.getScore(), manager.getPlayer().getLives(), manager.getTime(), level));
				dispose();
			}
			return;
		}

		if(manager.getEnemies().size == 0 && !waveManager.isSpawning()) //all enemies spawned and killed
		{
			if(!waveManager.spawnNextWave()) //spawn next wave
			{
				//there is no more waves, you win
				MusicPlayer.loop("win");
				manager.win();
				win = true;
			}
		}
	}
	
	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		background.render(game.batch);
		manager.render(game.batch);
		game.batch.end();
	}


	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height, true);
		background.setSize(viewport.getWorldWidth());
		manager.updateViewport(viewport);
	}

	@Override
	public void dispose() {
		manager.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}
}
