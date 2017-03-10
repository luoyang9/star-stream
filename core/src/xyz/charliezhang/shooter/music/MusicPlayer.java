package xyz.charliezhang.shooter.music;

import com.badlogic.gdx.audio.Music;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.GameData;

import java.util.HashMap;

import static xyz.charliezhang.shooter.Config.GAME_MUSIC_PATH;
import static xyz.charliezhang.shooter.Config.MENU_MUSIC_PATH;
import static xyz.charliezhang.shooter.Config.WIN_MUSIC_PATH;

public class MusicPlayer {

    public static float VOLUME = 1.0f;
    public static String MENU = "menu";
    public static String GAME = "game";
    public static String WIN = "win";

    private static HashMap<String, Music> musicMap;
    private static String currentMusic;
    public static void init()
    {
        musicMap = new HashMap<String, Music>();
        if(!GameData.prefs.getBoolean("soundOn", true)) {
            mute();
        }
    }

    public static void load() {
        musicMap.put(MENU, Assets.manager.get(MENU_MUSIC_PATH, Music.class));
        musicMap.put(GAME, Assets.manager.get(GAME_MUSIC_PATH, Music.class));
        musicMap.put(WIN, Assets.manager.get(WIN_MUSIC_PATH, Music.class));
    }

    private static void loadMusic(String name, Music music)
    {
        musicMap.put(name, music);
    }

    public static boolean isPlaying(String name)
    {
        return musicMap.get(name).isPlaying();
    }

    public static void loop(String name)
    {
        if(currentMusic != null && currentMusic.length() != 0) {
            stop(currentMusic);
        }
        currentMusic = name;
        musicMap.get(name).setLooping(true);
        musicMap.get(name).setVolume(VOLUME);
        musicMap.get(name).play();
    }

    public static void stop(String name)
    {
        musicMap.get(name).stop();
        currentMusic = null;
    }

    public static void pause(String name)
    {
        musicMap.get(name).pause();
    }

    public static void resume(String name)
    {
        musicMap.get(name).setVolume(VOLUME);
        musicMap.get(name).play();
    }

    public static void mute()
    {
        VOLUME = 0f;
        if(currentMusic != null && currentMusic.length() != 0) {
            stop(currentMusic);
        }
    }

    public static void unmute(String music)
    {
        VOLUME = 1.0f;
        loop(music);
    }
}
