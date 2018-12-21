package me.charliezhang.starstream.wave;

import com.badlogic.gdx.utils.Array;

class Level {
    private Array<Wave> waves;
    private int index;

    Level()
    {
        waves = new Array<Wave>();
        index = 0;
    }

    Wave getNextWave(){
        return waves.get(index++);
    }
    boolean hasMoreWaves() {return index < waves.size;}
}
