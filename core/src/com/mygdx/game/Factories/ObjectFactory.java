package com.mygdx.game.Factories;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.RealObjects.Board;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.RealObjects.LoadingSpinner;
import com.mygdx.game.Utils.GlobalVar;

import static com.mygdx.game.Utils.GlobalVar.screenHeight;
import static com.mygdx.game.Utils.GlobalVar.screenWidth;

public class ObjectFactory {

    private Stage stage;
    private float PPM;
    private BodyFactory bodyFactory;

    private Board board;
    private BounceBall ball;

    public ObjectFactory(World world, Stage stage){
        this.stage = stage;
        this.PPM = GlobalVar.PPM;
        this.bodyFactory = new BodyFactory(world);
        GlobalVar.boardHeight = PPM/4;
    }

    public BounceBall createBounceBallObject(float x, float y, float diam){

        if (this.ball == null) { this.ball = new BounceBall(); }
        Body body = this.bodyFactory.createCircleDinamicBody(0, 0, diam);
        body.setTransform(x/PPM, y/PPM, 0);
        this.ball.setBody(body);
        this.ball.setDiam(diam);

        this.stage.addActor(ball);
        return ball;
    }

    public Board createBoardObject(float x, float y, float xFin, float yFin){
        float ord = yFin-y;
        float asc = xFin-x;
        if (ord == 0 && asc == 0) return null;
        float ipo = (float)Math.sqrt(Math.pow(ord, 2) + Math.pow(asc, 2));
        float angle = asc > 0 ? (float)Math.asin(ord/ipo) : -(float)Math.asin(ord/ipo);

        if (this.board == null) { this.board = new Board(); }
        Body boardBody = this.bodyFactory.getBoardBody(x, y);
        this.board.setBody(boardBody);
        this.board.getBody().setTransform(new Vector2((x+asc/2)/PPM, (y+ord/2)/PPM), angle);
        this.board.setWidth(ipo);
        this.board.setHeight(GlobalVar.boardHeight);

        this.board.createFixture(this.board.getFixture());

        // board polygon
        float boardHalfMisure = (GlobalVar.boardHeight + PPM)   /2   /PPM;
        float boardExtension = (asc > 0)? x+ipo : x-ipo;
        Polygon polygon = asc > 0 ? new Polygon(new float[]{(x-PPM/3)/PPM, y/PPM - boardHalfMisure,
                                                    (boardExtension + PPM/3)/PPM, y/PPM - boardHalfMisure,
                                                    (boardExtension + PPM/3)/PPM, y/PPM + boardHalfMisure,
                                                     (x-PPM/3)/PPM, y/PPM + boardHalfMisure}) :
                                    new Polygon(new float[]{(x+PPM/3)/PPM, y/PPM - boardHalfMisure,
                                                    (boardExtension - PPM/3)/PPM, y/PPM - boardHalfMisure,
                                                    (boardExtension - PPM/3)/PPM, y/PPM + boardHalfMisure,
                                                    (x+PPM/3)/PPM, y/PPM + boardHalfMisure});

        polygon.setOrigin(x/PPM, y/PPM);
        double d = Math.toDegrees(angle);
        this.board.setAngle(d);
        polygon.rotate((float)d);

        board.setPolygon(polygon);

        if(board.getStage() == null) this.stage.addActor(board);
        return board;
    }

    public Board createTableObject(float x, float y, float xFin, float yFin){

        float ord = yFin-y;
        float asc = xFin-x;
        if (ord == 0 && asc == 0) return null;
        float ipo = (float)Math.sqrt(Math.pow(ord, 2) + Math.pow(asc, 2));

        float angle = asc > 0 ? (float)Math.asin(ord/ipo) : -(float)Math.asin(ord/ipo);

        Board table = new Board();
        Body tableBody = this.bodyFactory.getTableBody(x, y);
        table.setBody(tableBody);
        table.getBody().setTransform(new Vector2((x+asc/2)/PPM, (y+ord/2)/PPM), angle);
        table.setWidth(ipo);
        table.setHeight(GlobalVar.boardHeight);

        table.createFixture(table.getFixture());

        this.stage.addActor(table);
        return table;
    }

    public LoadingSpinner createLoadingSpinner(float diam){
        LoadingSpinner spinner = new LoadingSpinner(diam);
        this.stage.addActor(spinner);
        return spinner;
    }

    public void createScreen1BoundaryTiledWorld(){
        this.bodyFactory.createRectStaticBody(screenWidth/2, -1, screenWidth, 1);
    }

    public void createScreen2Boundaries(float x, float y){
        this.bodyFactory.createRectStaticBody(PPM*-6-1, y, 1, screenHeight);
        this.bodyFactory.createRectStaticBody(PPM*6+1, y, 1, screenHeight);
    }

    public void createScreen3Boundaries(){
        this.bodyFactory.createRectStaticBody(PPM*-6-1, PPM*0, 1, screenHeight);
        this.bodyFactory.createRectStaticBody(PPM*6+1, PPM*0, 1, screenHeight);
        this.bodyFactory.createRectStaticBody(PPM*0, -screenHeight/2-1, screenWidth, 1);
    }

    public void createScreen4Boundaries(){
        this.bodyFactory.createRectStaticBody(PPM*-6-1, PPM*0, 1, screenHeight);       //x-1
        this.bodyFactory.createRectStaticBody(PPM*6+1, PPM*0, 1, screenHeight);        //x+1
        this.bodyFactory.createRectStaticBody(PPM*0, -screenHeight/2-1-2*PPM, screenHeight, 4*PPM);      //y-1
        this.bodyFactory.createRectStaticBody(PPM*0, screenHeight/2+1+2, screenHeight, 1);       //y+1
    }

}
