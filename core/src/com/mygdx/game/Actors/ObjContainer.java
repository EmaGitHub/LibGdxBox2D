package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.game.Utils.GlobalVar;

public class ObjContainer extends Group {

    private Camera camera;

    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;

    MoveToAction move;
    SequenceAction sequence;
    RotateToAction rotate;

    private long startTime;
    private long elapsedTime;

    LineActor line;

    public ObjContainer(Camera camera){
        camera = camera;
        startTime = System.currentTimeMillis();

        setSize(3*PPM, 2*PPM);
        setOrigin(-getWidth()/2, -getHeight()/2);
        setPosition(-getWidth()/2, -5*UHM);

        line = new LineActor(2*PPM, -5*UHM, 3*PPM);
        //this.addActor(line);

        move = new MoveToAction();
        move.setStartPosition(getX(), getY());
        move.setX(getX());
        move.setY(3);
        move.setDuration(2f);

        rotate = new RotateToAction();
        rotate.setRotation(150);
        rotate.setDuration(2f);

        sequence = new SequenceAction(move, rotate);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime = System.currentTimeMillis() - startTime;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //line.draw(batch, parentAlpha);
        //if(elapsedTime >= 1000 ) line.addAction(move);
    }
}
