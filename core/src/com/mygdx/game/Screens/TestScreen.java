package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
}