package xyz.charliezhang.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

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
        userData = Gdx.files.local("userData.json");
        if (userData.exists()) {
            user = json.fromJson(UserObject.class, userData.readString());
        } else {
            user = new UserObject();
            userData.writeString(json.toJson(user), false);
        }
    }


    public static void updateScore(int level, int score)
    {
        user.setScore(level-1, score);
        userData.writeString(json.toJson(user), false);
    }

    public static int getScore(int level)
    {
        return user.getScore(level-1);
    }


    private static class UserObject {
        private int[] score;

        public UserObject()
        {
            score = new int[MainGame.NUM_LEVELS];
            for(int i = 0; i < MainGame.NUM_LEVELS; i++)
            {
                score[i] = 0;
            }
        }

        public void setScore(int index, int val)
        {
            score[index] = val;
        }

        public int getScore(int index)
        {
            return score[index];
        }
    }
}
