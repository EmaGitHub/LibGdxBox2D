package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.mygdx.game.Utils.GlobalVar;

public class EActor extends Actor {

    Texture img;
    Sprite sprite;
    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;

    private long startTime;
    private long elapsedTime;

    MoveToAction move;
    SequenceAction sequence;
    RotateToAction rotate;

    Container wrapper;

    public EActor(){

        img = new Texture("Images/e.png");
        sprite = new Sprite(img);
        startTime = System.currentTimeMillis();

        setOrigin(getWidth()/2, getHeight()/2);

        move = new MoveToAction();
        move.setStartPosition(getX(), getY());
        move.setX(getX());
        move.setY(GlobalVar.heightInUHM*UHM/2 - 3*PPM);
        move.setDuration(3);

        rotate = new RotateToAction();
        rotate.setRotation(150);
        rotate.setDuration(1f);

        sequence = new SequenceAction(move, rotate);
        this.addAction(Actions.moveTo(getX(), (GlobalVar.heightInUHM*UHM-GlobalVar.safeAreaInsetTop)/2 - 3*UHM, 1));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime = System.currentTimeMillis() - startTime;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sprite.setRotation(getRotation());
        batch.draw(sprite, this.getX()-sprite.getWidth()/2, this.getY()-sprite.getHeight()/2);

        //if(elapsedTime >= 2000 ) this.addAction(rotate);
    }
}
