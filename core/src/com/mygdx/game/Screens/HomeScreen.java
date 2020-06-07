package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.GlobalVar;

public class HomeScreen extends AbstractScreen {

    Skin skin;
    Table table;
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
        table = new Table();
        createScreenContent();
        float screenHeight = Gdx.graphics.getHeight();
        table.add(gameTitle).padBottom(PPM/2).padTop(GlobalVar.safeAreaInsetTop).row();
        table.add(testButton).padBottom(PPM/2).height(UHM).row();
        table.add(tilesButton).padBottom(PPM/2).height(UHM).row();
        table.add(debugButton).padBottom(PPM/2).row();
        table.add(startButton).height(UHM).padBottom(PPM/2).row();
        table.add(rootButton).height(UHM).padBottom(PPM/2).row();
        table.add(exitButton).height(UHM).padBottom(PPM/2).row();
    }

    @Override
    public void show(){
        super.show();
        stage.addActor(table);
    }

    private void createScreenContent(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Griffin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)UHM*2;
        titleFont = generator.generateFont(parameter);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;
        titleStyle.fontColor = Color.WHITE;

        gameTitle = new Label("Lorem\nIpsum", titleStyle);
        gameTitle.setAlignment(Align.center);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/HennyPenny-Regular.otf"));
        parameter.size = (int)PPM/3*2;
        textFont = generator.generateFont(parameter);

        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;

        testButton = new TextButton("",skin,"small");
        Label testLabel = new Label("Test", textStyle);
        testLabel.setAlignment(Align.center);
        testButton.setLabel(testLabel);
        tilesButton = new TextButton("",skin,"small");
        Label tilesLabel = new Label("Tiles", textStyle);
        tilesLabel.setAlignment(Align.center);
        tilesButton.setLabel(tilesLabel);
        debugButton = new TextButton("",skin,"small");
        Label debugLabel = new Label("Check-debug", textStyle);
        debugLabel.setAlignment(Align.center);
        debugButton.setLabel(debugLabel);
        startButton = new TextButton("",skin,"small");
        Label startLabel = new Label("Start", textStyle);
        startLabel.setAlignment(Align.center);
        startButton.setLabel(startLabel);
        rootButton = new TextButton("",skin,"small");
        Label rootLabel = new Label("Root", textStyle);
        rootLabel.setAlignment(Align.center);
        rootButton.setLabel(rootLabel);
        exitButton = new TextButton("",skin,"small");
        Label exitLabel = new Label("Exit", textStyle);
        exitLabel.setAlignment(Align.center);
        exitButton.setLabel(exitLabel);

        setButtonListener();
    }

    private void setButtonListener(){
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
}
