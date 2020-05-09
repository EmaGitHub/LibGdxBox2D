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
    TextButton startButton;
    TextButton exitButton;
    TextButton testButton;
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
        testButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TestScreen(game));
            }
        });
        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
        });
        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });
        rootButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new RootScreen(game));
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
        gameTitle.setSize(6*PPM, 5*PPM);
        gameTitle.setPosition(-3*PPM, 4*PPM);
        gameTitle.setAlignment(Align.center);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/HennyPenny-Regular.otf"));

        parameter.size = (int)PPM/3*2;
        textFont = generator.generateFont(parameter);
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;

        testButton = new TextButton("",skin,"small");
        testButton.setLabel(new Label("Test", textStyle));
        testButton.getLabel().setAlignment(Align.center);
        testButton.setSize(PPM*6, PPM*2);
        testButton.setPosition(-3*PPM,0*PPM);

        startButton = new TextButton("",skin,"small");
        startButton.setSize(PPM*6, PPM*2);
        startButton.setPosition(-3*PPM,-3*PPM);
        startButton.setLabel(new Label("Start", textStyle));
        startButton.getLabel().setAlignment(Align.center);

        rootButton = new TextButton("",skin,"small");
        rootButton.setSize(PPM*6, PPM*2);
        rootButton.setPosition(-3*PPM,-6*PPM);
        rootButton.setLabel(new Label("Root", textStyle));
        rootButton.getLabel().setAlignment(Align.center);

        exitButton = new TextButton("",skin,"small");
        exitButton.setSize(PPM*6, PPM*2);
        exitButton.setPosition(-3*PPM,-9*PPM);
        exitButton.setLabel(new Label("Exit", textStyle));
        exitButton.getLabel().setAlignment(Align.center);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.addActor(gameTitle);
        stage.addActor(testButton);
        stage.addActor(startButton);
        stage.addActor(exitButton);
        stage.addActor(rootButton);
    }
}
