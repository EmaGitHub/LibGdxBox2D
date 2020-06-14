package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.RealObjects.Board;

import static com.mygdx.game.Utils.GlobalVar.PPM;

public class TrajectoryActor extends Actor {

    private ProjectileEquation projectileEquation;
//    private Texture imageSprite;
//    TextureRegion currentFrame;
//    Animation<TextureRegion> framesAnimation;

    public int trajectoryPointCount = 1;
    public float timeSeparation = 0.5f;

    private ShapeRenderer shapeRenderer;

    float stateTime;

    private Board board;

    public TrajectoryActor() {
        this.projectileEquation = new ProjectileEquation();

        this.stateTime = 0f;
//        this.framesAnimation = SpritesFactory.getGlobeFrames();
//        imageSprite = new Texture("Images/e.png");

        this.shapeRenderer = new ShapeRenderer();
    }

    public void setStartPoint(Vector2 startPoint){
        this.projectileEquation.startPoint = startPoint;
    }

    public void setStartVelocity(Vector2 velocity){
        projectileEquation.startVelocity = velocity;
    }

    public Vector2 getStartPoint(){
        return this.projectileEquation.startPoint;
    }

    public Vector2 getStartVelocity(){
        return projectileEquation.startVelocity;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        //this.currentFrame = framesAnimation.getKeyFrame(stateTime, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float t = 0f;

        float x1 = 0, y1 = 0, x2, y2;

        for (int i = 0; i < trajectoryPointCount; i++) {

//            float x =  projectileEquation.getX(t)*PPM - 3;
//            float y = projectileEquation.getY(t)*PPM -3 ;
//
//            batch.setColor(this.getColor());
//            batch.draw(this.currentFrame, x, y, 6, 6);
//            t += timeSeparation;

            batch.end();

            //draw trajectory Lines
            if(x1 == 0)x1 = projectileEquation.getX(t)*PPM;
            if(y1 == 0)y1 = projectileEquation.getY(t)*PPM;
            t += timeSeparation;
            x2 = projectileEquation.getX(t)*PPM;
            y2 = projectileEquation.getY(t)*PPM;
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(x1, y1, x2, y2);
            shapeRenderer.end();

            if(this.board != null && this.isCollision(new Vector2(x1, y1), new Vector2(x2, y2))) {
                //System.out.println("COLLISION");
            }

            x1 = x2;
            y1 = y2;
            batch.begin();
        }

    }

    public void setBoard(Board board){
        //System.out.println("SET BOARD "+board);
        this.board = board;
    }

    public boolean isCollision(Vector2 lineStart, Vector2 lineEnd){

//        System.out.println("IS COLLISION ls "+lineStart.x+" , "+lineStart.y+
//                " le "+lineEnd.x+" , "+lineEnd.y+ " board "
//                +this.board.getPolygon().getX()+ " / "+this.board.getPolygon().getY()+
//                " Vertices: "+this.board.getPolygon().getVertices().toString());

 //       System.out.println("TEST");

        return Intersector.intersectSegmentPolygon(lineStart, lineEnd, board.getPolygon());
        //return false;
    }

    public void getT() {
        float fixedHorizontalDistance = 10f;
        timeSeparation = projectileEquation.getTForGivenX(fixedHorizontalDistance);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return null;
    }
}