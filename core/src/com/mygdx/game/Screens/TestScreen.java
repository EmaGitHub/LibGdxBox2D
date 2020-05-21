package com.mygdx.game.Screens;

import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;

import java.util.ArrayList;

import GameEntities.Controllers;

public class TestScreen extends AbstractGameScreen {

    ArrayList<BounceBall> balls = new ArrayList<>();;

    public TestScreen(final AppGame game){
        super(game);
        controllers = new Controllers(0, 0, game);
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen3Boundaries();
    }

    @Override
    public void render(float delta) {
        super.clear();
        super.render(delta);
    }

    @Override
    public void touched(){
        balls.add(this.objectFactory.createBounceBallObject(touchPoint.x, touchPoint.y, PPM));
        super.controllers.updateValue(this.balls.size());
    }

    @Override
    protected void freezeScene() {
        super.freezeScene();            //gravity
        for(BounceBall ball: balls)
            ball.freezeObject();
    }

    @Override
    protected void resumeScene() {
        super.resumeScene();            //gravity
        for(BounceBall ball: balls)
            ball.resumeObject();
    }

    @Override
    public void hide() {
        super.hide();
    }
}