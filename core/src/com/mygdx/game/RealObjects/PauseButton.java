package com.mygdx.game.RealObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.Utils.GlobalVar;

public class PauseButton extends Actor {

    private ShapeRenderer sr;
    private float PPM = GlobalVar.PPM;
    private float dimension = PPM;

    public PauseButton() {
        setPosition(0, GlobalVar.heightInPPM * PPM / 2 - PPM - GlobalVar.safeAreaInsetTop);
        setSize(dimension, dimension);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        this.setTouchable(Touchable.enabled);
        this.setBounds(getX(), getY(),
                this.getWidth(), this.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        sr.setProjectionMatrix(batch.getProjectionMatrix());
        Vector2 coords = new Vector2(getX(),getY());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.circle(coords.x, coords.y, dimension/2);
        sr.rectLine(coords.x - dimension/10, coords.y+dimension/5,coords.x-dimension/10,
                coords.y-dimension/5,dimension/10);
        sr.rectLine(coords.x + dimension/10, coords.y+dimension/5,coords.x+dimension/10,
                coords.y-dimension/5,dimension/10);
        sr.end();
        this.toFront();

        batch.begin();
    }
}