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

//        bodySprite = new Sprite(new Texture("Images/ball.png"));
//        bodySprite.setSize(PPM*1 , PPM*1);
//        bodySprite.setOrigin( PPM/2 , PPM/2);
//        body.setUserData(bodySprite);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

//        Sprite sprite = (Sprite) this.body.getUserData();
//        sprite.setPosition((this.body.getPosition().x)*PPM - sprite.getWidth()/2, (this.body.getPosition().y)*PPM - sprite.getHeight()/2);
//        sprite.setRotation(this.body.getAngle() * MathUtils.radiansToDegrees);
//        sprite.draw(batch);

        batch.draw(currentFrame, super.body.getPosition().x*PPM - rad,
                super.body.getPosition().y*PPM - rad,
                diam , diam);

//        System.out.println("BODY Y "+(super.body.getPosition().y));
//        System.out.println("B Y "+(super.body.getPosition().y));
        //System.out.println("position: "+(super.body.getPosition().x*PPM-rad)+"    .    "+ (super.body.getPosition().y*PPM-rad));

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

    public void dispose(){
        currentFrame.getTexture().dispose();
    }
}
