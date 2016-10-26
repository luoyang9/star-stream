package xyz.charliezhang.shooter.wave;

import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.shooter.entity.enemies.Enemy;

class Wave
{
    private Array<Enemy> enemies;
    private int index;

    public Wave()
    {
        enemies = new Array<Enemy>();
        index = 0;
    }

    Enemy getNextEnemy() {
        return enemies.get(index++);
    }
    boolean hasMoreEnemies() { return index < enemies.size; }
}
