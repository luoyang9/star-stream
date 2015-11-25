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

    public Wave(int[] enemyCodes, Vector2[] enemyPositions, Vector2[] enemyDirections, int delay)
    {
        this.delay = delay;
        enemies = new Array<WaveEnemy>();
        for(int i = 0; i < enemyCodes.length; i++)
        {
            enemies.add(new WaveEnemy());
            enemies.get(i).enemyCode = enemyCodes[i];
            enemies.get(i).x = enemyPositions[i].x;
            enemies.get(i).y = enemyPositions[i].y;
            enemies.get(i).dx = enemyDirections[i].x;
            enemies.get(i).dy = enemyDirections[i].y;
        }
    }

    public int getDelay(){return delay;}
    public WaveEnemy getEnemy(int i){return enemies.get(i);}
    public int getNumEnemies() {return enemies.size;}
}
