package com.mygdx.game.RealObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;

public class Board extends Object{

    float stateTime;

    TextureRegion currentFrame;
    TextureRegion pausedFrame;
    Animation<TextureRegion> framesAnimation;
    private boolean PAUSED;

    private ShapeRenderer shapeRenderer;
    private Polygon polygon;

    public Board(Body body){
        super(body);
        stateTime = 0f;
        this.shapeRenderer = new ShapeRenderer();
  }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        if(!GlobalVar.DEBUG) batch.draw(currentFrame, body.getPosition().x*PPM - rad,
//                body.getPosition().y*PPM - rad,
//                diam, diam);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        //if (isCollision());
    }

    public void setPolygon(Polygon polygon){
        this.polygon = polygon;
    }

    public Polygon getPolygon(){
        return this.polygon;
    }

    public void dispose(){ currentFrame.getTexture().dispose(); }
}
