package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.Global;

public class TitleScreen extends ScreenAdapter {

    private AppGame game;
    private OrthographicCamera camera;

    private float PPM;

    public TitleScreen(AppGame game){
        this.game = game;
        this.PPM = Global.PPM;
        this.camera = game.camera;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        cameraUpdate();

        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, .20f, 50, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Please touch to play.", Gdx.graphics.getWidth() * .32f, Gdx.graphics.getHeight() * .35f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    public void cameraUpdate() {

        Vector3 position = camera.position;
        position.x = Gdx.graphics.getWidth()/2;
        position.y = Gdx.graphics.getHeight()/2;
        camera.position.set(position);
        camera.update();
    }
}
