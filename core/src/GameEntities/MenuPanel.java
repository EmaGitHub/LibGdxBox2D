package GameEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.AppGame;
import com.mygdx.game.Screens.AbstractGameScreen;
import com.mygdx.game.Utils.GlobalVar;


public class MenuPanel extends Table {

    private AppGame appGame;

    ShapeRenderer shapeRenderer;
    Label gameTitle;
    Skin skin;
    private boolean closing = false;

    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;
    private float height = 0;
    private float maxHeight = (GlobalVar.heightInUHM*UHM) - 2*PPM - GlobalVar.safeAreaInsetTop;
    private float menuBackgroundPosition = (maxHeight - GlobalVar.safeAreaInsetTop)/2;
    private boolean restAdded = false;
    private float rest;

    private TextButton settingsButton;
    private TextButton exitButton;

    BitmapFont titleFont;
    BitmapFont textFont;

    public MenuPanel(final AppGame game){
        this.setWidth(10*PPM);
        this.setHeight(maxHeight);
        this.setPosition(-getWidth()/2, -(maxHeight + GlobalVar.safeAreaInsetTop)/2);
        rest = maxHeight%UHM;

        appGame = game;
        shapeRenderer = new ShapeRenderer();
        this.skin = new Skin(Gdx.files.internal("Skins/skin/glassy-ui.json"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Griffin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)PPM*2;
        titleFont = generator.generateFont(parameter);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;
        titleStyle.fontColor = Color.WHITE;

        gameTitle = new Label("Project", titleStyle);
        gameTitle.setAlignment(Align.center);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/HennyPenny-Regular.otf"));
        parameter.size = (int)PPM;
        textFont = generator.generateFont(parameter);

        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;

        settingsButton = new TextButton("",skin,"small");
        settingsButton.setLabel(new Label("Settings", textStyle));
        settingsButton.getLabel().setAlignment(Align.center);

        exitButton = new TextButton("", skin, "small");
        exitButton.setLabel(new Label("Exit", textStyle));
        exitButton.getLabel().setAlignment(Align.center);
        exitButton.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Exit");
                game.setScreen(game.homeScreen);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.add(gameTitle).top().padBottom(UHM*3).row();
        this.add(settingsButton).padBottom(UHM/2).row();
        this.add(exitButton).row();
    }

    public float getHeightAnimation(){
        if(closing) {
            if(height <= UHM) {
                ((AbstractGameScreen) this.appGame.getScreen()).closeMenuCallback();
                closing = false;
                restAdded = false;
                return height = 0;
            }
                return height -= PPM;
        }
        else if(height < maxHeight - UHM) return height += UHM;
        if(!restAdded) {
            this.height += rest;
            restAdded = true;
        }
        return height;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        batch.end();

        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.4f,0.6f,1f, 0.6f);  //0.7
        shapeRenderer.rect(-5*PPM, menuBackgroundPosition, PPM*10,
                -this.getHeightAnimation() );
        shapeRenderer.end();

        gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        super.draw(batch, parentAlpha);
    }

    public void openMenu(Stage stage){
        stage.addActor(this);
    }

    public void closeMenu(){
        this.closing = true;
    }
}
