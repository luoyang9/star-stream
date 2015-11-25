package xyz.charliezhang.shooter.screen;

import com.badlogic.gdx.utils.Array;

public class ScreenManager 
{
	private static Screen currentScreen;
	
	public ScreenManager()
	{
	}
	
	public static void setScreen(Screen screen)
	{
		if(currentScreen != null)
		{
			currentScreen.dispose();
		}
		currentScreen = screen; 
		currentScreen.create();
	}
	
	public static Screen getCurrentScreen() {return currentScreen;}
}
