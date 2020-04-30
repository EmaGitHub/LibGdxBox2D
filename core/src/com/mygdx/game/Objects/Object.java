package com.mygdx.game.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Object extends Actor {

    protected Body body;

    public Object(Body body){
        this.body = body;
    }
}
