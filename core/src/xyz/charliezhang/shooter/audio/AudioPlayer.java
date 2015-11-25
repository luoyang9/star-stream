package xyz.charliezhang.shooter.audio;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {
	
	private static HashMap<String, Sound> sounds;
	private static HashMap<String, Music> music;
	
	public static void init() 
	{
		sounds = new HashMap<String, Sound>();
		music = new HashMap<String, Music>();
	}
	
	public static void load(String l, String name, int type) 
	{
		if(type == 1)
		{
			if(sounds.get(name) != null) return;
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("audio/" + l));
			sounds.put(name, sound);
		}
		else if(type == 2)
		{
			if(music.get(name) != null) return;
			Music m = Gdx.audio.newMusic(Gdx.files.internal("audio/" + l));
			music.put(name, m);
		}
	}
	
	public static void play(String s, int type) 
	{
		if(type == 1)
			sounds.get(s).play();
		else if(type == 2)
			music.get(s).play();
	}
	
	public static void stop(String s, int type)
	{
		if(type == 1)
			sounds.get(s).stop();
		else if(type == 2)
			music.get(s).stop();
	}
	
	public static void pause(String s, int type) 
	{
		if(type == 1)
			sounds.get(s).pause();
		else if(type == 2)
			music.get(s).pause();
	}
	
	public static void loop(String s, int type)
	{
		if(type == 1)
			sounds.get(s).loop();
		else if(type == 2)
		{
			music.get(s).play();
			music.get(s).setLooping(true);
		}
	}
	
	public static void dispose(String s, int type) {
		if(type == 1)
			sounds.get(s).dispose();
		else if(type == 2)
			music.get(s).dispose();
	}
	
}