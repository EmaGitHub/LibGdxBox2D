package com.mygdx.game.RealObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Utils.GlobalVar;

public class Rocks extends Object{

    float PPM = GlobalVar.PPM;
    Texture frame;

    public Rocks(Body body, float width, float height, int type){
        super();
        super.setBody(body);
        setOrigin(width/2, height/2);
        this.setSize(width, height);
        this.frame = new Texture(Gdx.files.internal("Images/rock"+type+".png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(frame, body.getPosition().x*PPM - getWidth()/2,
                body.getPosition().y*PPM - getHeight()/2,
                getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose(){ frame.dispose(); }
}
