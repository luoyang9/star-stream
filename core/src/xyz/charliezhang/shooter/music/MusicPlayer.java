package xyz.charliezhang.shooter.music;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Created by Charlie on 2016-01-09.
 */
public class MusicPlayer {

    public static float VOLUME = 1.0f;

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

    public static void loop(String name)
    {
        stop(name);
        musicMap.get(name).setLooping(true);
        musicMap.get(name).setVolume(VOLUME);
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
        musicMap.get(name).setVolume(VOLUME);
        musicMap.get(name).play();
    }

    public static void mute()
    {
        VOLUME = 0f;
        for(HashMap.Entry<String, Music> entry : musicMap.entrySet())
        {
            stop(entry.getKey());
        }
    }

    public static void unmute(String music)
    {
        VOLUME = 1.0f;
        loop(music);
    }
}
