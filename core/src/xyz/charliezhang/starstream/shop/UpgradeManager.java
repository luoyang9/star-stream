package xyz.charliezhang.starstream.shop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.starstream.GameData;

/**
 * Created by Charlie Zhang on 4/19/2017.
 */
public class UpgradeManager {

    public static String[] UpgradeTypes = {
            "health" ,
            "damage",
            "missile",
            "super att"
    };

    public static int[] UpgradeMax = {
            10,
            5,
            5,
            5
    };

    public static int upgrade(String upgrade) {
        int oldVal = 0;
        if(GameData.prefs.contains(upgrade)) {
            oldVal = GameData.prefs.getInteger(upgrade);
        }
        long newMoney = GameData.prefs.getLong("money") - getUpgradeCost(new Upgrade(upgrade, oldVal));
        for(int i = 0; i < UpgradeTypes.length; i++) {
            if(upgrade.equals(UpgradeTypes[i]) && oldVal < UpgradeMax[i]) {
                int newVal = oldVal + 1;
                GameData.prefs.putInteger(upgrade, newVal);
                GameData.prefs.putLong("money", newMoney).flush();
                return newVal;
            }
        }
        return oldVal;
    }

    public static long getUpgradeCost(Upgrade u) {
        int v = u.getValue();
        if(u.getName().equals("health")) {
            return (10 + v) * 10;
        } else if(u.getName().equals("damage") || u.getName().equals("missile") || u.getName().equals("super att")) {
            return (1 + v) * 100;
        }
        System.out.println("Unknown upgrade was encountered: " + u.getName());
        return 0;
    }

    public static Array<Upgrade> getPlayerUpgrades() {
        Array<Upgrade> upgrades = new Array<Upgrade>();
        for(String u : UpgradeTypes) {
            if(GameData.prefs.contains(u)) {
                upgrades.add(new Upgrade(u, GameData.prefs.getInteger(u)));
            }
        }
        return upgrades;
    }

    public static Array<Upgrade> getAllUpgrades() {
        Array<Upgrade> upgrades = new Array<Upgrade>();
        for(String u : UpgradeTypes) {
            if(GameData.prefs.contains(u)) {
                upgrades.add(new Upgrade(u, GameData.prefs.getInteger(u)));
            } else {
                upgrades.add(new Upgrade(u));
            }
        }
        return upgrades;
    }
}
