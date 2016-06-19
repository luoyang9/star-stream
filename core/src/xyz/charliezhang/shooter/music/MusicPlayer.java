package xyz.charliezhang.shooter.music;

import com.badlogic.gdx.audio.Music;
import xyz.charliezhang.shooter.Assets;
import xyz.charliezhang.shooter.GameData;

import java.util.HashMap;

public class MusicPlayer {

    public static float VOLUME = 1.0f;

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
        musicMap.put("menu", Assets.manager.get("data/music/menu.ogg", Music.class));
        musicMap.put("game", Assets.manager.get("data/music/background.ogg", Music.class));
        musicMap.put("win", Assets.manager.get("data/music/win.mp3", Music.class));
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
        if(currentMusic != null && !currentMusic.isEmpty()) {
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
        if(currentMusic != null && !currentMusic.isEmpty()) {
            stop(currentMusic);
        }
    }

    public static void unmute(String music)
    {
        VOLUME = 1.0f;
        loop(music);
    }
}
