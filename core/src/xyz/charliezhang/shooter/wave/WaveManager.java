package xyz.charliezhang.shooter.wave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.entity.enemies.*;

public class WaveManager
{
    private Level level;
    private Wave currentWave;
    private Enemy currentEnemy;

    private EntityManager manager;

    private boolean spawning;
    private long delayStartTime;

    public WaveManager(int levelNum, EntityManager manager)
    {
        this.manager = manager;
        spawning = false;
        createLevel(levelNum);
    }

    public void update() {
        if(spawning) {
            long delayElapsedTime = (System.nanoTime() - delayStartTime) / 1000000;
            if(delayElapsedTime >=  currentEnemy.getSpawnDelay())
            {
                delayStartTime = System.nanoTime();

                spawnNextEnemy();
            }
        }
    }

    private void spawnNextEnemy() {
        manager.spawnEnemy(currentEnemy);

        //if all enemies spawned
        if(!currentWave.hasMoreEnemies()) {
            spawning = false;
        }
        else {
            currentEnemy = currentWave.getNextEnemy();
        }
    }

    public boolean spawnNextWave() {
        //if no more waves
        if(!level.hasMoreWaves()) return false;

        spawning = true;
        currentWave = level.getNextWave();
        currentEnemy = currentWave.getNextEnemy();
        delayStartTime = System.nanoTime();
        return true;
    }

    private void createLevel(int levelNum)
    {
        FileHandle handle = Gdx.files.internal("data/waves/level" + levelNum + ".lvl");
        String levelJSON = handle.readString();

        Json json = new Json();
        level = json.fromJson(Level.class, levelJSON);
    }

    public boolean isSpawning() {return spawning;}
}
