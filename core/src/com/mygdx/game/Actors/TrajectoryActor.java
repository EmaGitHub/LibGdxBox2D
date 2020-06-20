package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Factories.ObjectFactory;
import com.mygdx.game.RealObjects.Board;
import com.mygdx.game.Utils.BallEquation;

import static com.mygdx.game.Utils.GlobalVar.PPM;
import static java.lang.Math.abs;

public class TrajectoryActor extends Actor {

    private BallEquation ballEquation;
    private BallEquation bounceEquation;

//    private Texture imageSprite;
//    TextureRegion currentFrame;
//    Animation<TextureRegion> framesAnimation;
    private Vector2 firstCollisionPoint;

    public int trajectoryPointCount = 200;
    public float timeSeparation = 0.01f;

    private ShapeRenderer shapeRenderer;

    float stateTime;

    private Board board;

    private boolean changedAngle = false;

    private boolean firstDraw = true;

    private boolean bounceTrajectorySetted = false;
    float x1 , y1 , x2, y2;
    float x1b , y1b , x2b, y2b;

    private boolean upDirection;

    private ObjectFactory objectFactory;

    public TrajectoryActor(ObjectFactory objFactory) {

        this.objectFactory = objFactory;

        this.ballEquation = new BallEquation();
        this.bounceEquation = new BallEquation();

        this.stateTime = 0f;
//        this.framesAnimation = SpritesFactory.getGlobeFrames();
//        imageSprite = new Texture("Images/e.png");
        this.shapeRenderer = new ShapeRenderer();
    }

    public void setStartPoint(Vector2 startPoint){
        this.ballEquation.startPoint = startPoint;
    }
    public void setStartVelocity(Vector2 velocity){
        this.ballEquation.startVelocity = velocity;
    }

    public void setBounceStartPoint(Vector2 startPoint){
        this.bounceEquation.startPoint = startPoint;
    }
    public void setBounceStartVelocity(Vector2 velocity){
        this.bounceEquation.startVelocity = velocity;
    }
    public void resetchangedAngle(){
        this.changedAngle = false;
    }

    public boolean setFirstDraw(boolean value){
        this.firstDraw = value;
        return true;
    }
    public void setBounceTrajectorySetted(boolean value){
        this.bounceTrajectorySetted = value;
    }


    public Vector2 getStartPoint(){ return this.ballEquation.startPoint; }

    public Vector2 getStartVelocity(){
        return ballEquation.startVelocity;
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
        float tBounce = 0f;
        firstDraw = true;


        for (int i = 0; i < trajectoryPointCount; i++) {

//            float x =  ballEquation.getX(t)*PPM - 3;
//            float y = ballEquation.getY(t)*PPM -3 ;
//            batch.setColor(this.getColor());
//            batch.draw(this.currentFrame, x, y, 6, 6);
//            t += timeSeparation;

            batch.end();

            //draw trajectory Lines
            x1 = ballEquation.getX(t)*PPM;
            y1 = ballEquation.getY(t)*PPM;
            t += timeSeparation;
            x2 = ballEquation.getX(t)*PPM;
            y2 = ballEquation.getY(t)*PPM;

            if(firstDraw) {
                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.line(x1, y1, x2, y2);
                shapeRenderer.end();
            }
            if(this.board != null && this.isCollision(new Vector2(x1/PPM, y1/PPM), new Vector2(x2/PPM, y2/PPM))) {
                this.setBounceTrajectory(x1, y1,x2, y2);
                if(y2 < y1) this.upDirection = false;
                firstDraw = false;
            }
            batch.begin();
        }

        if (bounceTrajectorySetted == true){

            for (int j = 0; j < trajectoryPointCount; j++) {

                x1b = bounceEquation.getX(tBounce) * PPM;
                y1b = bounceEquation.getY(tBounce) * PPM;
                tBounce += timeSeparation;
                x2b = bounceEquation.getX(tBounce) * PPM;
                y2b = bounceEquation.getY(tBounce) * PPM;

                shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.line(x1b, y1b, x2b, y2b);
                shapeRenderer.end();
            }
        }
    }

    public void setBounceTrajectory(float x1, float y1, float x2, float y2){
        if (bounceTrajectorySetted == false){

            this.firstCollisionPoint = new Vector2(x2/PPM, y2/PPM);

            double ipo = Math.sqrt((double)(Math.pow((x2 - x1)/PPM, 2) + Math.pow((y2 - y1)/PPM, 2)));
            float ord = abs((y2-y1)/PPM);
            double angleSin = ord/ipo;
            double angle = Math.asin(angleSin) + Math.toRadians(this.board.getDeg360Angle()*2);

            //System.out.println("TRAJ. ANGLE "+(x2 - x1)+" BOARD ANGLE "+(x2 - x1)/PPM+" dfr "+Math.pow((x2 - x1)/PPM, 2));

            float xVelocity = (float) (ipo * PPM * Math.cos(angle));  //3.33
            float yVelocity = (float) (ipo * PPM * Math.sin(angle));

            //System.out.println("EXTI POS "+x2+" . "+y2+" EXIT VECTOR "+xVelocity+" . "+yVelocity);

            this.bounceEquation.startPoint = firstCollisionPoint;
            this.bounceEquation.startVelocity = new Vector2(xVelocity, yVelocity);

            this.bounceTrajectorySetted = true;
        }
    }

    public void setBoard(Board board){
        //System.out.println("ANGLE "+Math.toDegrees(board.getBody().getAngle()));
        this.board = board;
        this.resetchangedAngle();
    }

    public boolean isCollision(Vector2 lineStart, Vector2 lineEnd){

        return Intersector.intersectSegmentPolygon(lineStart, lineEnd, board.getPolygon());
    }

    public void getT() {
        float fixedHorizontalDistance = 10f;
        timeSeparation = ballEquation.getTForGivenX(fixedHorizontalDistance);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return null;
    }
}