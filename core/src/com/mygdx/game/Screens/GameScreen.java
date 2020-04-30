package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.AppGame;
import com.mygdx.game.Objects.BounceBall;
import com.mygdx.game.Utils.BodyFactory;
import com.mygdx.game.Utils.Global;
import com.mygdx.game.Utils.ObjectFactory;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    private AppGame game;
    private OrthographicCamera camera;
    private boolean FIRST = true;
    private float PPM;

    private Box2DDebugRenderer b2dr;
    private boolean DEBUG;
    private World world;
    private BodyFactory bodyFactory;
    private ObjectFactory objectFactory;
    private Body platform;

    Vector3 touchPoint;
    private boolean firstTouch = true;

    ArrayList<BounceBall> balls;

    public GameScreen(AppGame game){

        this.game = game;
        this.PPM = Global.PPM;
        this.camera = game.camera;
        this.DEBUG = Global.DEBUG;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata

        world = new World(new Vector2(0, -9.8f), false);	//-9.8f
        b2dr = new Box2DDebugRenderer();

        bodyFactory = new BodyFactory(world);
        objectFactory = new ObjectFactory(world, game.stage);
        touchPoint = new Vector3();

        balls = new ArrayList<>();
        platform = bodyFactory.createStaticBody(0, -PPM/2-PPM/10, PPM*5, PPM/5);
    }

    @Override
    public void render(float delta){

        // Update Logic
        this.update(Gdx.graphics.getDeltaTime());

        // Draw Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        this.game.stage.act(delta);
        this.game.stage.draw();

        b2dr.render(world, camera.combined.scl(PPM));
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.titleScreen);
            for(BounceBall ball: balls)
                ball.dispose();
        }
    }


    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = 0; //player.getPosition().x * PPM;
        position.y = 8*PPM; //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void dispose () {
        world.dispose();
        b2dr.dispose();
    }
}
