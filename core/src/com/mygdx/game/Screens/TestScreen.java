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
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.RealObjects.PauseButton;
import com.mygdx.game.Utils.GlobalVar;

import java.util.ArrayList;

public class TestScreen extends ScreenAdapter {

    private AppGame game;
    private Stage gameStage;
    private OrthographicCamera camera;
    private boolean DEBUG;
    private boolean FIRST = true;
    private float PPM;

    private Box2DDebugRenderer b2dr;
    private World world;
    private ObjectFactory objectFactory;

    Vector3 touchPoint;
    private boolean firstTouch = true;

    ArrayList<BounceBall> balls;

    public TestScreen(AppGame game){

        this.game = game;
        this.PPM = GlobalVar.PPM;
        this.DEBUG = GlobalVar.DEBUG;
        this.camera = game.camera;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata

        world = new World(new Vector2(0, -9.8f), false);	//-9.8f
        this.gameStage = new Stage(game.viewport, game.batch);
        b2dr = new Box2DDebugRenderer();

        objectFactory = new ObjectFactory(world, gameStage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);               //evita la chiusura con bottone back
        touchPoint = new Vector3();

        balls = new ArrayList<>();
        this.gameStage.addActor(new PauseButton());
        this.objectFactory.createScreen3Boundaries();
    }

    @Override
    public void render(float delta){
        // Update Logic
        this.update(Gdx.graphics.getDeltaTime());

        // Draw Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        this.gameStage.act(delta);
        this.gameStage.draw();
        if(DEBUG) b2dr.render(world, camera.combined.scl(PPM));
    }
    public void update(float delta) {
        world.step(1/60f, 1, 1);
        inputUpdate(delta);
        cameraUpdate(delta);
    }

    public void inputUpdate(float delta) {
        if(Gdx.input.isTouched()){
            if(firstTouch) firstTouch=false;
            else {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                balls.add(this.objectFactory.createBounceBallObject(touchPoint.x, touchPoint.y, PPM * 1));
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.homeScreen);
            for(BounceBall ball: balls)
                ball.dispose();
        }
    }


    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = 0; //player.getPosition().x * PPM;
        position.y = 0 - GlobalVar.safeAreaInsetBottom; //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
