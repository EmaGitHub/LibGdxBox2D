package com.mygdx.game.Screens;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Utils.GlobalVar;

import GameEntities.Controllers;

public class GameScreen extends AbstractGameScreen {

    BounceBall ball;

    public GameScreen(final AppGame game){
        super(game);
        controllers = new Controllers(0, 0,false, false, game);
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
    public void update() {
        super.update();
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
    protected void cameraUpdate() {
        Vector3 position = camera.position;
        position.x =  0;                                          //player.getPosition().x * PPM;
        position.y = -GlobalVar.safeAreaInsetBottom;         //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
        super.hide();
    }
}