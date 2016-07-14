package xyz.charliezhang.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import xyz.charliezhang.shooter.entity.Entity;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.music.MusicPlayer;

public class GameData {

    public static Preferences prefs = Gdx.app.getPreferences("My Preferences");

    static void init()
    {
        if (!prefs.getBoolean("init")) {
            initPrefs();
        }
    }

    private static void initPrefs() {
        prefs.putBoolean("init", true);
        prefs.putBoolean("soundOn", true);
        for(int i = 0; i < MainGame.NUM_LEVELS; i++) {
            prefs.putInteger("level-" + (i+1), 0);
        }
        for(int i = 0; i < EntityManager.NUM_TYPES; i++) {
            prefs.putBoolean("type-" + (i+1), false);
        }
        prefs.putInteger("playerType", EntityManager.PLAYER_BLUE);

        //dev stuff, delete for live
        prefs.putBoolean("type-" + EntityManager.PLAYER_BLUE, true);
        prefs.putBoolean("type-" + EntityManager.PLAYER_RED, true);
    }

}
