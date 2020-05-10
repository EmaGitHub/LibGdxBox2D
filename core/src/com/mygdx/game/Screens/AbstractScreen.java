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

import GameEntities.FreezeButton;
import GameEntities.MenuButton;
import GameEntities.MenuPanel;
import GameEntities.MoveButton;
import GameEntities.ScoreBoard;

public class AbstractScreen extends ScreenAdapter {

    protected AppGame game;
    protected World world;
    protected Stage stage;
    protected OrthographicCamera camera;
    protected Box2DDebugRenderer b2dr;

    protected boolean PAUSED = false;
    protected boolean FREEZED = false;

    protected float PPM;
    protected float UHM;

    protected ObjectFactory objectFactory;

    protected Vector3 touchPoint;
    protected boolean firstTouch = true;

    MenuButton menuButton;
    protected boolean menuButtonVisible = true;
    private MenuPanel menu;
    ScoreBoard scoreBoard;
    protected boolean scoreBoardVisible = true;
    FreezeButton freezeButton;
    protected boolean freezeButtonVisible = true;
    MoveButton moveButton;
    protected boolean moveButtonVisible = true;

    public AbstractScreen(AppGame game){
        this.game = game;
        this.PPM = GlobalVar.PPM;
        this.UHM = GlobalVar.UHM;
        this.camera = game.camera;
    }

    @Override
    public void show(){                                                             // Prima funzione chiamata
        world = new World(new Vector2(0, -9.8f), false);    	//-9.8f
        stage = new Stage(game.viewport, game.batch);

        touchPoint = new Vector3();
        b2dr = new Box2DDebugRenderer();
        objectFactory = new ObjectFactory(this.world, this.stage);

        menu = new MenuPanel(game);


        if(scoreBoardVisible) {
            scoreBoard = new ScoreBoard();
            scoreBoard.setX(-PPM/2);
            scoreBoard.setY(GlobalVar.heightInUHM*UHM/2 - PPM - PPM/2 - GlobalVar.safeAreaInsetTop);
            this.stage.addActor(scoreBoard);
        }
        if(menuButtonVisible) {
            menuButton = new MenuButton();
            menuButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!PAUSED) {
                        pauseGame();
                        stage.addActor(menu);
                        menu.openMenu(stage);
                        return false;
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(PAUSED) {
                        menu.closeMenu();
                    }
                }
            });
            menuButton.setX(-PPM);
            menuButton.setY(GlobalVar.heightInUHM*UHM/2 - PPM*2 - GlobalVar.safeAreaInsetTop);
            this.stage.addActor(menuButton);
        }
        if(freezeButtonVisible) {
            freezeButton = new FreezeButton();
            freezeButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!FREEZED && !PAUSED) {
                        freeze();
                        return false;
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(FREEZED && !PAUSED) unfreeze();
                }
            });
            freezeButton.setX(4*PPM);
            freezeButton.setY(-GlobalVar.heightInUHM*UHM/2);
            this.stage.addActor(freezeButton);
        }
        if(moveButtonVisible) {
            moveButton = new MoveButton();
            moveButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!FREEZED && !PAUSED) {
                        freeze();
                        return false;
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(FREEZED && !PAUSED) unfreeze();
                }
            });
            moveButton.setX(-6*PPM);
            moveButton.setY(-GlobalVar.heightInUHM*UHM/2);
            this.stage.addActor(moveButton);
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
        stage.setDebugAll(GlobalVar.DEBUG);
        this.stage.act(delta);
        this.stage.draw();
        if(GlobalVar.DEBUG)
            b2dr.render(this.world, this.camera.combined.scl(PPM));
    }

    public void update() {
        world.step(1/60f, 1, 1);
        inputUpdate();
        cameraUpdate();
    }

    protected void inputUpdate() {
        if(Gdx.input.isTouched() && !PAUSED && !FREEZED){
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
        this.PAUSED = true;
        Gdx.app.log("Info", "Game in Pause");
        this.menuButton.switchState();
        if(!FREEZED)this.freezeScene();
    }

    protected void resumeGame(){
        Gdx.app.log("Info", "Resume to Game");
        this.menuButton.switchState();
        this.resumeScene();
        this.PAUSED = false;
    }

    protected void freeze() {
        this.FREEZED = true;
        Gdx.app.log("Info", "Stage freezed");
        this.freezeButton.switchState();
        this.freezeScene();
    }

    protected void unfreeze() {
        Gdx.app.log("Info", "Stage unfreezed");
        this.freezeButton.switchState();
        this.resumeScene();
        this.FREEZED = false;
    }

    protected void freezeScene(){
        Gdx.app.log("Info", "Game freezed");
        this.world.setGravity(new Vector2(0, 0));
    }

    protected void resumeScene(){
        this.world.setGravity(new Vector2(0, -9.8f));
    }

    public void closeMenuCallback(){
        menu.remove();
        System.out.println("Callback");
        PAUSED = false;
        if(!FREEZED) resumeGame();
        else menuButton.switchState();
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
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
}