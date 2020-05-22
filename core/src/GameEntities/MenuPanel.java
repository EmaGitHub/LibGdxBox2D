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
    private float x, y;

    ShapeRenderer shapeRenderer;
    Label gameTitle;
    Skin skin;

    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;
    private float height = 0;
    private float maxHeight;
    private float menuBackgroundPosition;
    private boolean restAdded = false;
    private float rest;

    private TextButton settingsButton;
    private TextButton debugButton;
    private TextButton exitButton;

    BitmapFont titleFont;
    BitmapFont textFont;

    private float opacity = 0;
    private boolean closing = false;

    public MenuPanel(float x, float y, final AppGame game){

        this.x = x;
        this.y = y;
        this.setWidth(10*PPM);
        maxHeight = (GlobalVar.heightInUHM*UHM) - 2*PPM - GlobalVar.safeAreaInsetTop;
        menuBackgroundPosition = (maxHeight - GlobalVar.safeAreaInsetTop)/2;
        this.setHeight(maxHeight);
        this.setPosition(x-getWidth()/2, y-(maxHeight + GlobalVar.safeAreaInsetTop)/2);
        rest = maxHeight%UHM;

        appGame = game;
        shapeRenderer = new ShapeRenderer();
        this.skin = new Skin(Gdx.files.internal("Skins/skin/glassy-ui.json"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Griffin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)UHM*2;
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

        debugButton = new TextButton("",skin,"small");
        debugButton.setLabel(new Label("Debug", textStyle));
        debugButton.getLabel().setAlignment(Align.center);
        debugButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GlobalVar.DEBUG = !GlobalVar.DEBUG;
                return false;
            }
        });


        exitButton = new TextButton("", skin, "small");
        exitButton.setLabel(new Label("Exit", textStyle));
        exitButton.getLabel().setAlignment(Align.center);
        exitButton.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.homeScreen);
                return false;
            }
        });

        this.add(gameTitle).height(UHM).padBottom(UHM*6).row();
        this.add(debugButton).height(2*UHM).padBottom(UHM).row();
        this.add(settingsButton).height(2*UHM).padBottom(UHM).row();
        this.add(exitButton).height(2*UHM).row();
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
        shapeRenderer.setColor(0.4f,0.2f,1f, 0.8f);  //0.7
        shapeRenderer.rect(x-5*PPM, menuBackgroundPosition, PPM*10,
                -this.getHeightAnimation() );
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(x-5*PPM, menuBackgroundPosition, PPM*10,
                -this.getHeightAnimation() );
        shapeRenderer.end();

        gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        super.draw(batch, this.getOpacity());
    }

    public float getOpacity(){
        if(opacity<1 && !closing) return opacity+=0.04;
        if(opacity>0 && closing) return opacity-=0.1;
        return opacity;
    }

    public void openMenu(Stage stage){
        stage.addActor(this);
    }

    public void closeMenu(){
        this.closing = true;
    }
}
