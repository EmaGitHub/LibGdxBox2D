package com.mygdx.game.Factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.RealObjects.LoadingSpinner;
import com.mygdx.game.Utils.GlobalVar;

import static com.mygdx.game.Utils.GlobalVar.screenHeight;
import static com.mygdx.game.Utils.GlobalVar.screenWidth;

public class ObjectFactory {

    private World world;
    private Stage stage;
    private float PPM;
    private FramesFactory frameFactory;

    public ObjectFactory(World world, Stage stage){
        this.world = world;
        this.stage = stage;
        this.PPM = GlobalVar.PPM;
        this.frameFactory = new FramesFactory(world);
    }

    public BounceBall createBounceBallObject(float x, float y, float diam){
        Body body = this.frameFactory.createCircleDinamicBody(0, 0, diam);
        BounceBall ball = new BounceBall(body, diam);
        body.setTransform(new Vector2(x/PPM, y/PPM), 0);
        this.stage.addActor(ball);
        return ball;
    }

    public LoadingSpinner createLoadingSpinner(float diam){
        LoadingSpinner spinner = new LoadingSpinner(diam);
        this.stage.addActor(spinner);
        return spinner;
    }

    public void createScreen1BoundaryTiledWorld(){
        this.frameFactory.createRectStaticBody(screenWidth/2, -1, screenWidth, 1);
    }

    public void createScreen2Boundaries(){
        this.frameFactory.createRectStaticBody(PPM*-6-1, PPM*0, 1, screenHeight);
        this.frameFactory.createRectStaticBody(PPM*6+1, PPM*0, 1, screenHeight);
    }

    public void createScreen3Boundaries(){
        this.frameFactory.createRectStaticBody(PPM*-6-1, PPM*0, 1, screenHeight);
        this.frameFactory.createRectStaticBody(PPM*6+1, PPM*0, 1, screenHeight);
        this.frameFactory.createRectStaticBody(PPM*0, -screenHeight/2-1, screenWidth, 1);
    }

    public void createScreen4Boundaries(){
        this.frameFactory.createRectStaticBody(PPM*-6-1, PPM*0, 1, screenHeight);       //x-1
        this.frameFactory.createRectStaticBody(PPM*6+1, PPM*0, 1, screenHeight);        //x+1
        this.frameFactory.createRectStaticBody(PPM*0, -screenHeight/2-1-2*PPM, screenHeight, 4*PPM);      //y-1
        this.frameFactory.createRectStaticBody(PPM*0, screenHeight/2+1+2, screenHeight, 1);       //y+1
    }

}
