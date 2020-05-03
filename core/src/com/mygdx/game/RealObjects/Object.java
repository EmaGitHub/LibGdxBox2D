package com.mygdx.game.RealObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Object extends Actor {

    protected Body body;
    private Vector2 velocity;

    public Object(Body body){
        this.body = body;
    }

    public void freezeObject(){
        this.velocity = this.body.getLinearVelocity().cpy();
        this.body.setLinearVelocity(new Vector2(0f, 0f));
    }

    public void resumeObject(){
        this.body.setLinearVelocity(this.velocity.set(velocity.x, velocity.y));
    }
}
