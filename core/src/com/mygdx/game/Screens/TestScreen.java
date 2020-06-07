package com.mygdx.game.Screens;

import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.BounceBall;

import java.util.ArrayList;

import GameEntities.Controllers;

public class TestScreen extends AbstractGameScreen {

    ArrayList<BounceBall> balls = new ArrayList<>();;

    public TestScreen(final AppGame game){
        super(game);
        controllers = new Controllers(0, 0, game);
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        this.objectFactory.createScreen3Boundaries();

        //this.objectFactory.createTestObject(0*PPM,0-UHM* GlobalVar.heightInUHM/2, 2*UHM);
    }

    @Override
    public void render(float delta) {
        super.clear();
        super.render(delta);
    }

    @Override
    public void touched(){
        balls.add(this.objectFactory.createBounceBallObject(touchPointUp.x, touchPointUp.y, PPM));
        super.controllers.updateValue(this.balls.size());
    }

    @Override
    protected void freezeScene() {
        super.freezeScene();            //gravity
        for(BounceBall ball: balls)
            ball.freezeObject();
    }

//    @Override
//    protected void listenTouchInput(){
//        if(Gdx.input.isTouched()){
//            camera.unproject(touchPointDown.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//            touched();
//        }
//    }

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