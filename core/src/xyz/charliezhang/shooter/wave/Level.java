package xyz.charliezhang.shooter.wave;

import com.badlogic.gdx.utils.Array;

class Level {
    private Array<Wave> waves;

    Level()
    {
        waves = new Array<Wave>();
    }

    Wave getWave(int i){return waves.get(i);}
    int getNumWaves() {return waves.size;}
}
