package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Utils.GlobalVar;

public class EActor extends Actor {

    Texture img;
    Sprite sprite;
    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;

    private long startTime;
    private long elapsedTime;

    public EActor(){

        setSize(3*PPM, 2*PPM);
        setOrigin(-getWidth()/2, -getHeight()/2);
        setPosition(-getWidth()/2, -5*UHM);

        img = new Texture("Images/e.png");
        sprite = new Sprite(img);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime = System.currentTimeMillis() - startTime;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.draw(sprite, getX(), getY(), 3*PPM, 2*PPM);

        if(elapsedTime >= 0) this.addAction(Actions.moveTo(getX(), (GlobalVar.heightInUHM*UHM-GlobalVar.safeAreaInsetTop)/2 - 4*UHM, 1f));
    }
}
