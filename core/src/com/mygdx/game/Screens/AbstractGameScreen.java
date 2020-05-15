package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.GlobalVar;

import GameEntities.FreezeButton;
import GameEntities.MenuButton;
import GameEntities.MenuPanel;
import GameEntities.MoveButton;
import GameEntities.ScoreBoard;

public class AbstractGameScreen extends AbstractScreen {

    protected boolean PAUSED = false;
    protected boolean FREEZED = false;

    MenuButton menuButton;
    protected boolean menuButtonVisible = true;
    private MenuPanel menu;
    ScoreBoard scoreBoard;
    protected boolean scoreBoardVisible = true;
    FreezeButton freezeButton;
    protected boolean freezeButtonVisible = true;
    MoveButton moveButton;
    protected boolean moveButtonVisible = true;

    public AbstractGameScreen(final AppGame game){
        super(game);
    }

    @Override
    public void show(){                                                                     // Prima funzione chiamata
        if(scoreBoardVisible) {
            scoreBoard = new ScoreBoard();
            scoreBoard.setX(-PPM/2);
            scoreBoard.setY(GlobalVar.heightInUHM*UHM/2 - PPM - PPM/2 - GlobalVar.safeAreaInsetTop);
            this.stage.addActor(scoreBoard);
        }
        if(menuButtonVisible) {
            menu = new MenuPanel(game);
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
    protected void listenTouchInput() {
        if(Gdx.input.isTouched() && !FREEZED && !PAUSED){
            if(firstTouch) firstTouch=false;
            else {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                touched();
            }
        }
    }

    @Override
    public void render(float delta){
        super.render(delta);
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
}