package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.TitleScreen;
import com.mygdx.game.Utils.Global;

public class AppGame extends Game {

	float screenWidth, screenHeight;

	public OrthographicCamera camera;
	private Viewport viewport;

	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create () {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		Global.PPM = screenWidth / 12;

		this.camera = new OrthographicCamera();
		this.viewport = new StretchViewport(screenWidth, screenHeight, camera);
		viewport.apply();

		batch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(new TitleScreen(this));
	}

//	@Override
//	public void resize (int width, int height){
//		camera.setToOrtho(false, width/Global.SCALE, height/Global.SCALE);
//		viewport.update(width/(int)Global.SCALE, height/(int)Global.SCALE);
//	}
}
