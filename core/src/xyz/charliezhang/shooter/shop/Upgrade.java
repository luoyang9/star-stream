package xyz.charliezhang.shooter.shop;

/**
 * Created by Charlie Zhang on 3/11/2017.
 */
public class Upgrade {
    public static String[] UpgradeTypes = {
            "upgrade-health" ,
            "upgrade-damage"
    };

    private String name;
    private int value;

    public Upgrade(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }
    public int getValue() { return value; }
}
