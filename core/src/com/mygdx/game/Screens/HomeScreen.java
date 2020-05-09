package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AppGame;

public class HomeScreen extends AbstractScreen {

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
    Label rootButtonLabel;
    TextButton rootButton;

    public HomeScreen(AppGame game){
        super(game);
        skin = new Skin(Gdx.files.internal("Skins/skin/glassy-ui.json"));
        menuButtonVisible = false;
        scoreBoardVisible = false;
        freezeButtonVisible = false;
        moveButtonVisible = false;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata in Screen
        super.show();
        createScreenContent();
        testButtonLabel.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TestScreen(game));
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
        rootButtonLabel.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new RootScreen(game));
                return true;
            }
        });
    }

    private void createScreenContent(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Griffin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)PPM*2;
        titleFont = generator.generateFont(parameter);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;
        titleStyle.fontColor = Color.BLUE;

        gameTitle = new Label("Lorem\nIpsum", titleStyle);
        gameTitle.setSize(6*PPM, 6*PPM);
        gameTitle.setPosition(-3*PPM, 4*PPM);
        gameTitle.setAlignment(Align.center);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/HennyPenny-Regular.otf"));

        parameter.size = (int)PPM/3*2;
        textFont = generator.generateFont(parameter);
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;

        testButtonLabel = new Label("Test", textStyle);
        testButtonLabel.setSize(PPM*6, PPM*2);
        testButtonLabel.setPosition(-3*PPM,-1*PPM);
        testButtonLabel.setAlignment(Align.center);
        testButton = new TextButton("",skin,"small");
        testButton.setSize(PPM*6, PPM*2);
        testButton.setPosition(-3*PPM,-1*PPM);
        startButtonLabel = new Label("Start game", textStyle);
        startButtonLabel.setSize(PPM*6, PPM*2);
        startButtonLabel.setPosition(-3*PPM,-4*PPM);
        startButtonLabel.setAlignment(Align.center);
        startButton = new TextButton("",skin,"small");
        startButton.setSize(PPM*6, PPM*2);
        startButton.setPosition(-3*PPM,-4*PPM);
        exitButtonLabel = new Label("Quit", textStyle);
        exitButtonLabel.setSize(PPM*6, PPM*2);
        exitButtonLabel.setPosition(-3*PPM,-7*PPM);
        exitButtonLabel.setAlignment(Align.center);
        exitButton = new TextButton("",skin,"small");
        exitButton.setSize(PPM*6, PPM*2);
        exitButton.setPosition(-3*PPM,-7*PPM);
        rootButtonLabel = new Label("Root", textStyle);
        rootButtonLabel.setSize(PPM*6, PPM*2);
        rootButtonLabel.setPosition(-3*PPM,-10*PPM);
        rootButtonLabel.setAlignment(Align.center);
        rootButton = new TextButton("",skin,"small");
        rootButton.setSize(PPM*6, PPM*2);
        rootButton.setPosition(-3*PPM,-10*PPM);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.addActor(gameTitle);
        stage.addActor(testButton);
        stage.addActor(testButtonLabel);
        stage.addActor(startButton);
        stage.addActor(startButtonLabel);
        stage.addActor(exitButton);
        stage.addActor(exitButtonLabel);
        stage.addActor(rootButton);
        stage.addActor(rootButtonLabel);
    }
}
