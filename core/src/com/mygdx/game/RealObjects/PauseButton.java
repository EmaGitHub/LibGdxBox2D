package com.mygdx.game.RealObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    Texture texture = new Texture(Gdx.files.internal("Images/ball.png"));


    public PauseButton() {
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
        sr.circle(coords.x+radius, coords.y+radius, radius);
        sr.rectLine(coords.x-(diameter/10)+radius, coords.y+(diameter/5)+radius,
                coords.x-(diameter/10)+radius, coords.y-(diameter/5)+radius, diameter /10);
        sr.rectLine(coords.x+(diameter/10)+radius, coords.y+(diameter/5)+radius,
                coords.x+(diameter/10)+radius, coords.y-(diameter/5)+radius, diameter/10);
        sr.end();

        batch.begin();
    }
}