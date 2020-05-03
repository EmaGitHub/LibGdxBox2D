package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Utils.GlobalVar;

public class GameScreen extends AbstractScreen {

    BounceBall ball;

    public GameScreen(AppGame game){
        super(game);
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen2Boundaries();
        this.ball = this.objectFactory.createBounceBallObject(0, GlobalVar.heightInPPM*PPM/2 - 3*PPM, PPM);
    }

    @Override
    public void inputUpdate(float delta) {
        if(Gdx.input.isTouched()){
            if(firstTouch) firstTouch=false;
            else {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(game.homeScreen);
            ball.dispose();
        }
    }
}