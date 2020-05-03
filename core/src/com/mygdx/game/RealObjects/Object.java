package com.mygdx.game.RealObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Object extends Actor {

    protected Body body;

    public Object(Body body){
        this.body = body;
    }

    public void freezeObject(){
        this.body.setLinearVelocity(new Vector2(0, 0));
    }
}
