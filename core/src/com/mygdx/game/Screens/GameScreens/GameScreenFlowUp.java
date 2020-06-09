package com.mygdx.game.Screens.GameScreens;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.Board;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Screens.AbstractGameScreen;
import com.mygdx.game.Utils.GlobalVar;

import GameEntities.Controllers;

public class GameScreenFlowUp extends AbstractGameScreen {

    BounceBall ball;
    Board board;

    float difference = 0;
    float bottomLimit = 0;
    float bottomLimitBase = 0;

    boolean topLimitUpdated = true;
    float topLimit = 8;
    float topLimitBase = 8;

    float ballYValue;
    float prevBallYValue;

    float topBound;



    public GameScreenFlowUp(final AppGame game){
        super(game);
        this.topBound = 8;
        controllers = new Controllers(0, 0,true, false, game);
    }

    @Override
    public void show(){
        super.show();
        this.objectFactory.createScreen3Boundaries();
        this.objectFactory.createScreen2Boundaries(0, UHM*10);
        this.ball = this.objectFactory.createBounceBallObject(0, 4*UHM, PPM);
    }

    @Override
    public void render(float delta) {
        super.clear();
        super.render(delta);

        // creazione dinamica boundaries
        if (this.ball.getPosition().y > this.topBound) {
            this.objectFactory.createScreen2Boundaries(0, this.topBound * UHM + 20 * UHM);
            this.topBound += 20;
        }
    }

    @Override
    public void touched(){
        this.board = this.objectFactory.createBoardObject(touchPointDown.x, touchPointDown.y,
                touchPointUp.x, touchPointUp.y);
    }

    @Override
    protected void touchEnd() {
//        System.out.println("Touch end TOUCH START: "+touchPointDown.x+", "+touchPointDown.y
//                +" TOUCH END: "+touchPointUp.x+", "+touchPointUp.y);

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
    protected void cameraUpdate() {
        Vector3 position = camera.position;
        position.x =  0;                                          //player.getPosition().x * PPM;

        this.ballYValue = this.ball.getPosition().y;

        if (this.ballYValue > this.prevBallYValue) {
            this.topLimitUpdated = false;
            this.difference = ballYValue - this.topLimit;
            if(difference > 0){
                this.bottomLimit =  difference * PPM + this.bottomLimitBase;
            }
        }
        else {
            if (!this.topLimitUpdated) {
                this.topLimit = topLimitBase + this.bottomLimit / PPM;
                this.bottomLimitBase = this.bottomLimit;
                this.topLimitUpdated = true;
            }
        }
        position.y = -GlobalVar.safeAreaInsetBottom + this.bottomLimit;

        this.prevBallYValue = this.ballYValue;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
        super.hide();
    }
}