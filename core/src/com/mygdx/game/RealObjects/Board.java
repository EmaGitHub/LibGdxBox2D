package com.mygdx.game.RealObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Utils.GlobalVar;

public class Board extends Object{

    float PPM = GlobalVar.PPM;
    float width;
    float stateTime;

    TextureRegion currentFrame;
    TextureRegion pausedFrame;
    Animation<TextureRegion> framesAnimation;
    private boolean PAUSED;

    public Board(Body body, float width){
        super(body);
        stateTime = 0f;
//        this.diam = diam;
//        this.rad = diam/2;
//        setOrigin(rad, rad);
//        this.framesAnimation = SpritesFactory.getGlobeFrames();
//        body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);		//per muovere numero metri al secondo
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        if(!GlobalVar.DEBUG) batch.draw(currentFrame, body.getPosition().x*PPM - rad,
//                body.getPosition().y*PPM - rad,
//                diam, diam);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//
//        if (!PAUSED) this.currentFrame = framesAnimation.getKeyFrame(stateTime, true);
//        else
//            if(!this.body.getLinearVelocity().equals(new Vector2(0, 0)))
//                this.body.setLinearVelocity(new Vector2(0, 0));
    }

    public void dispose(){ currentFrame.getTexture().dispose(); }
}
