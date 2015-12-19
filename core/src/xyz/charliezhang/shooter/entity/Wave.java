package xyz.charliezhang.shooter.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.shooter.entity.enemies.Battleship;
import xyz.charliezhang.shooter.entity.enemies.Enemy;

import java.util.HashMap;

/**
 * Created by Charlie on 2015-11-24.
 */
public class Wave
{
    private Array<WaveEnemy> enemies;
    private int delay;

    public Wave()
    {
    }

    public void setEnemies(Array<WaveEnemy> enemies)
    {
        this.enemies = enemies;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public int getDelay(){return delay;}
    public WaveEnemy getEnemy(int i){return enemies.get(i);}
    public int getNumEnemies() {return enemies.size;}
}
