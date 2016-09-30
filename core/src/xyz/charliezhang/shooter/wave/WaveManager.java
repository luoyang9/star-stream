package xyz.charliezhang.shooter.wave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.enemies.*;

public class WaveManager
{
    private Level level;
    private Wave currWave;
    private WaveEnemy currEnemy;
    private EntityManager manager;

    private int waveIndex;
    private int enemyIndex;
    private boolean spawning;
    private long start;


    private static final int UFO = 1;
    private static final int ICARUS = 2;
    private static final int SKULLINATOR = 3;
    private static final int STRIKER = 4;
    private static final int KAMIKAZE = 5;
    private static final int ASTEROID = 6;
    private static final int VALKYRIE = 7;

    public WaveManager(int levelNum, EntityManager manager)
    {
        this.manager = manager;
        level = new Level();
        waveIndex = 8;
        enemyIndex = 0;
        spawning = false;
        createWaves(levelNum);
    }

    public void update() {
        if(spawning) {
            if(enemyIndex >= currWave.getNumEnemies()) { //all enemies done spawning
                spawning = false;
                enemyIndex = 0;
                return;
            }

            currEnemy = currWave.getEnemy(enemyIndex);
            long elapsed = (System.nanoTime() - start) / 1000000;
            int spawnDelay = currEnemy.getDelay();

            if(elapsed >= spawnDelay)
            {
                //spawn next enemy
                start = System.nanoTime();
                Enemy e;
                switch(currEnemy.getId())
                {
                    case UFO  : e = new UFO(manager, (int)(Math.random()*4 + 1));
                        break;
                    case ICARUS : e = new Icarus(manager, (int)(Math.random()*3 + 1));
                        break;
                    case SKULLINATOR : e = new Skullinator(manager);
                        break;
                    case STRIKER : e = new Striker(manager);
                        break;
                    case KAMIKAZE : e = new Kamikaze(manager);
                        break;
                    case ASTEROID : e = new Asteroid(manager);
                        break;
                    case VALKYRIE : e = new Valkyrie(manager);
                        break;
                    default: e = new UFO(manager, (int)(Math.random()*4 + 1));
                }
                e.setPosition(currEnemy.getX()*manager.getViewport().getWorldWidth() - e.getSprite().getWidth()/2, manager.getViewport().getWorldHeight() + currEnemy.getY());
                e.setDirection(currEnemy.getDx(), currEnemy.getDy());
                e.setStop(currEnemy.getStop());
                manager.spawnEnemy(e);
                enemyIndex++;
            }
        }
    }

    public boolean spawnNextWave() {
        //if current wave doesn't exist
        if(waveIndex >= level.getNumWaves()) {
            waveIndex = 0;
            enemyIndex = 0;
            spawning = false;
            return false;
        }

        spawning = true;
        currWave = level.getWave(waveIndex);
        start = System.nanoTime();
        waveIndex++;
        return true;
    }

    private void createWaves(int levelNum)
    {
        FileHandle handle = Gdx.files.internal("data/waves/level" + levelNum + ".wvs");
        String levelJson = handle.readString();

        Json json = new Json();
        json.addClassTag("wave", Wave.class);
        json.addClassTag("enemy", WaveEnemy.class);

        level = json.fromJson(Level.class, levelJson);
    }

    public boolean isSpawning() {return spawning;}
}
