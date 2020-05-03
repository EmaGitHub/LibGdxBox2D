package com.mygdx.game.RealObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Factories.SpritesFactory;
import com.mygdx.game.Utils.GlobalVar;

public class BounceBall extends Object{

    float PPM = GlobalVar.PPM;
    float diam;
    float rad;
    float stateTime;

    TextureRegion currentFrame;
    TextureRegion pausedFrame;
    Animation<TextureRegion> framesAnimation;
    private boolean DEBUG;
    private boolean PAUSED;

    public BounceBall(Body body, float diam){
        super(body);
        stateTime = 0f;
        this.diam = diam;
        this.rad = diam/2;
        setOrigin(rad, rad);
        this.DEBUG = GlobalVar.DEBUG;
        this.framesAnimation = SpritesFactory.getBallFrames();
        body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);		//per muovere numero metri al secondo
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!DEBUG) batch.draw(currentFrame, body.getPosition().x*PPM - rad,
                body.getPosition().y*PPM - rad,
                diam, diam);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        if (!PAUSED) this.currentFrame = framesAnimation.getKeyFrame(stateTime, true);
        else
            if(!this.body.getLinearVelocity().equals(new Vector2(0, 0)))
                this.body.setLinearVelocity(new Vector2(0, 0));
    }

    @Override
    public void freezeObject() {
        super.freezeObject();
        this.PAUSED = true;
        this.pausedFrame = this.currentFrame;
    }

    @Override
    public void resumeObject() {
        super.resumeObject();
        this.PAUSED = false;
    }

    public void dispose(){ currentFrame.getTexture().dispose(); }
}
