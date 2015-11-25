package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.glass.ui.SystemClipboard;
import xyz.charliezhang.shooter.MainGame;
import xyz.charliezhang.shooter.audio.AudioPlayer;
import xyz.charliezhang.shooter.background.Background;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.Player;
import xyz.charliezhang.shooter.entity.Wave;
import xyz.charliezhang.shooter.entity.WaveManager;
import xyz.charliezhang.shooter.entity.enemies.*;

public class GameScreen extends Screen
{
	protected OrthographicCamera camera;
	protected Viewport viewport;
	protected EntityManager manager;
	protected WaveManager wmanager;
	protected Background background;

	private int enemyWave;
	private boolean notSpawned;
	private Player player;

	private Wave currentWave;
	private long elapsed, start;
	private int enemyCount = 0;
	private int spawnDelay;

	@Override
	public void create() {
		camera = new OrthographicCamera(MainGame.WIDTH, MainGame.HEIGHT);
		viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, camera);
		viewport.apply();
		//viewport.update(480,  800);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		camera.update();

		background = new Background();
		background.setSize(MainGame.WIDTH, MainGame.HEIGHT);
		background.setPosition(0, 0);
		background.setVector(0, -2);

		manager = new EntityManager(camera);
		player = manager.getPlayer();

		wmanager = new WaveManager();

		enemyWave = 0;
		notSpawned = false;

		AudioPlayer.load("background2.mp3", "level1-1", 2);
		AudioPlayer.loop("level1-1", 2);
	}

	@Override
	public void update() {

		camera.update();
		background.update();
		manager.update();

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
	public void render(SpriteBatch sb, BitmapFont font) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		background.render(sb);
		manager.render(sb);

		font.draw(sb, "Max HP: " + player.getMaxHealth() + " Dmg: " + player.getDamage(), 10, 10);
		font.draw(sb, "Lives: " + player.getLives() + " Wave: " + enemyWave, 10, MainGame.HEIGHT-10);
		sb.end();
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

	private void spawnWave()
	{
		if(enemyCount >= currentWave.getNumEnemies())
		{
			notSpawned = false;
			enemyCount=0;
		}

		elapsed = (System.nanoTime()-start) / 1000000;
		if(elapsed >= spawnDelay)
		{
			//spawn next enemy
			start = System.nanoTime();
			Enemy e = new Battleship(manager, 100);
			switch(currentWave.getEnemy(enemyCount).enemyCode)
			{
				case WaveManager.BATTLESHIP : e = new Battleship(manager, (int)(Math.random()*100+50));
					break;
				case WaveManager.ICARUS : e = new Icarus(manager, (int)(Math.random()*100+50));
					break;
				case WaveManager.SKULLINATOR: e = new Skullinator(manager);
					break;
				case WaveManager.STRIKER: e = new Striker(manager, (int)(Math.random()*100+50));
					break;
			}
			e.setPosition(currentWave.getEnemy(enemyCount).x*MainGame.WIDTH, MainGame.HEIGHT + currentWave.getEnemy(enemyCount).y);
			e.setDirection(currentWave.getEnemy(enemyCount).dx, currentWave.getEnemy(enemyCount).dy);
			manager.spawnEnemy(e);
			enemyCount++;
		}

		/*
		for(int i = 0; i < enemyWave / 3 + 1; i++)
		{
			switch(enemyWave % 3)
			{
				case 1:
					Battleship b = new Battleship(manager, (int)(Math.random()*100+50));
					b.setPosition((int)(Math.random()*viewport.getWorldWidth()*0.7 + viewport.getWorldWidth()*0.15), viewport.getWorldHeight() + 500);
					b.setDirection(0, -3);
					manager.spawnEnemy(b);
					break;
				case 2:
					Battleship c = new Battleship(manager, (int)(Math.random()*100+50));
					c.setPosition((int)(Math.random()*viewport.getWorldWidth()*0.7 + viewport.getWorldWidth()*0.15), viewport.getWorldHeight() + 500);
					c.setDirection(0, -3);
					manager.spawnEnemy(c);
					break;
				case 0:
					Battleship d = new Battleship(manager, (int)(Math.random()*100+50));
					d.setPosition((int)(Math.random()*viewport.getWorldWidth()*0.7 + viewport.getWorldWidth()*0.15), viewport.getWorldHeight() + 500);
					d.setDirection(0, -3);
					manager.spawnEnemy(d);
					break;
			}
		}
		*/
	}

}
