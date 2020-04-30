package com.mygdx.game.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Objects.BounceBall;

public class ObjectFactory {

    static World world;
    static Stage stage;
    private Body body;
    private float PPM;
    private BodyFactory bodyFactory;

    public ObjectFactory(World world, Stage stage){
        this.world = world;
        this.stage = stage;
        this.PPM = Global.PPM;
        this.bodyFactory = new BodyFactory(world);
    }

    public BounceBall createBounceBallObject(float x, float y, float diam){
        this.body = this.bodyFactory.createBallBody(0, 0, diam);
        BounceBall ball = new BounceBall(this.body);
        this.stage.addActor(ball);
        this.body.setTransform(new Vector2(x/PPM, y/PPM), 0);
        return ball;
    }

}
