package com.mygdx.game.Actors;

import com.badlogic.gdx.math.Vector2;

public class ProjectileEquation {

    public float gravity = -9.8f;
    public Vector2 startVelocity = new Vector2();
    public Vector2 startPoint = new Vector2();

    public float getX(float t) {
        return startVelocity.x * t + startPoint.x;
    }

    public float getY(float t) {
        return 0.5f * gravity * t * t + startVelocity.y * t + startPoint.y;
    }

    public float getTForGivenX(float x) {
        // x = startVelocity.x * t + startPoint.x
        // x - startPoint.x = startVelocity.x * t
        // t = (x - startPoint.x) / (startVelocity.x);
        return (x - startPoint.x) / (startVelocity.x);
    }
}