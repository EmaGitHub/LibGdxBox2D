package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Application;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 270;					//1080;
		config.height = 480;				//1920;
		config.backgroundFPS = 60;
		config.foregroundFPS = 60;
		config.resizable = true;
		new LwjglApplication(new Application(), config);
	}
}
