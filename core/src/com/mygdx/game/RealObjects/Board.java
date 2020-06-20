package com.mygdx.game.RealObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.mygdx.game.Utils.GlobalVar.PPM;

public class Board extends Object{

    float stateTime;

    TextureRegion currentFrame;
    TextureRegion pausedFrame;
    Animation<TextureRegion> framesAnimation;
    private boolean PAUSED;

    private Polygon polygon;
    private FixtureDef fixtureDef;

    private double angle;
    private float width, height;

    public Board(){
        stateTime = 0f;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public FixtureDef getFixture(){
        if(this.fixtureDef != null) return this.fixtureDef;
        else return getNewFixture();
    }

    public void setFixture(FixtureDef fixture){
        this.fixtureDef = fixture;
    }

    public FixtureDef getNewFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);                //calcolato dal punto centrale

        FixtureDef boardFixture = new FixtureDef();
        boardFixture.shape = shape;
        boardFixture.density = 1.0f;
        boardFixture.restitution = 1.0f;
        boardFixture.friction = 0.6f;
        return boardFixture;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setPolygon(Polygon polygon){
        this.polygon = polygon;
    }

    public Polygon getPolygon(){
        return this.polygon;
    }

    public double getDeg360Angle() {

        boolean toRight = this.polygon.getVertices()[4] > this.polygon.getVertices()[6];
        double angle = this.getAngle();
        if(!toRight) return 90 + (90+angle);
        if(angle < 0) return 360 + angle;

        return angle;
    }

    public void  setBoardRestitution(float restitution){

        //this.getBody().destroyFixture();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);                //calcolato dal punto centrale

        FixtureDef boardFixture = new FixtureDef();
        boardFixture.shape = shape;
        boardFixture.density = 1.0f;
        boardFixture.restitution = restitution;
        boardFixture.friction = 0.6f;

        this.getBody().createFixture(boardFixture);
        shape.dispose();
    }

    public double getAngle(){
        return this.angle;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public void dispose(){ currentFrame.getTexture().dispose(); }
}
