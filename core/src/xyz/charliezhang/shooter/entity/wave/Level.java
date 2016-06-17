package xyz.charliezhang.shooter.entity.wave;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Charlie on 2016-06-05.
 */
public class Level {
    private Array<Wave> waves;

    public Level()
    {
        waves = new Array<Wave>();
    }

    public Wave getWave(int i){return waves.get(i);}
    public int getNumWaves() {return waves.size;}
}
