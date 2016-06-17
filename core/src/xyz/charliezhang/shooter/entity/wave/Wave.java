package xyz.charliezhang.shooter.entity.wave;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Charlie on 2015-11-24.
 */
public class Wave
{
    private Array<WaveEnemy> enemies;
    private int delay;

    public Wave()
    {
        enemies = new Array<WaveEnemy>();
    }

    public int getDelay(){return delay;}
    public WaveEnemy getEnemy(int i){return enemies.get(i);}
    public int getNumEnemies() {return enemies.size;}
}
