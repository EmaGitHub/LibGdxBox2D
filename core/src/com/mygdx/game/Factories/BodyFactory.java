package com.mygdx.game.Factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Utils.GlobalVar;

public class BodyFactory {

    static World world;
    private float PPM = GlobalVar.PPM;

    private Body boardBody;

    public BodyFactory(World world){

        this.world = world;
    }

    public Body getTableBody(float x, float y, float width, float height){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/PPM, y/PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM );				//calcolato dal punto centrale

        FixtureDef tableFixture = new FixtureDef();
        tableFixture.shape = shape;
        tableFixture.density=1.0f;
        tableFixture.restitution = .8f;

        tableFixture.friction=0.6f;
        body.createFixture(tableFixture);

        shape.dispose();
        return body;
    }

    public Body getBoardBody(float x, float y, float width, float height){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/PPM, y/PPM);
        bodyDef.fixedRotation = true;
        if (boardBody != null) world.destroyBody(boardBody);
        boardBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM );				//calcolato dal punto centrale

        FixtureDef boardFixture = new FixtureDef();
        boardFixture.shape = shape;
        boardFixture.density=1.0f;
        boardFixture.restitution = 1.1f;
        boardFixture.friction=0.6f;
        boardBody.createFixture(boardFixture);

        shape.dispose();
        return boardBody;
    }

    public Body createRectStaticBody(float x, float y, float width, float height){
        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/PPM, y/PPM);
        bodyDef.fixedRotation = true;
        pBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM );				//calcolato dal punto centrale

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    public Body createCircleDinamicBody(float x, float y, float diam){
        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/PPM, y/PPM);
        bodyDef.fixedRotation = false;
        pBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(x/2/PPM, y/2/PPM));				//calcolato dal punto centrale
        shape.setRadius(diam/2/PPM);

        FixtureDef circleFixture = new FixtureDef();
        circleFixture.shape = shape;
        circleFixture.density=1.0f;
        circleFixture.restitution = .9f;       //0.8f
        circleFixture.friction=0.6f;
        pBody.createFixture(circleFixture);

        shape.dispose();
        return pBody;
    }

    public Body createRectDinamicBody(float x, float y, float width, float height){
        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/PPM, y/PPM);
        bodyDef.fixedRotation = true;
        pBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM );				//calcolato dal punto centrale

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }
}
