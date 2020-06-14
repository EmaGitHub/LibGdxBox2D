package com.mygdx.game.Factories;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.RealObjects.Board;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.RealObjects.LoadingSpinner;
import com.mygdx.game.RealObjects.Test;
import com.mygdx.game.Utils.GlobalVar;

import static com.mygdx.game.Utils.GlobalVar.screenHeight;
import static com.mygdx.game.Utils.GlobalVar.screenWidth;

public class ObjectFactory {

    private Stage stage;
    private float PPM;
    private BodyFactory frameFactory;

    private float halfWidth = screenWidth/2;
    private float haltHeight = screenHeight/2;
    private Board board;

    public ObjectFactory(World world, Stage stage){
        this.stage = stage;
        this.PPM = GlobalVar.PPM;
        this.frameFactory = new BodyFactory(world);

        GlobalVar.boardHeight = PPM/4;
    }

    public BounceBall createBounceBallObject(float x, float y, float diam){
        Body body = this.frameFactory.createCircleDinamicBody(0, 0, diam);
        BounceBall ball = new BounceBall(body, diam);
        body.setTransform(new Vector2(x/PPM, y/PPM), 0);
        this.stage.addActor(ball);
        return ball;
    }

    public Board createBoardObject(float x, float y, float xFin, float yFin){

        float ord = yFin-y;
        float asc = xFin-x;
        if (ord == 0 && asc == 0) return null;
        float ipo = (float)Math.sqrt(Math.pow(ord, 2) + Math.pow(asc, 2));

        float angle = asc > 0 ? (float)Math.asin(ord/ipo) : -(float)Math.asin(ord/ipo);
        Body boardBody = this.frameFactory.getBoardBody(x, y, ipo, GlobalVar.boardHeight);
        if (this.board == null) this.board = new Board(boardBody);

        float boardHalfMisure = GlobalVar.boardHeight/2 / PPM;
        float boardExtension = (asc > 0)? x+ipo : x-ipo;

        Polygon polygon = new Polygon(new float[]{x/PPM, y/PPM - boardHalfMisure,
                                                    boardExtension/PPM, y/PPM - boardHalfMisure,
                                                    boardExtension/PPM, y/PPM + boardHalfMisure,
                                                     x/PPM, y/PPM + boardHalfMisure});
        polygon.setOrigin(x/PPM, y/PPM);
        double d = Math.toDegrees(angle);
        polygon.rotate((float)d);

        board.setPolygon(polygon);


        boardBody.setTransform(new Vector2((x+asc/2)/PPM, (y+ord/2)/PPM), angle);
        if(board.getStage() == null) this.stage.addActor(board);
        return board;
    }



    public Board createTableObject(float x, float y, float xFin, float yFin){

        float ord = yFin-y;
        float asc = xFin-x;
        if (ord == 0 && asc == 0) return null;
        float ipo = (float)Math.sqrt(Math.pow(ord, 2) + Math.pow(asc, 2));

        float angle = asc > 0 ? (float)Math.asin(ord/ipo) : -(float)Math.asin(ord/ipo);
        Body boardBody = this.frameFactory.getTableBody(x, y, ipo, PPM/4);
        Board board = new Board(boardBody);

        boardBody.setTransform(new Vector2((x+asc/2)/PPM, (y+ord/2)/PPM), angle);
        if(board.getStage() == null) this.stage.addActor(board);
        return board;
    }

    public Test createTestObject(float x, float y, float diam){
        Body body = this.frameFactory.createCircleDinamicBody(0, 0, diam);
        Test test = new Test(body, diam);
        body.setTransform(new Vector2(x/PPM, y/PPM), 0);
        this.stage.addActor(test);
        return test;
    }

    public LoadingSpinner createLoadingSpinner(float diam){
        LoadingSpinner spinner = new LoadingSpinner(diam);
        this.stage.addActor(spinner);
        return spinner;
    }

    public void createScreen1BoundaryTiledWorld(){
        this.frameFactory.createRectStaticBody(screenWidth/2, -1, screenWidth, 1);
    }

    public void createScreen2Boundaries(float x, float y){
        this.frameFactory.createRectStaticBody(PPM*-6-1, y, 1, screenHeight);
        this.frameFactory.createRectStaticBody(PPM*6+1, y, 1, screenHeight);
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
