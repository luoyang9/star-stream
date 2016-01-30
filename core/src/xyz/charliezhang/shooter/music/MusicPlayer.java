package xyz.charliezhang.shooter.music;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by Charlie on 2016-01-09.
 */
public class MusicPlayer {

    private static HashMap<String, Music> musicMap;

    public static void init()
    {
        musicMap = new HashMap<String, Music>();
    }

    public static void loadMusic(String name, Music music)
    {
        musicMap.put(name, music);
    }

    public static boolean loaded(String name)
    {
        return musicMap.containsKey(name);
    }

    public static boolean isPlaying(String name)
    {
        return musicMap.get(name).isPlaying();
    }

    public static void play(String name)
    {
        stop(name);
        musicMap.get(name).play();
    }

    public static void loop(String name)
    {
        stop(name);
        musicMap.get(name).setLooping(true);
        musicMap.get(name).play();
    }

    public static void stop(String name)
    {
        musicMap.get(name).stop();
    }

    public static void pause(String name)
    {
        musicMap.get(name).pause();
    }

    public static void resume(String name)
    {
        musicMap.get(name).play();
    }
}
