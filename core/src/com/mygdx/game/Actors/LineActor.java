package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Utils.GlobalVar;

public class LineActor extends Actor {

    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;

    ShapeRenderer shapeRenderer;

    private float lenght;

    public LineActor(float x, float y, float lenght){

        shapeRenderer = new ShapeRenderer();

        this.lenght = lenght;
        setOrigin(getWidth()/2, getHeight()/2);
        this.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.toFront();
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1f);
        shapeRenderer.rectLine(getX(), getY(),
                getX()+this.lenght, getY(), PPM/10);
        shapeRenderer.end();

        batch.begin();

    }
}
