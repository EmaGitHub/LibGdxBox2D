package com.mygdx.game.Screens;

import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Utils.GlobalVar;

public class GameScreen extends AbstractScreen {

    BounceBall ball;

    public GameScreen(AppGame game){
        super(game);
        super.freezeButtonVisible = false;
        super.moveButtonVisible = false;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen2Boundaries();
        this.ball = this.objectFactory.createBounceBallObject(0, GlobalVar.heightInUHM*UHM/2 - 3*UHM, PPM);
    }

    @Override
    public void touched(){
        //System.out.println("Touched "+touchPoint.x+", "+touchPoint.y);
    }

    @Override
    public void hide(){
        super.hide();
        this.ball.dispose();
    }

    @Override
    protected void freezeScene() {
        super.freezeScene();                    //gravity
        this.ball.freezeObject();
    }

    @Override
    protected void resumeScene() {
        super.resumeScene();                    //gravity
        this.ball.resumeObject();
    }
}