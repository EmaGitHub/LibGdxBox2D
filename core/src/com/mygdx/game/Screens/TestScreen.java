package com.mygdx.game.Screens;

import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;

import java.util.ArrayList;

public class TestScreen extends AbstractGameScreen {

    ArrayList<BounceBall> balls = new ArrayList<>();;

    public TestScreen(final AppGame game){
        super(game);
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen3Boundaries();
    }

    @Override
    public void touched(){
        balls.add(this.objectFactory.createBounceBallObject(touchPoint.x, touchPoint.y, PPM));
        super.scoreBoard.updateValue(this.balls.size());
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