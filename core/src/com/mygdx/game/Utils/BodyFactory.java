package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

    static World world;
    private float PPM = Global.PPM;

    public BodyFactory(World world){

        this.world = world;
    }

    public Body createStaticBody(float x, float y, float width, float height){

        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/PPM, y/PPM);
        //System.out.println("Body position rect "+bodyDef.position.x+" real ("+x+") / "+bodyDef.position.y+" real ("+y+")");
        bodyDef.fixedRotation = true;
        pBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM );				//calcolato dal punto centrale


        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    public Body createBallBody(float x, float y, float diam){

        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/2/PPM, y/2/PPM);
        //System.out.println("Body position circle "+bodyDef.position.x+" real ("+x+") / "+bodyDef.position.y+" real ("+y+")");
        bodyDef.fixedRotation = false;
        pBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(x/2/PPM, y/2/PPM));				//calcolato dal punto centrale
        shape.setRadius(diam/2/PPM);

        FixtureDef circleFixture = new FixtureDef();
        circleFixture.density=1.0f;
        circleFixture.shape = shape;
        circleFixture.restitution = 1f;       //0,8f
        circleFixture.friction=0.6f;

        pBody.createFixture(circleFixture);
        shape.dispose();
        return pBody;
    }
}
