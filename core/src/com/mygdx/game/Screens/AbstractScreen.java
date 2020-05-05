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
import com.mygdx.game.Utils.GlobalVar;

import GameEntities.PauseButton;
import GameEntities.ScoreBoard;

public class AbstractScreen extends ScreenAdapter {

    protected AppGame game;
    protected World world;
    protected Stage stage;
    protected OrthographicCamera camera;
    protected Box2DDebugRenderer b2dr;

    protected boolean DEBUG;
    protected boolean PAUSE = false;

    protected float PPM;

    protected ObjectFactory objectFactory;

    protected Vector3 touchPoint;
    protected boolean firstTouch = true;

    PauseButton pauseButton;
    protected boolean pauseButtonVisible = true;

    ScoreBoard scoreBoard;
    protected boolean scoreBoardVisible = true;

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
        if(scoreBoardVisible) {
            scoreBoard = new ScoreBoard();
            scoreBoard.setX(-PPM/2);
            scoreBoard.setY(GlobalVar.heightInPPM*PPM/2 - PPM - PPM/2 - GlobalVar.safeAreaInsetTop);
            this.stage.addActor(scoreBoard);
        }
        if(pauseButtonVisible) {
            pauseButton = new PauseButton();
            pauseButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!PAUSE) {
                        pauseGame();
                        return false;
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(PAUSE) resumeGame();
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
        this.update();
        // Draw Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        this.stage.act(delta);
        this.stage.draw();
        b2dr.render(this.world, this.camera.combined.scl(PPM));
    }

    public void update() {
        world.step(1/60f, 1, 1);
        inputUpdate();
        cameraUpdate();
    }

    protected void inputUpdate() {
        if(Gdx.input.isTouched() && !PAUSE){
            if(firstTouch) firstTouch=false;
            else {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                touched();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.homeScreen);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            GlobalVar.DEBUG = !GlobalVar.DEBUG;
        }
    }

    protected void pauseGame() {
        this.PAUSE = true;
        Gdx.app.log("Info", "Game in Pause");
        this.pauseButton.switchState();
        this.freezeScene();
    }

    protected void resumeGame(){
        Gdx.app.log("Info", "Resume to Game");
        this.pauseButton.switchState();
        this.resumeScene();
        this.PAUSE = false;
    }

    protected void touched(){ }

    protected void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = 0;                                          //player.getPosition().x * PPM;
        position.y = 0 - GlobalVar.safeAreaInsetBottom;         //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    protected void freezeScene(){
        this.world.setGravity(new Vector2(0, 0));
    }

    protected void resumeScene(){
        this.world.setGravity(new Vector2(0, -9.8f));
    }

    @Override
    public void hide(){
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
}