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

public class GameScreen implements Screen
{
	private OrthographicCamera camera;
	private Viewport viewport;
	private EntityManager manager;
	private WaveManager wmanager;
	private Background background;

	private MainGame game;
	private Music backgroundMusic;

	private int enemyWave;
	private boolean notSpawned;
	private Player player;

	private Wave currentWave;
	private long start;
	private int enemyCount = 0;
	private int spawnDelay;

	public GameScreen(MainGame game)
	{
		this.game = game;
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

		manager = new EntityManager(camera, game, background);
		player = manager.getPlayer();

		wmanager = new WaveManager();

		enemyWave = 0;
		notSpawned = false;

		backgroundMusic = game.manager.get("data/music/background.mp3", Music.class);
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}

	public void update(float delta) {

		camera.update();
		background.update();
		manager.update(delta);

		if(manager.getPlayer().isDead())
		{
			backgroundMusic.stop();
			//gameover screen?
		}
		if(manager.getEnemies().size <= 0) //wave is done
		{
			notSpawned = true;
			enemyWave++;
			currentWave = wmanager.getWave(enemyWave);
			spawnDelay = currentWave.getDelay();
			start = 0;
		}
		if(notSpawned)
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
		if(enemyCount >= currentWave.getNumEnemies())
		{
			notSpawned = false;
			enemyCount=0;
		}

		long elapsed = (System.nanoTime() - start) / 1000000;
		if(elapsed >= spawnDelay)
		{
			//spawn next enemy
			start = System.nanoTime();
			Enemy e = new AttackHeli(manager, 100);
			switch(currentWave.getEnemy(enemyCount).getEnemyCode())
			{
				case WaveManager.BATTLESHIP : e = new AttackHeli(manager, (int)(Math.random()*100+100));
					break;
				case WaveManager.ICARUS : e = new Icarus(manager, (int)(Math.random()*100+100));
					break;
				case WaveManager.SKULLINATOR: e = new Skullinator(manager);
					break;
				case WaveManager.STRIKER: e = new Striker(manager, (int)(Math.random()*100+100));
					break;
			}
			e.setPosition(currentWave.getEnemy(enemyCount).getX()*MainGame.WIDTH, MainGame.HEIGHT + currentWave.getEnemy(enemyCount).getY());
			e.setDirection(currentWave.getEnemy(enemyCount).getDx(), currentWave.getEnemy(enemyCount).getDy());
			manager.spawnEnemy(e);
			enemyCount++;
		}
	}

}
