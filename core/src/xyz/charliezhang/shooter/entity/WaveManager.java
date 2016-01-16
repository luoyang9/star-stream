package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

public class WaveManager
{
    private Array<Wave> waves;

    public static final int ATTACKHELI = 1;
    public static final int ICARUS = 2;
    public static final int SKULLINATOR = 3;
    public static final int STRIKER = 4;
    public static final int KAMIKAZE = 5;

    public WaveManager(int level)
    {
        waves = new Array<Wave>();
        createWaves(level);
    }

    public Wave getWave(int enemyWave)
    {
        if(enemyWave <= waves.size) return waves.get(enemyWave-1);
        else return new Wave();
    }

    private Wave getRandomWave(int enemyWave)
    { //todo generate random waves based on enemy wave number
        int maxWaves = waves.size;
        return waves.get(enemyWave);
    }

    private void createWaves(int level)
    {
        FileHandle handle = Gdx.files.internal("data/waves/level" + level + ".wvs");
        String temp = handle.readString();
        String[] waveJson = temp.split(";");
        Json json = new Json();
        json.setElementType(Wave.class, "enemies", WaveEnemy.class);

        for(int i = 0; i < waveJson.length; i++)
        {
            Wave wave = json.fromJson(Wave.class, waveJson[i]);
            waves.add(wave);
        }
    }

    public boolean allWavesCleared(int enemyWave)
    {
        return enemyWave > waves.size;
    }
}
