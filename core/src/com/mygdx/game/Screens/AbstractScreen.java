package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.AppGame;
import com.mygdx.game.Factories.ObjectFactory;
import com.mygdx.game.RealObjects.PauseButton;
import com.mygdx.game.Utils.GlobalVar;

public class AbstractScreen extends ScreenAdapter {

    protected AppGame game;
    protected boolean gameInPause = false;
    protected Stage stage;
    protected OrthographicCamera camera;
    protected Box2DDebugRenderer b2dr;

    protected boolean DEBUG;
    protected World world;
    protected float PPM;

    protected ObjectFactory objectFactory;

    protected Vector3 touchPoint;
    protected boolean firstTouch = true;
    PauseButton pauseButton;
    protected boolean pauseButtonVisible = true;

    public AbstractScreen(AppGame game){

        this.game = game;
        this.PPM = GlobalVar.PPM;
        this.DEBUG = GlobalVar.DEBUG;
        this.camera = game.camera;
    }

    @Override
    public void show(){                                                             // Prima funzione chiamata

        world = new World(new Vector2(0, -9.8f), false);    	//-9.8f
        stage = new Stage(game.viewport, game.batch);
        b2dr = new Box2DDebugRenderer();
        objectFactory = new ObjectFactory(this.world, this.stage);
        touchPoint = new Vector3();
        if(pauseButtonVisible) {
            pauseButton = new PauseButton();
            pauseButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    pauseScreen();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            pauseButton.setX(-PPM/2);
            pauseButton.setY(GlobalVar.heightInPPM*PPM/2 - PPM - PPM/2 - GlobalVar.safeAreaInsetTop);
            this.stage.addActor(pauseButton);
        }
        Gdx.input.setCatchKey(Input.Keys.BACK, true);               //evita la chiusura con bottone back
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        // Update Logic
        this.update(delta);
        // Draw Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        this.stage.act(delta);
        this.stage.draw();
        if(DEBUG) b2dr.render(this.world, this.camera.combined.scl(PPM));
    }
    public void update(float delta) {
        world.step(1/60f, 1, 1);
        inputUpdate(delta);
        cameraUpdate(delta);
    }

    protected void inputUpdate(float delta) {
        if(Gdx.input.isTouched() && !gameInPause){
            if(firstTouch) firstTouch=false;
            else {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                touched();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.homeScreen);
        }
    }

    protected void pauseScreen(){
        if(!gameInPause) {
            Gdx.app.log("Info", "Game in Pause");
            this.gameInPause = true;
            this.pauseButton.switchState();
            this.freezeActors();
        }
    }

    protected void touched(){ }

    protected void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = 0; //player.getPosition().x * PPM;
        position.y = 0 - GlobalVar.safeAreaInsetBottom; //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    protected void freezeActors(){
        this.world.setGravity(new Vector2(0, 0));
    }

    @Override
    public void hide(){
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
}