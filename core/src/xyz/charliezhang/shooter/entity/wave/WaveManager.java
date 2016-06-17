package xyz.charliezhang.shooter.entity.wave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class WaveManager
{
    private Level level;

    public static final int UFO = 1;
    public static final int ICARUS = 2;
    public static final int SKULLINATOR = 3;
    public static final int STRIKER = 4;
    public static final int KAMIKAZE = 5;
    public static final int ASTEROID = 6;

    public WaveManager(int levelNum)
    {
        level = new Level();
        createWaves(levelNum);
    }

    public Wave getWave(int enemyWave)
    {
        if(enemyWave <= level.getNumWaves()) return level.getWave(enemyWave-1);
        else return new Wave();
    }

    private Wave getRandomWave(int enemyWave)
    { //todo generate random waves based on enemy wave number
        int maxWaves = level.getNumWaves();
        return level.getWave(enemyWave);
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

    public boolean allWavesCleared(int enemyWave)
    {
        return enemyWave > level.getNumWaves();
    }
}
