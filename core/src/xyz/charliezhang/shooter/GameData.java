package xyz.charliezhang.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.shop.Upgrade;

public class GameData {

    public static Preferences prefs = Gdx.app.getPreferences("My Preferences");

    static void init()
    {
        if (!prefs.contains("init")) {
            initPrefs();
        }
    }

    private static void initPrefs() {
        prefs.putBoolean("init", true);

        // settings
        prefs.putBoolean("soundOn", true);

        // high scores
        for(int i = 0; i < MainGame.NUM_LEVELS; i++) {
            prefs.putInteger("level-" + (i+1), 0);
        }

        // unlocked ships
        for(int i = 0; i < EntityManager.NUM_TYPES; i++) {
            prefs.putBoolean("type-" + (i+1), false);
        }

        // current ship
        prefs.putInteger("playerType", EntityManager.PLAYER_BLUE);

        // money
        prefs.putLong("money", 0);

        //dev stuff, delete for live
        prefs.putInteger("upgrade-health", 5);
        prefs.putInteger("upgrade-damage", 2);
        prefs.putBoolean("type-" + EntityManager.PLAYER_BLUE, true);
        prefs.putBoolean("type-" + EntityManager.PLAYER_RED, true);
    }

    public static Array<Upgrade> getPlayerUpgrades() {
        Array<Upgrade> upgrades = new Array<Upgrade>();
        for(String u : Upgrade.UpgradeTypes) {
            if(prefs.contains(u)) {
                upgrades.add(new Upgrade(u, prefs.getInteger(u)));
            }
        }
        return upgrades;
    }

}
