package com.mygdx.game.RealObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Utils.GlobalVar;

public class MyActor extends Actor {

    TextureRegion region;
    Pixmap pixmapBase, pixmap100;
    int PPM = (int) GlobalVar.PPM;

    public MyActor () {

        pixmapBase = new Pixmap(Gdx.files.internal("Images/ball.png"));
        pixmap100 = new Pixmap(PPM, PPM, pixmapBase.getFormat());
        pixmap100.drawPixmap(pixmapBase,
                0, 0, pixmapBase.getWidth(), pixmapBase.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture texture = new Texture(pixmap100);
        region = new TextureRegion(texture);

        setBounds(region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight());
        this.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touched");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}