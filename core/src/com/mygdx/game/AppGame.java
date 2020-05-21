package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.HomeScreen;
import com.mygdx.game.Screens.SplashScreen;
import com.mygdx.game.Utils.GlobalVar;

import static com.mygdx.game.Utils.GlobalVar.screenHeight;
import static com.mygdx.game.Utils.GlobalVar.screenWidth;

public class AppGame extends Game {

	public OrthographicCamera camera;
	public AssetManager assetsManager = new AssetManager();                                          //per gestione caricamento

	public BitmapFont font;

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
		System.out.println("Display aspect ratio: 12 x "+screenHeight / GlobalVar.PPM+ " " +
				"\nScale width: "+GlobalVar.getScaleWidth()+ ", Scale height: "+GlobalVar.getScaleHeight());

		assetsManager = new AssetManager();                                          //per gestione caricamento
        font = new BitmapFont();
		font.getData().setScale(GlobalVar.getScaleWidth());

		this.camera = new OrthographicCamera();
		this.viewport = new StretchViewport(screenWidth,
				screenHeight + GlobalVar.safeAreaInsetBottom, camera);
		viewport.setScreenPosition(0,0);
		viewport.apply();
		batch = new SpriteBatch();

		homeScreen = new HomeScreen(this);
		setScreen(new SplashScreen(this));

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
	public void dispose() {
		batch.dispose();
		getScreen().dispose();
	}
}
