package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.AppGame;

import GameEntities.Controllers;

public class AbstractGameScreen extends AbstractScreen {

    protected boolean PAUSED = false;
    protected boolean FREEZED = false;

    protected Controllers controllers;

    public AbstractGameScreen(final AppGame game){
        super(game);
    }

    @Override
    public void show(){
        controllers.getMenuButton().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!PAUSED) {
                    pauseGame();
                    controllers.getStage().addActor(controllers.getMenu());
                    controllers.getMenu().openMenu(controllers.getStage());
                    return false;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(PAUSED) {
                    controllers.getMenu().closeMenu();
                }
            }
        });
        controllers.getFreezeButton().addListener(new InputListener(){
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
        controllers.getMoveButton().addListener(new InputListener(){
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
        InputMultiplexer multiplexer = new InputMultiplexer();              // Input check
        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.addProcessor(controllers.getStage());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        controllers.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        controllers.resize(width, height);
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

    protected void pauseGame() {
        this.PAUSED = true;
        Gdx.app.log("Info", "Game in Pause");
        controllers.getMenuButton().switchState();
        if(!FREEZED)this.freezeScene();
    }

    protected void resumeGame(){
        Gdx.app.log("Info", "Resume to Game");
        controllers.getMenuButton().switchState();
        this.resumeScene();
        this.PAUSED = false;
    }

    protected void freeze() {
        this.FREEZED = true;
        Gdx.app.log("Info", "Stage freezed");
        controllers.getFreezeButton().switchState();
        this.freezeScene();
    }

    protected void unfreeze() {
        Gdx.app.log("Info", "Stage unfreezed");
        controllers.getFreezeButton().switchState();
        this.resumeScene();
        this.FREEZED = false;
    }

    protected void freezeScene(){
        Gdx.app.log("Info", "Scene freezed");
        this.world.setGravity(new Vector2(0, 0));
    }

    protected void resumeScene(){
        Gdx.app.log("Info", "Scene resumed");
        this.world.setGravity(new Vector2(0, -9.8f));
    }

    public void closeMenuCallback(){
        controllers.getMenu().remove();
        System.out.println("Callback");
        PAUSED = false;
        if(!FREEZED) resumeGame();
        else controllers.getMenuButton().switchState();
    }
}