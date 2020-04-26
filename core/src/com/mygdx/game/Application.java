package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.mygdx.game.Utils.Constants.PPM;

public class Application extends ApplicationAdapter {

	private boolean DEBUG = false;
	private float SCALE = 1.0f;
	private float scaleTot;

	float screenWidth, screenHeight;
	Vector3 touchPoint;

	private OrthographicCamera camera;
	private Viewport viewport;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player, platform;

	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 8, FRAME_ROWS = 6;
	// Objects used
	Animation<TextureRegion> worldAnimation; // Must declare frame type (TextureRegion)
	private SpriteBatch batch;
	private Texture worldSheet;
	// A variable for tracking elapsed time for the animation
	float stateTime;

	@Override
	public void create () {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		System.out.println("Screen size: "+screenWidth+" x "+screenHeight);
		touchPoint = new Vector3();

		float scale1 = 270 / screenWidth;
		float scale2 = 480 / screenHeight;
		scaleTot = (scale1 + scale2) / 2;

		this.camera = new OrthographicCamera();
		this.viewport = new StretchViewport(screenWidth, screenHeight, camera);
		viewport.apply();
		System.out.println("Viewport created on Camera");

		world = new World(new Vector2(0, -9.8f), false);	//-9.8f
		b2dr = new Box2DDebugRenderer();
		batch = new SpriteBatch();

		player = createPlayerBody(0, 0, 32, 32);
		platform = createStaticBody(0, -128, 128, 8);
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

	public Body createStaticBody(int x, int y, int width, int height){

		Body pBody;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(x/2/PPM, y/2/PPM);
		bodyDef.fixedRotation = true;
		pBody = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2/PPM, height/2/PPM );				//calcolato dal punto centrale

		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		return pBody;
	}

	public Body createPlayerBody(int x, int y, int width, int height){

		Body pBody;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x/2/PPM, y/2/PPM);
		bodyDef.fixedRotation = false;
		pBody = world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2(x/2/PPM, y/2/PPM));				//calcolato dal punto centrale
		shape.setRadius(16/PPM);

		FixtureDef circleFixture = new FixtureDef();
		circleFixture.density=1.0f;
		circleFixture.shape = shape;
		circleFixture.restitution = 0.8f;
		circleFixture.friction=0.0f;

		pBody.createFixture(circleFixture);
		shape.dispose();
		return pBody;
	}

	@Override
	public void resize (int width, int height){
		camera.setToOrtho(false, width/SCALE, height/SCALE);
		viewport.update(width/(int)SCALE, height/(int)SCALE);
	}

	@Override
	public void render () {
		// Update Logic
		this.update(Gdx.graphics.getDeltaTime());

		// Render Drow
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = worldAnimation.getKeyFrame(stateTime, true);

		batch.begin();
//		batch.draw(currentFrame,
//				player.getPosition().x * PPM - 16 ,
//				player.getPosition().y * PPM - 16 ,
//				32, 32);
		batch.end();

		b2dr.render(world, camera.combined.scl(PPM));

		//System.out.println("Posititon: "+player.getPosition().x * PPM+"-"+player.getPosition().y * PPM);
		//System.out.println("Camera: "+screenWidth+"-"+screenHeight);

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
	}

	public void update(float delta) {

		world.step(1/60f, 6, 2);

		inputUpdate(delta);
		cameraUpdate(delta);

		batch.setProjectionMatrix(camera.combined);
	}

	public void inputUpdate(float delta) {

		int horizontalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			horizontalForce += 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			player.applyForceToCenter(0, 300, false);
		}
		if(Gdx.input.justTouched()){

			camera.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0));
			createPlayerBody((int)(touchPoint.x), (int)(touchPoint.y), 32, 32);

			if(!DEBUG){
				platform.setTransform(0, -4, 0.5f);
				player.applyForceToCenter(0, 300 , false);
				DEBUG = true;
			}
			else {
				platform.setTransform(0, -4, 0f);
				player.applyForceToCenter(-0, 300 , false);
				DEBUG = false;
			}

		}

		player.setLinearVelocity(player.getLinearVelocity().x, player.getLinearVelocity().y);		//per muovere numero metri al secondo
		//System.out.println("player.getLinearVelocity().y "+player.getLinearVelocity().y);
	}


	public void cameraUpdate(float delta) {

		Vector3 position = camera.position;				//per centrare camera sul player
		position.x = 0; //player.getPosition().x * PPM;
		position.y = 0; //player.getPosition().y * PPM;
		camera.position.set(position);

		//camera.zoom = scaleTot;
		camera.update();
	}
	
	@Override
	public void dispose () {
		world.dispose();
		b2dr.dispose();
		batch.dispose();
	}
}
