package com.mygdx.game.RealObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Factories.SpritesFactory;
import com.mygdx.game.Utils.Global;

public class BounceBall extends Object{

    float PPM = Global.PPM;
    float diam;
    float rad;
    float stateTime;
    TextureRegion currentFrame;
    Animation<TextureRegion> framesAnimation;
    boolean DEBUG;

    public BounceBall(Body body, float diam){
        super(body);
        stateTime = 0f;
        this.diam = diam;
        this.rad = diam/2;
        setOrigin(rad, rad);
        this.DEBUG = Global.DEBUG;
        this.framesAnimation = SpritesFactory.getBallFrames();
        this.body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);		//per muovere numero metri al secondo
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!DEBUG) batch.draw(currentFrame, super.body.getPosition().x*PPM - rad,
                super.body.getPosition().y*PPM - rad,
                diam, diam);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        this.currentFrame = this.framesAnimation.getKeyFrame(stateTime, true);
    }

    public void dispose(){
        currentFrame.getTexture().dispose();
    }
}
