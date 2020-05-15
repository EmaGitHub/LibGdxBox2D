package com.mygdx.game.Screens;

import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Utils.GlobalVar;

public class GameScreen extends AbstractGameScreen {

    BounceBall ball;

    public GameScreen(final AppGame game){
        super(game);
        super.freezeButtonVisible = false;
        super.moveButtonVisible = false;
    }

    @Override
    public void show(){
        super.show();
        this.objectFactory.createScreen3Boundaries();
        this.ball = this.objectFactory.createBounceBallObject(0, GlobalVar.heightInUHM*UHM/2 - 3*UHM, PPM);
    }

    @Override
    public void touched(){
        //System.out.println("Touched "+touchPoint.x+", "+touchPoint.y);
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

    @Override
    public void hide(){
        super.hide();
    }
}