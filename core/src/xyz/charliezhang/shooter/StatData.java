package xyz.charliezhang.shooter;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class StatData 
{
	private static FileHandle playerStats = Gdx.files.local("playerstats.txt");
	private static String[] readStats;
	public static int playerHealth;
	public static int playerDamage;
	public static int APUDuration;
	public static int playerCash;
	
	public static void checkNewPlayer() throws IOException
	{
		if(!Gdx.files.local("playerstats.txt").exists())
		{
			Gdx.files.local("playerstats.txt").file().createNewFile();
			writeStats(10, 1, 2000, 0);
		}
	}
	
	public static void readStats()
	{
		readStats = playerStats.readString().split(" ");
		playerHealth = Integer.parseInt(readStats[0]);
		playerDamage = Integer.parseInt(readStats[1]);
		APUDuration = Integer.parseInt(readStats[2]);
		playerCash = Integer.parseInt(readStats[3]);
	}
	
	public static void healthUpgrade(int h)
	{
		playerHealth += h;
		writeStats(playerHealth, playerDamage, APUDuration, playerCash);
	}	
	public static void damageUpgrade(int h)
	{
		playerDamage += h;
		writeStats(playerHealth, playerDamage, APUDuration, playerCash);
	}	
	public static void APUUpgrade(int h)
	{
		APUDuration += h;
		writeStats(playerHealth, playerDamage, APUDuration, playerCash);
	}
	public static void addCash(int c)
	{
		playerCash+=c;
		writeStats(playerHealth, playerDamage, APUDuration, playerCash);
	}
	
	public static void writeStats(int health, int damage, int APU, int cash)
	{
		playerStats.writeString(health+ " ", false);
		playerStats.writeString(damage + " ", true);
		playerStats.writeString(APU+ " ", true);
		playerStats.writeString(cash+ " ", true);
	}
}
