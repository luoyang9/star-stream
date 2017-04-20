package xyz.charliezhang.starstream.shop;

import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.starstream.GameData;

/**
 * Created by Charlie Zhang on 3/11/2017.
 */
public class Upgrade {

    private String name;
    private int value;

    public Upgrade(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Upgrade(String name) {
        this.name = name;
        this.value = 0;
    }

    public String getName() { return name; }
    public int getValue() { return value; }

}
