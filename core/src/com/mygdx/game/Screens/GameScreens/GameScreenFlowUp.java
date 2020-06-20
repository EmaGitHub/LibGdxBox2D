package com.mygdx.game.Screens.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Actors.TrajectoryActor;
import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.Board;
import com.mygdx.game.RealObjects.BounceBall;
import com.mygdx.game.Screens.AbstractGameScreen;
import com.mygdx.game.Utils.GlobalVar;

import GameEntities.Controllers;

public class GameScreenFlowUp extends AbstractGameScreen {

    BounceBall ball;
    Board board;

    TrajectoryActor trajectoryActor;
    //this.trajectorySprite = new

    float difference = 0;
    float bottomLimit = 0;
    float bottomLimitBase = 0;

    boolean topLimitUpdated = true;
    float topLimit = 5;
    float topLimitBase = 5;

    float ballYValue;
    float prevBallYValue;

    float topBound;

    private ShapeRenderer shapeRenderer;

    public GameScreenFlowUp(final AppGame game){
        super(game);
        this.topBound = 8;
        controllers = new Controllers(0, 0,true, false, game);
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show(){
        super.show();
        this.objectFactory.createScreen3Boundaries();
        this.objectFactory.createScreen2Boundaries(0, UHM*10);
        this.ball = this.objectFactory.createBounceBallObject(0, 1*UHM, PPM);

        this.trajectoryActor = new TrajectoryActor(this.objectFactory);

        this.board = this.objectFactory.createBoardObject(-2*PPM, -8*UHM, 2*PPM, -8*UHM, 1.0f);

        this.objectFactory.createTableObject(-6*PPM, 6*UHM, -4*PPM, 6*UHM);
        this.objectFactory.createTableObject(2*PPM, 6*UHM, 6*PPM, 6*UHM);
        this.objectFactory.createTableObject(-6*PPM, 13*UHM, 0*UHM, 13*UHM);
        this.objectFactory.createTableObject(3*PPM, 13*UHM, 6*PPM, 13*UHM);
        this.objectFactory.createTableObject(-4*PPM, 20*UHM, 6*PPM, 20*UHM);
    }

    public TrajectoryActor getTrajectoryActor(){
        return this.trajectoryActor;
    }

    @Override
    public void render(float delta) {
        super.clear();
        super.render(delta);

        // creazione dinamica boundaries
        if (this.ball.getPosition().y > this.topBound) {
            this.objectFactory.createScreen2Boundaries(0, this.topBound * UHM + 20 * UHM);
            this.topBound += 20;
        }
        // disegno polygon board
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.setProjectionMatrix(this.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(this.board.getPolygon() != null) {
            shapeRenderer.polygon(this.board.getPolygon().getTransformedVertices());
        }
        shapeRenderer.end();
    }

    @Override
    public void touched(){
        Board board = this.objectFactory.createBoardObject(touchPointDown.x, touchPointDown.y,
                touchPointUp.x, touchPointUp.y, this.controllers.getRestitution());
        if(board != null) {
            this.board = board;
            if(this.trajectoryActor != null) {
                this.trajectoryActor.setBoard(this.board);
                this.trajectoryActor.setBounceTrajectorySetted(false);
            }
        }
    }

    @Override
    protected void touchEnd() {
    }

    protected void inputUpdate() {
        super.inputUpdate();
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            this.controllers.setRestitution(this.controllers.getRestitution()+0.02f);
            this.board.setBoardRestitution(this.controllers.getRestitution());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            this.controllers.setRestitution(this.controllers.getRestitution()-0.02f);
            this.board.setBoardRestitution(this.controllers.getRestitution());
        }
    }

    @Override
    protected void freezeScene() {

        //System.out.println("FREEZE ");
        this.trajectoryActor = new TrajectoryActor(this.objectFactory);
        if(this.board != null) {
            this.trajectoryActor.setBoard(this.board);
        }
        this.stage.addActor(this.trajectoryActor);
        this.trajectoryActor.setStartPoint(new Vector2(this.ball.getBody().getPosition()));
        this.trajectoryActor.setStartVelocity(new Vector2(this.ball.getBody().getLinearVelocity()));

//        BounceBall b = this.objectFactory.createBounceBallObject(this.ball.getBody().getPosition().x*PPM,
//                                                this.ball.getBody().getPosition().y*PPM, PPM, true);
//        b.getBody().setLinearVelocity(this.ball.getBody().getLinearVelocity());
        super.freezeScene();                    //gravity
        this.ball.freezeObject();
    }


    public void update() {
        super.update();
    }

    @Override
    protected void resumeScene() {
        this.trajectoryActor.addAction(Actions.removeActor());
        this.trajectoryActor = null;
        super.resumeScene();                    //gravity
        this.ball.resumeObject();
    }

    @Override
    protected void cameraUpdate() {
        Vector3 position = camera.position;
        position.x =  0;                                          //player.getPosition().x * PPM;

        this.ballYValue = this.ball.getPosition().y;

        if (this.ballYValue > this.prevBallYValue) {
            this.topLimitUpdated = false;
            this.difference = ballYValue - this.topLimit;
            if(difference > 0){
                this.bottomLimit =  difference * PPM + this.bottomLimitBase;
            }
        }
        else {
            if (!this.topLimitUpdated) {
                this.topLimit = topLimitBase + this.bottomLimit / PPM;
                this.bottomLimitBase = this.bottomLimit;
                this.topLimitUpdated = true;
            }
        }
        position.y = -GlobalVar.safeAreaInsetBottom + this.bottomLimit;

        this.prevBallYValue = this.ballYValue;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
        super.hide();
    }
}