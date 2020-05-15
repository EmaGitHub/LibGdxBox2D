package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.game.Utils.GlobalVar;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class LineContainer extends Group {

    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;

    MoveToAction move;
    SequenceAction sequence;
    RotateToAction rotate;

    private long startTime;
    private long elapsedTime;

    private ShapeRenderer shapeRenderer;
    private float lenght;

    public LineContainer(float x, float y, float lenght){
        startTime = System.currentTimeMillis();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(1, 1, 1, 1f);
        this.lenght = lenght;
        setSize(lenght, PPM/5);
        setOrigin(getX(), getHeight()/2);
        setPosition(x, y);

        this.move();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime = System.currentTimeMillis() - startTime;
        this.toFront();
        super.draw(batch, parentAlpha);

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(getX(), getY()+getHeight()/2-PPM/20,
                0, 0,
                this.lenght, PPM/10,
                1, 1, getRotation());
        shapeRenderer.end();
    }

    public void move(){

        DelayAction delay = new DelayAction(0.5f);
        RotateToAction rotateToAction = new RotateToAction();
        rotateToAction.setRotation(360);
        rotateToAction.setDuration(1f);

        this.addAction(sequence(delay, rotateToAction));
    }
}
