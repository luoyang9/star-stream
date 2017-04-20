package xyz.charliezhang.starstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import xyz.charliezhang.starstream.entity.EntityManager;
import xyz.charliezhang.starstream.shop.Upgrade;

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
        for(int i = 0; i < xyz.charliezhang.starstream.MainGame.NUM_LEVELS; i++) {
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
        prefs.putInteger("health", 5);
        prefs.putInteger("damage", 2);
        prefs.putBoolean("type-" + EntityManager.PLAYER_BLUE, true);
        prefs.putBoolean("type-" + EntityManager.PLAYER_RED, true);
    }

}
