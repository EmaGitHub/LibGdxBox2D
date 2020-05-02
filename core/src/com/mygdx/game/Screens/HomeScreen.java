package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.Global;

public class HomeScreen extends ScreenAdapter {

    private AppGame game;
    private OrthographicCamera camera;
    private Stage homeStage;
    private float PPM;

    Skin skin;
    BitmapFont titleFont;
    BitmapFont textFont;

    Label gameTitle;
    Label startButtonLabel;
    TextButton startButton;
    Label exitButtonLabel;
    TextButton exitButton;
    Label testButtonLabel;
    TextButton testButton;

    public HomeScreen(AppGame game){
        this.game = game;
        this.camera = game.camera;
        this.homeStage = new Stage(game.viewport, game.batch);
        PPM = Global.PPM;
        skin = new Skin(Gdx.files.internal("Skins/skin/glassy-ui.json"));
    }

    @Override
    public void show(){                                             // Prima funzione chiamata in Screen

        createScreenContent();
        testButtonLabel.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true;
            }
        });
        startButtonLabel.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                return true;
            }
        });
        exitButtonLabel.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        Gdx.input.setInputProcessor(this.homeStage);
    }

    private void createScreenContent(){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Griffin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int)PPM*2;
        titleFont = generator.generateFont(parameter);
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;
        titleStyle.fontColor = Color.BLUE;

        gameTitle = new Label("Bounce\nWorld", titleStyle);
        gameTitle.setSize(6*PPM, PPM);
        gameTitle.setPosition(-3*PPM, 7*PPM);
        gameTitle.setAlignment(Align.center);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/HennyPenny-Regular.otf"));

        parameter.size = (int)PPM/3*2;
        textFont = generator.generateFont(parameter);
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;

        testButtonLabel = new Label("Test", textStyle);
        testButtonLabel.setSize(PPM*6, PPM*2);
        testButtonLabel.setPosition(-3*PPM,-2*PPM);
        testButtonLabel.setAlignment(Align.center);
        testButton = new TextButton("",skin,"small");
        testButton.setSize(PPM*6, PPM*2);
        testButton.setPosition(-3*PPM,-2*PPM);
        startButtonLabel = new Label("Start game", textStyle);
        startButtonLabel.setSize(PPM*6, PPM*2);
        startButtonLabel.setPosition(-3*PPM,-5*PPM);
        startButtonLabel.setAlignment(Align.center);
        startButton = new TextButton("",skin,"small");
        startButton.setSize(PPM*6, PPM*2);
        startButton.setPosition(-3*PPM,-5*PPM);
        exitButtonLabel = new Label("Quit", textStyle);
        exitButtonLabel.setSize(PPM*6, PPM*2);
        exitButtonLabel.setPosition(-3*PPM,-8*PPM);
        exitButtonLabel.setAlignment(Align.center);
        exitButton = new TextButton("",skin,"small");
        exitButton.setSize(PPM*6, PPM*2);
        exitButton.setPosition(-3*PPM,-8*PPM);
    }

    @Override
    public void render(float delta) {
        cameraUpdate();
        homeStage.addActor(gameTitle);
        homeStage.addActor(testButton);
        homeStage.addActor(testButtonLabel);
        homeStage.addActor(startButton);
        homeStage.addActor(startButtonLabel);
        homeStage.addActor(exitButton);
        homeStage.addActor(exitButtonLabel);
        Gdx.gl.glClearColor(.05f, .05f, .05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        homeStage.act(delta);
        homeStage.draw();
    }

    public void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = 0;
        position.y = 0;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
