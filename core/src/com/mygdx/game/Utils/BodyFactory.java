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

    public BodyFactory(World world){

        this.world = world;
    }

    public static Body createStaticBody(int x, int y, int width, int height){

        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/Global.PPM, y/Global.PPM);
        bodyDef.fixedRotation = true;
        pBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/Global.PPM, height/2/Global.PPM );				//calcolato dal punto centrale

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    public Body createPlayerBody(int x, int y){

        Body pBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/2/Global.PPM, y/2/Global.PPM);
        bodyDef.fixedRotation = false;
        pBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(x/2/Global.PPM, y/2/Global.PPM));				//calcolato dal punto centrale
        shape.setRadius(Global.PPM/2/Global.PPM);

        FixtureDef circleFixture = new FixtureDef();
        circleFixture.density=1.0f;
        circleFixture.shape = shape;
        circleFixture.restitution = 0.8f;
        circleFixture.friction=0.6f;

        pBody.createFixture(circleFixture);
        shape.dispose();
        return pBody;
    }
}
