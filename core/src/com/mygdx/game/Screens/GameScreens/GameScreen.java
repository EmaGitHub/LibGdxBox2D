package com.mygdx.game.Screens.GameScreens;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.Board;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Screens.AbstractGameScreen;

import GameEntities.Controllers;

public class GameScreen extends AbstractGameScreen {

    BounceBall ball;
    Board board;
    Board testBoard;

    public GameScreen(final AppGame game){
        super(game);
        controllers = new Controllers(0, 0,true, false, game);
    }

    @Override
    public void show(){
        super.show();
        this.objectFactory.createScreen3Boundaries();
        this.ball = this.objectFactory.createBounceBallObject(PPM, 5*UHM, PPM);

    }

    @Override
    public void render(float delta) {
        super.clear();
        super.render(delta);
    }

    @Override
    public void touched(){
        this.testBoard = this.objectFactory.createBoardObject(touchPointDown.x, touchPointDown.y,
                touchPointUp.x, touchPointUp.y, 1.0f);
        this.board = board;
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
        position.y =  this.ball.getY(); // - GlobalVar.safeAreaInsetBottom + this.ball.checkCameraBounds();
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
        super.hide();
    }
}