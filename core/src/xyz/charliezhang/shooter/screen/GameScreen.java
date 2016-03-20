package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.background.Background;
import xyz.charliezhang.shooter.entity.*;
import xyz.charliezhang.shooter.entity.enemies.*;
import xyz.charliezhang.shooter.music.MusicPlayer;

public class GameScreen implements Screen
{
	private OrthographicCamera camera;
	private Viewport viewport;
	private EntityManager manager;
	private WaveManager wmanager;
	private Background background;
	private int level;

	private MainGame game;

	private int enemyWave;
	private boolean notSpawned;

	private Wave currentWave;
	private int enemyCount;
	private int spawnDelay;
	private long start;

	private boolean win;

	public GameScreen(MainGame game, int level)
	{
		this.game = game;
		this.level = level;

		win = false;
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

		wmanager = new WaveManager(level);

		enemyWave = 0;
		enemyCount = 0;
		notSpawned = false;

		MusicPlayer.loadMusic("game", Assets.manager.get("data/music/background.ogg", Music.class));
		MusicPlayer.loadMusic("win", Assets.manager.get("data/music/win.mp3", Music.class));
		MusicPlayer.loop("game");
	}

	public void update(float delta) {

		camera.update();
		background.update();
		manager.update(delta);

		if(manager.getEnemies().size <= 0) //wave is done
		{
			enemyWave++;
			notSpawned = true;
			//check player won
			if(wmanager.allWavesCleared(enemyWave) && !win)
			{
				MusicPlayer.stop("game");
				MusicPlayer.loop("win");
				manager.win();
				win = true;
			}
			else
			{
				currentWave = wmanager.getWave(enemyWave);
				start = 0;
			}
		}
		if(notSpawned && !win)
		{
			spawnWave();
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

	private void spawnWave()
	{
		if(enemyCount >= currentWave.getNumEnemies()) //all enemies spawned
		{
			notSpawned = false;
			enemyCount=0;
			return;
		}

		long elapsed = (System.nanoTime() - start) / 1000000;
		spawnDelay = currentWave.getEnemy(enemyCount).getDelay();
		if(elapsed >= spawnDelay)
		{
			//spawn next enemy
			start = System.nanoTime();
			Enemy e;
			switch(currentWave.getEnemy(enemyCount).getEnemyCode())
			{
				case WaveManager.ATTACKHELI : e = new UFO(manager, (int)(Math.random()*4 + 1));
					break;
				case WaveManager.ICARUS : e = new Icarus(manager, (int)(Math.random()*3 + 1));
					break;
				case WaveManager.SKULLINATOR: e = new Skullinator(manager);
					break;
				case WaveManager.STRIKER: e = new Striker(manager);
					break;
				case WaveManager.KAMIKAZE: e = new Kamikaze(manager);
					break;
				case WaveManager.ASTEROID: e = new Asteroid(manager);
					break;
				default: e = new UFO(manager, (int)(Math.random()*4 + 1));
			}
			e.setPosition(currentWave.getEnemy(enemyCount).getX()*viewport.getWorldWidth() - e.getSprite().getWidth()/2, viewport.getWorldHeight() + currentWave.getEnemy(enemyCount).getY());
			e.setDirection(currentWave.getEnemy(enemyCount).getDx(), currentWave.getEnemy(enemyCount).getDy());
			e.setStop(currentWave.getEnemy(enemyCount).getStop());
			manager.spawnEnemy(e);
			enemyCount++;
		}
	}

}
