package com.mygdx.game.RealObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Factories.SpritesFactory;
import com.mygdx.game.Utils.GlobalVar;

public class BounceBall extends Object{

    float PPM = GlobalVar.PPM;
    float diam;
    float rad;
    float stateTime;

    FixtureDef fixtureDef;

    TextureRegion currentFrame;
    TextureRegion pausedFrame;
    Animation<TextureRegion> framesAnimation;
    private boolean PAUSED;

    public BounceBall(){
        super();
        stateTime = 0f;
        this.framesAnimation = SpritesFactory.getGlobeFrames();
    }

    public void setDiam(float diam){
        this.diam = diam;
        this.rad = diam/2;
        setOrigin(rad, rad);
    }

    public void setBody(Body body){
        this.body = body;
        body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);		//per muovere numero metri al secondo
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!GlobalVar.DEBUG) batch.draw(currentFrame, body.getPosition().x*PPM - rad,
                body.getPosition().y*PPM - rad,
                diam, diam);
    }

    public FixtureDef getFixture(){
        if(this.fixtureDef != null) return this.fixtureDef;
        else return getNewFixture();
    }

    public void createFixture(FixtureDef fixture){
        this.getBody().createFixture(fixture);
    }

    public void createFixture(){
        this.getBody().createFixture(fixtureDef);
    }

    public FixtureDef getNewFixture(){
        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(this.getBody().getPosition().x/2/PPM, this.getBody().getPosition().y/2/PPM));				//calcolato dal punto centrale
        shape.setRadius(diam/2/PPM);

        FixtureDef circleFixture = new FixtureDef();
        circleFixture.shape = shape;
        circleFixture.density=1.0f;
        circleFixture.restitution = 0.0f;       //0.8f
        circleFixture.friction=0.6f;
        shape.dispose();
        return circleFixture;
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
