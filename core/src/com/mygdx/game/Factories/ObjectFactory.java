package com.mygdx.game.Factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Utils.Global;

public class ObjectFactory {

    static World world;
    static Stage stage;
    private float PPM;
    private FramesFactory frameFactory;

    public ObjectFactory(World world, Stage stage){
        this.world = world;
        this.stage = stage;
        this.PPM = Global.PPM;
        this.frameFactory = new FramesFactory(world);
    }

    public BounceBall createBounceBallObject(float x, float y, float diam){
        Body body = this.frameFactory.createBallBody(0, 0, diam);
        BounceBall ball = new BounceBall(body, diam);
        this.stage.addActor(ball);
        body.setTransform(new Vector2(x/PPM, y/PPM), 0);
        return ball;
    }

    public void createBox(float x, float y, float width, float height){
        Body body = this.frameFactory.createStaticBody(0, 0, width, height);
        body.setTransform(new Vector2(x/PPM, y/PPM), 0);
    }

    public void createScreenBoundaries(){
        this.frameFactory.createStaticBody(PPM*-6-1, PPM*0, 1, Gdx.graphics.getHeight());
        this.frameFactory.createStaticBody(PPM*6+1, PPM*0, 1, Gdx.graphics.getHeight());
        this.frameFactory.createStaticBody(PPM*0, -Gdx.graphics.getHeight()/2-1, Gdx.graphics.getHeight(), 1);
//        this.frameFactory.createStaticBody(PPM*-6-1, PPM*0, 1, Gdx.graphics.getHeight());
    }

}
