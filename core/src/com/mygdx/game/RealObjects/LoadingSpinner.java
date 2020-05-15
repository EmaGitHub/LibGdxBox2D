package com.mygdx.game.RealObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Factories.SpritesFactory;

public class LoadingSpinner extends Actor {

    float diam;
    float rad;
    float stateTime;

    TextureRegion currentFrame;
    Animation<TextureRegion> framesAnimation;

    public LoadingSpinner(float diam){
        stateTime = 0f;
        this.diam = diam;
        this.rad = diam/2;
        setOrigin(rad, rad);
        setX(0);
        setY(0);
        this.framesAnimation = SpritesFactory.getSpinnerFrames();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, getX() - rad,
                getY() - rad,
                diam, diam);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        this.currentFrame = framesAnimation.getKeyFrame(stateTime, true);
    }

    public void dispose(){ currentFrame.getTexture().dispose(); }
}
