package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

        setSize(GlobalVar.widthInPPM*PPM, GlobalVar.heightInUHM*UHM);
        setOrigin(-getWidth()/2, -getHeight()/2);
        setPosition(-getWidth()/2, getHeight()/2);

        line = new LineActor(0*PPM, -5*UHM, 3*PPM);
        this.addActor(line);

        move = new MoveToAction();
        move.setStartPosition(line.getX(), line.getY());
        move.setX(line.getX());
        move.setY(3);
        move.setDuration(5f);

        rotate = new RotateToAction();
        rotate.setRotation(150);
        rotate.setDuration(2f);

        sequence = new SequenceAction(move, rotate);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime = System.currentTimeMillis() - startTime;
        this.toFront();
        super.draw(batch, parentAlpha);

        //line.draw(batch, parentAlpha);
        if(elapsedTime >= 1000 ) line.addAction(Actions.moveTo(0, 3, 2f));
    }
}
