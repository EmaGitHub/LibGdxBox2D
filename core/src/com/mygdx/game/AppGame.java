package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.HomeScreen;
import com.mygdx.game.Utils.Global;

public class AppGame extends Game {

	float screenWidth, screenHeight;
	public OrthographicCamera camera;
	public Viewport viewport;
	public SpriteBatch batch;
	public Screen homeScreen;

	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		// Screen aspect ratio definition
		float PPM = screenWidth / Global.widthInPPM;
		Global.PPM = PPM;
		float height = screenHeight / Global.PPM;
		Global.heightInPPM = height;
		System.out.println("Display aspect ratio: 12 x "+height);

		this.camera = new OrthographicCamera();
		this.viewport = new StretchViewport(screenWidth, screenHeight, camera);
		viewport.apply();
		batch = new SpriteBatch();

		homeScreen = new HomeScreen(this);
		setScreen(homeScreen);

		Gdx.app.log("Gdx version", com.badlogic.gdx.Version.VERSION);
	}

	@Override
	public void resize (int width, int height){
		camera.setToOrtho(false, width/Global.SCALE, height/Global.SCALE);
		viewport.update(width/(int)Global.SCALE, height/(int)Global.SCALE);
	}
}
