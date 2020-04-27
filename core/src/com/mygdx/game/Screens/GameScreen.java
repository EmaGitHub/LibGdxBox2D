package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.BodyFactory;
import com.mygdx.game.Utils.Global;

public class GameScreen extends ScreenAdapter {

    private AppGame game;

    private OrthographicCamera camera;
    private boolean DEBUG = false;
    private float PPM;

    private Box2DDebugRenderer b2dr;
    private World world;
    private BodyFactory bodyFactory;
    private Body player, platform;

    Vector3 touchPoint;
    private boolean firstTouch = true;

    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 8, FRAME_ROWS = 6;
    // Objects used
    Animation<TextureRegion> worldAnimation; // Must declare frame type (TextureRegion)
    private Texture worldSheet;
    // A variable for tracking elapsed time for the animation
    float stateTime;

    public GameScreen(AppGame game){

        this.game = game;
        this.PPM = Global.PPM;
        this.camera = game.camera;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata

        world = new World(new Vector2(0, -9.8f), false);	//-9.8f
        bodyFactory = new BodyFactory(world);
        b2dr = new Box2DDebugRenderer();
        touchPoint = new Vector3();

        player = bodyFactory.createPlayerBody(0, 0);
        platform = bodyFactory.createStaticBody(0, -4*(int)PPM, 4*(int)PPM, (int)PPM/4);

        createSprite();
    }

    public void createSprite(){

        // Load the sprite sheet as a Texture
        worldSheet = new Texture(Gdx.files.internal("Sprites/worldSprite.gif"));
        TextureRegion[][] tmp = TextureRegion.split(worldSheet,
                worldSheet.getWidth() / FRAME_COLS,
                worldSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        worldAnimation = new Animation<TextureRegion>(0.010f, walkFrames);
        stateTime = 0f;
    }

    @Override
    public void render(float delta){

        // Update Logic
        this.update(Gdx.graphics.getDeltaTime());

        // Draw Render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = worldAnimation.getKeyFrame(stateTime, true);

//        game.batch.begin();
//        game.batch.draw(currentFrame,
//                player.getPosition().x * PPM - (int)PPM/2 ,
//                player.getPosition().y * PPM - (int)PPM/2 ,
//                1*(int)PPM, 1*(int)PPM);
//        game.batch.end();

        b2dr.render(world, camera.combined.scl(PPM));
    }
    public void update(float delta) {

        world.step(1/60f, 6, 2);

        inputUpdate(delta);
        cameraUpdate(delta);

        game.batch.setProjectionMatrix(camera.combined);
    }

    public void inputUpdate(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))player.applyForceToCenter(-6, 0, false);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))player.applyForceToCenter(6, 0, false);
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))player.applyForceToCenter(0, 300, false);
        if(Gdx.input.justTouched()){

            if(firstTouch) firstTouch = false;
            else {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                bodyFactory.createPlayerBody((int) (touchPoint.x), (int) (touchPoint.y));

                if (!DEBUG) {
                    platform.setTransform(0, -4, 0.5f);
                    player.applyForceToCenter(0, 300, false);
                    DEBUG = true;
                } else {
                    platform.setTransform(0, -4, 0f);
                    player.applyForceToCenter(-0, 300, false);
                    DEBUG = false;
                }
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        player.setLinearVelocity(player.getLinearVelocity().x, player.getLinearVelocity().y);		//per muovere numero metri al secondo
    }


    public void cameraUpdate(float delta) {

        Vector3 position = camera.position;				//per centrare camera sul player
        position.x = 0; //player.getPosition().x * PPM;
        position.y = 0; //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void dispose () {
        world.dispose();
        b2dr.dispose();
    }
}
