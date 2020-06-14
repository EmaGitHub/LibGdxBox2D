package com.mygdx.game.Screens;

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
        super.show();
        super.inputMultiplexer.addProcessor(controllers.getStage());
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
        });         //gestione controlli onScreen
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
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        controllers.draw();
    }

    @Override
    protected void listenTouchInput() {
        if(!PAUSED){
            super.listenTouchInput();
        }
    }

    protected void pauseGame() {
        this.PAUSED = true;
        controllers.getMenuButton().switchState();
        if(!FREEZED)this.freezeScene();
    }

    protected void resumeGame(){
        controllers.getMenuButton().switchState();
        this.resumeScene();
        this.PAUSED = false;
    }

    protected void freeze() {
        this.FREEZED = true;
        controllers.getFreezeButton().switchState();
        this.freezeScene();
    }

    protected void unfreeze() {
        controllers.getFreezeButton().switchState();
        this.resumeScene();
        this.FREEZED = false;
    }

    protected void freezeScene(){
        this.world.setGravity(new Vector2(0, 0));
    }

    protected void resumeScene(){
        this.world.setGravity(new Vector2(0, -9.8f));
    }

    public void closeMenuCallback(){
        controllers.getMenu().remove();
        PAUSED = false;
        if(!FREEZED) resumeGame();
        else controllers.getMenuButton().switchState();
    }
}