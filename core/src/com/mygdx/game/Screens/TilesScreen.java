package com.mygdx.game.Screens;

import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Utils.GlobalVar;

public class TilesScreen extends AbstractGameScreenTiled {

    BounceBall ball;

    public TilesScreen(final AppGame game){
        super(game);
    }

    @Override
    public void show(){
        super.show();
        this.objectFactory.createScreen1BoundaryTiledWorld();
        this.ball = this.objectFactory.createBounceBallObject(GlobalVar.widthInPPM*PPM/2 + 3*PPM, GlobalVar.heightInUHM*UHM/2 + 4*UHM, PPM);
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
}