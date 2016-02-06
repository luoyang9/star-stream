package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;
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
	private Player player;

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
		viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);
		viewport.apply();
		//viewport.update(480,  800);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		camera.update();

		background = new Background(game);
		background.setVector(0, -0.5f);

		manager = new EntityManager(camera, game, background, level);
		player = manager.getPlayer();

		wmanager = new WaveManager(level);

		enemyWave = 0;
		enemyCount = 0;
		notSpawned = false;

		MusicPlayer.loadMusic("game", game.manager.get("data/music/background.ogg", Music.class));
		MusicPlayer.loadMusic("win", game.manager.get("data/music/win.mp3", Music.class));
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
			if(wmanager.allWavesCleared(enemyWave))
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
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
				case WaveManager.ICARUS : e = new Icarus(manager);
					break;
				case WaveManager.SKULLINATOR: e = new Skullinator(manager);
					break;
				case WaveManager.STRIKER: e = new Striker(manager);
					break;
				case WaveManager.KAMIKAZE: e = new Kamikaze(manager);
					break;
				default: e = new UFO(manager, (int)(Math.random()*4 + 1));
			}
			e.setPosition(currentWave.getEnemy(enemyCount).getX()*MainGame.WIDTH, MainGame.HEIGHT + currentWave.getEnemy(enemyCount).getY());
			e.setDirection(currentWave.getEnemy(enemyCount).getDx(), currentWave.getEnemy(enemyCount).getDy());
			e.setStop(currentWave.getEnemy(enemyCount).getStop());
			manager.spawnEnemy(e);
			enemyCount++;
		}
	}

}
