package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.shooter.audio.AudioPlayer;
import xyz.charliezhang.shooter.entity.enemies.Enemy;
import xyz.charliezhang.shooter.entity.enemies.EnemyLaser;
import xyz.charliezhang.shooter.entity.powerup.AttackPowerUp;
import xyz.charliezhang.shooter.entity.powerup.PowerUp;

public class WaveManager
{
    private Array<Wave> waves;

    public static final int BATTLESHIP = 1;
    public static final int ICARUS = 2;
    public static final int SKULLINATOR = 3;
    public static final int STRIKER = 4;



    public WaveManager()
    {
        waves = new Array<Wave>();
        createWaves();
    }

    public Wave getWave(int enemyWave)
    {
        if(enemyWave >= waves.size) return waves.get(waves.size-1);
        else return waves.get(enemyWave);
    }

    private Wave getRandomWave(int enemyWave)
    { //todo generate random waves based on enemy waves
        int maxWaves = waves.size;
        if(enemyWave < 2) return waves.get(enemyWave);
    }

    private void createWaves()
    {
        int[] enemies = {1,4,1};
        Vector2 position[] = new Vector2[3];
        position[0] = new Vector2(0.3f, 300);
        position[1] = new Vector2(0.5f, 300);
        position[2] = new Vector2(0.7f, 300);
        Vector2 direction[] = new Vector2[3];
        direction[0] = new Vector2(0, -2);
        direction[1] = new Vector2(0, -2);
        direction[2] = new Vector2(0, -2);
        waves.add(new Wave(enemies, position, direction, 1000));
    }
}
