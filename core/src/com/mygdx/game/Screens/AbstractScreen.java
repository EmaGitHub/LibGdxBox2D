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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.AppGame;
import com.mygdx.game.Factories.ObjectFactory;
import com.mygdx.game.Utils.GlobalVar;

public class AbstractScreen extends ScreenAdapter {

    protected AppGame game;
    protected World world;
    protected Stage stage;
    protected OrthographicCamera camera;
    protected Box2DDebugRenderer b2dr;

    protected float PPM;
    protected float UHM;

    protected ObjectFactory objectFactory;
    protected Vector3 touchPoint;
    protected boolean firstTouch = true;

    public AbstractScreen(final AppGame game){
        this.game = game;
        this.PPM = GlobalVar.PPM;
        this.UHM = GlobalVar.UHM;
        this.camera = game.camera;

        world = new World(new Vector2(0, -9.8f), false);    	//-9.8f
        stage = new Stage(game.viewport, game.batch);
        touchPoint = new Vector3();
        b2dr = new Box2DDebugRenderer();
        objectFactory = new ObjectFactory(this.world, this.stage);
    }

    @Override
    public void show(){
        Gdx.input.setCatchKey(Input.Keys.BACK, true);                   //evita la chiusura con bottone back
        Gdx.input.setInputProcessor(stage);
    }

    protected void clear(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta){
        // Update Logic
        this.update();

        // Draw Render
        game.batch.setProjectionMatrix(camera.combined);
        this.stage.act(delta);
        this.stage.draw();

        stage.setDebugAll(GlobalVar.DEBUG);                             //Debug All
        //if(GlobalVar.DEBUG)
            b2dr.render(this.world, this.camera.combined.scl(PPM));
    }

    public void update() {
        world.step(1/60f, 1, 1);
        inputUpdate();
        cameraUpdate();
    }

    protected void inputUpdate() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.homeScreen);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            GlobalVar.DEBUG = !GlobalVar.DEBUG;
        }
        listenTouchInput();
    }

    @Override
    public void resize (int width, int height){
        game.viewport.update((int)(width), (int)(height));
        camera.setToOrtho(false, width, height);
    }

    protected void listenTouchInput(){
        if(Gdx.input.isTouched()){
            if(firstTouch) firstTouch=false;
            else {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                touched();
            }
        }
    }

    protected void touched(){ }

    protected void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = 0;                                          //player.getPosition().x * PPM;
        position.y = 0 - GlobalVar.safeAreaInsetBottom;         //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
        this.stage.dispose();
        Gdx.input.setInputProcessor(null);
    }
}