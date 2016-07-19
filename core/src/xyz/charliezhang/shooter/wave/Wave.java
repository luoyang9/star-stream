package xyz.charliezhang.shooter.wave;

import com.badlogic.gdx.utils.Array;

class Wave
{
    private Array<WaveEnemy> enemies;
    private int delay;

    public Wave()
    {
        enemies = new Array<WaveEnemy>();
    }

    public int getDelay(){return delay;}
    WaveEnemy getEnemy(int i){return enemies.get(i);}
    int getNumEnemies() {return enemies.size;}
}
