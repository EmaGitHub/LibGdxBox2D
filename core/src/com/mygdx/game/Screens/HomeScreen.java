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
import com.mygdx.game.Utils.GlobalVar;

public class HomeScreen extends AbstractScreen {

    Skin skin;
    BitmapFont titleFont;
    BitmapFont textFont;

    Label gameTitle;
    TextButton startButton;

    TextButton exitButton;
    TextButton testButton;
    TextButton tilesButton;
    TextButton debugButton;
    TextButton rootButton;

    public HomeScreen(final AppGame game){
        super(game);
        skin = new Skin(Gdx.files.internal("Skins/skin/glassy-ui.json"));
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
        gameTitle.setSize(6*PPM, 4*UHM-1);
        gameTitle.setPosition(-3*PPM, 5*UHM);
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
        testButton.setSize(PPM*4, UHM*2);
        testButton.setPosition(-5*PPM,2*UHM);
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

        tilesButton = new TextButton("",skin,"small");
        tilesButton.setLabel(new Label("Tiles", textStyle));
        tilesButton.getLabel().setAlignment(Align.center);
        tilesButton.setSize(PPM*4, UHM*2);
        tilesButton.setPosition(PPM,2*UHM);
        tilesButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TilesScreen(game));
            }
        });

        debugButton = new TextButton("",skin,"small");
        debugButton.setLabel(new Label("Check-Debug", textStyle));
        debugButton.getLabel().setAlignment(Align.center);
        debugButton.setSize(PPM*6, UHM*1);
        debugButton.setPosition(-3*PPM, UHM/2);
        debugButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GlobalVar.DEBUG = !GlobalVar.DEBUG;
            }
        });

        startButton = new TextButton("",skin,"small");
        startButton.setLabel(new Label("Start Game", textStyle));
        startButton.setSize(PPM*6, UHM*2);
        startButton.setPosition(-3*PPM,-3*UHM);
        startButton.getLabel().setAlignment(Align.center);
        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoadingScreen(game));
            }
        });

        rootButton = new TextButton("",skin,"small");
        rootButton.setLabel(new Label("Root", textStyle));
        rootButton.setSize(PPM*6, UHM*2);
        rootButton.setPosition(-3*PPM,-6*UHM);
        rootButton.getLabel().setAlignment(Align.center);
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

        exitButton = new TextButton("",skin,"small");
        exitButton.setLabel(new Label("Exit", textStyle));
        exitButton.setSize(PPM*6, UHM*2);
        exitButton.setPosition(-3*PPM,-9*UHM);
        exitButton.getLabel().setAlignment(Align.center);
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
    }

    @Override
    public void render(float delta) {
        super.clear();
        super.render(delta);
    }

    @Override
    public void show(){                                             // Prima funzione chiamata in Screen
        super.show();
        createScreenContent();
        stage.addActor(gameTitle);
        stage.addActor(testButton);
        stage.addActor(tilesButton);
        stage.addActor(debugButton);
        stage.addActor(startButton);
        stage.addActor(exitButton);
        stage.addActor(rootButton);
    }

    @Override
    public void hide() {
        super.hide();
    }
}
