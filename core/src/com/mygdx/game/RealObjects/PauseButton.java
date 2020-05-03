package com.mygdx.game.RealObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.Utils.GlobalVar;

public class PauseButton extends Button {

    private ShapeRenderer sr;
    private float PPM = GlobalVar.PPM;
    private float diameter = PPM;
    private float radius = diameter/2;

    public PauseButton() {
        setPosition(0, GlobalVar.heightInPPM * PPM / 2 - PPM - GlobalVar.safeAreaInsetTop);
        setSize(diameter, diameter);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        this.setTouchable(Touchable.enabled);
        this.setBounds(getX()-radius, getY()-radius,
                diameter, diameter);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        this.toFront();

        sr.setProjectionMatrix(batch.getProjectionMatrix());
        Vector2 coords = new Vector2(getX(),getY());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.circle(coords.x, coords.y, radius);
        sr.rectLine(coords.x - diameter /10, coords.y+ diameter /5,coords.x- diameter /10,
                coords.y- diameter /5, diameter /10);
        sr.rectLine(coords.x + diameter /10, coords.y+ diameter /5,coords.x+ diameter /10,
                coords.y- diameter /5, diameter /10);
        sr.end();

        batch.begin();
    }
}