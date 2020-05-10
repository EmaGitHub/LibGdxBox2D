package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.HomeScreen;
import com.mygdx.game.Utils.GlobalVar;

public class AppGame extends Game {

	float screenWidth, screenHeight;
	public OrthographicCamera camera;
	private int scale;
	public Viewport viewport;
	public SpriteBatch batch;
	public Screen homeScreen;

	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		GlobalVar.safeAreaInsetTop = getIOSSafeAreaInsets().x;									// support iPhone X
		GlobalVar.safeAreaInsetBottom = getIOSSafeAreaInsets().y;

		// Screen aspect ratio definition
		float PPM = screenWidth / GlobalVar.widthInPPM;
		GlobalVar.PPM = PPM;
		GlobalVar.UHM = screenHeight / GlobalVar.heightInUHM;
		this.scale = (int) GlobalVar.SCALE;
		System.out.println("Display aspect ratio: 12 x "+screenHeight / GlobalVar.PPM);

		this.camera = new OrthographicCamera();
		this.viewport = new StretchViewport(screenWidth,
				screenHeight + GlobalVar.safeAreaInsetBottom, camera);
		viewport.setScreenPosition(0,0);
		viewport.apply();
		batch = new SpriteBatch();

		homeScreen = new HomeScreen(this);
		setScreen(homeScreen);

		Gdx.app.log("Gdx version", com.badlogic.gdx.Version.VERSION);
	}

	private Vector2 getIOSSafeAreaInsets() {
		if (Gdx.app.getType() == Application.ApplicationType.iOS) {
			try {
				Class<?> IOSLauncher = Class.forName("com.mygdx.game.IOSLauncher");
				return (Vector2) IOSLauncher.getDeclaredMethod("getSafeAreaInsets").invoke(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Vector2();
	}

	@Override
	public void resize (int width, int height){
		camera.setToOrtho(false, width/scale, scale);
		viewport.update(width/scale, height/scale);
	}
}
