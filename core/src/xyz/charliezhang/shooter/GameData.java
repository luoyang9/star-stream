package xyz.charliezhang.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import xyz.charliezhang.shooter.entity.Entity;
import xyz.charliezhang.shooter.entity.EntityManager;
import xyz.charliezhang.shooter.music.MusicPlayer;

/**
 * Created by Charlie on 2016-02-05.
 */
public class GameData {
    private static FileHandle userData;
    private static UserObject user;
    private static Json json;


    public static void init()
    {
        json = new Json();
        json.setUsePrototypes(false);
        userData = Gdx.files.local("userData.json");
        if (userData.exists()) {
            user = json.fromJson(UserObject.class, userData.readString());
        } else {
            user = new UserObject();
            userData.writeString(json.toJson(user), false);
        }

        if(!user.soundOn)
        {
            MusicPlayer.mute();
        }
    }


    public static void updateScore(int level, int score)
    {
        user.setScore(level-1, score);
        userData.writeString(json.toJson(user), false);
    }

    public static void updateSoundOn(boolean b)
    {
        user.setSoundOn(b);
        userData.writeString(json.toJson(user), false);
    }

    public static void setPlayerType(int type)
    {
        if(user.getAvailableTypes()[type])
        {
            user.setPlayerType(type);
            userData.writeString(json.toJson(user), false);
        }
    }

    public static int getScore(int level)
    {
        return user.getScore(level-1);
    }
    public static int getPlayerType()
    {
        return user.getPlayerType();
    }
    public static boolean[] getAvailableTypes()
    {
        return user.getAvailableTypes();
    }
    public static boolean soundOn()
    {
        return user.soundOn();
    }


    private static class UserObject {
        private int[] score;
        private boolean[] availableTypes;
        private int playerType;
        private boolean soundOn;

        public UserObject()
        {
            score = new int[MainGame.NUM_LEVELS];
            availableTypes = new boolean[EntityManager.NUM_TYPES];
            soundOn = true;
            for(int i = 0; i < MainGame.NUM_LEVELS; i++)
            {
                score[i] = 0;
            }
            for(int i = 0; i < EntityManager.NUM_TYPES; i++)
            {
                availableTypes[i] = true;
            }
            availableTypes[EntityManager.PLAYER_BLUE] = true;
            availableTypes[EntityManager.PLAYER_RED] = true;
            playerType = EntityManager.PLAYER_BLUE;
        }

        public void setScore(int index, int val)
        {
            score[index] = val;
        }
        public void setSoundOn(boolean b) {soundOn = b;}
        public void setPlayerType(int type) {playerType = type;}

        public int getScore(int index)
        {
            return score[index];
        }
        public int getPlayerType()
        {
            return playerType;
        }
        public boolean[] getAvailableTypes() {return availableTypes;}
        public boolean soundOn() {return soundOn;}
    }
}
