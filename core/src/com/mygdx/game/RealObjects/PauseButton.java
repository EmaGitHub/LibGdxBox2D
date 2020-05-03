package com.mygdx.game.RealObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Utils.GlobalVar;

public class PauseButton extends Actor {

    private ShapeRenderer sr;
    private float PPM = GlobalVar.PPM;
    private float dimension = PPM;

    public PauseButton(){
        setPosition(0, GlobalVar.heightInPPM*PPM/2 - PPM - GlobalVar.safeAreaInsetTop);
        setSize(dimension, dimension);
        setColor(Color.WHITE);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Vector2 coords = new Vector2(getX(),getY());
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(coords.x, coords.y, dimension/2);
        sr.setColor(Color.BLACK);
        sr.circle(coords.x, coords.y, dimension*10/22);
        sr.setColor(Color.WHITE);
        sr.rectLine(coords.x - dimension/10, coords.y+dimension/5,coords.x-dimension/10,
                coords.y-dimension/5,dimension/10);
        sr.rectLine(coords.x + dimension/10, coords.y+dimension/5,coords.x+dimension/10,
                coords.y-dimension/5,dimension/10);
        sr.end();
        this.toFront();

        batch.begin();
    }
}