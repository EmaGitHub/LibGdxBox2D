package com.mygdx.game.Screens;

import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;

import java.util.ArrayList;

public class TestScreen extends AbstractScreen {

    ArrayList<BounceBall> balls;

    public TestScreen(AppGame game){
        super(game);
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        balls = new ArrayList<>();
        this.objectFactory.createScreen3Boundaries();
    }

    @Override
    public void touched(){
        balls.add(this.objectFactory.createBounceBallObject(touchPoint.x, touchPoint.y, PPM*1));
    }

    @Override
    protected void freezeActors() {
        super.freezeActors();
        for(BounceBall ball: balls)
            ball.freezeObject();
    }
}