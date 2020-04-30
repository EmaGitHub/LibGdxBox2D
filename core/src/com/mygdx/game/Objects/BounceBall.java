package com.mygdx.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Utils.FramesFactory;
import com.mygdx.game.Utils.Global;

public class BounceBall extends Object{

    float PPM = Global.PPM;
    float diam = PPM*1;
    float rad = diam/2;
    // A variable for tracking elapsed time for the animation
    float stateTime;
    TextureRegion currentFrame;
    Animation<TextureRegion> worldAnimation;

    private Sprite bodySprite;

    public BounceBall(Body body){
        super(body);
        stateTime = 0f;
        this.worldAnimation = FramesFactory.createBallFrames();

        setOrigin(rad, rad);
        this.body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);		//per muovere numero metri al secondo
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(currentFrame, super.body.getPosition().x*PPM - rad,
                super.body.getPosition().y*PPM - rad,
                diam , diam);
    }

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        this.currentFrame = this.worldAnimation.getKeyFrame(stateTime, true);
    }

    public void jump(){

        body.applyForceToCenter(0, 300, false);
        //if(Gdx.input.isKeyPressed(Input.Keys.LEFT))player.applyForceToCenter(-6, 0, false);
    }

    public void dispose(){
        currentFrame.getTexture().dispose();
    }
}
