package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AppGame;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 360;					//1440;
		config.height = 640;				//2560;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.resizable = true;
		new LwjglApplication(new AppGame(), config);
	}
}
