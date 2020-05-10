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
import com.mygdx.game.Screens.AbstractScreen;
import com.mygdx.game.Utils.GlobalVar;


public class MenuPanel extends Table {

    private AppGame appGame;

    ShapeRenderer shapeRenderer;
    Label gameTitle;
    Skin skin;
    private boolean closing = false;

    private float PPM = GlobalVar.PPM;
    private float height = 0;
    private float maxHeightInPPM = (GlobalVar.heightInPPM-6);
    private float maxHeight = (maxHeightInPPM*PPM)-GlobalVar.safeAreaInsetTop;

    private TextButton settingsButton;
    private TextButton exitButton;
    private TextButton debugButton;

    BitmapFont titleFont;
    BitmapFont textFont;

    public MenuPanel(final AppGame game){
        this.setWidth(10*PPM);
        this.setHeight(maxHeight);
        this.setPosition(-getWidth()/2, -(getHeight())/2);
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
        gameTitle.setSize(4*PPM, 2*PPM);
        gameTitle.setAlignment(Align.center);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Cagliostro-Regular.ttf"));

        parameter.size = (int)PPM;
        textFont = generator.generateFont(parameter);
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.WHITE;

        debugButton = new TextButton("",skin,"small");
        debugButton.setLabel(new Label("Check-Debug", textStyle));
        debugButton.getLabel().setAlignment(Align.center);
        if(GlobalVar.DEBUG) debugButton.setChecked(true);
        debugButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GlobalVar.DEBUG = !GlobalVar.DEBUG;
                return super.touchDown(event, x, y, pointer, button);
            }
        });

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
    }

    public float getHeightAnimation(){
        if(closing) {
            if(height<=0) {
                this.remove();
                ((AbstractScreen)this.appGame.getScreen()).closeMenuCallback();
            }
                return height-=PPM;
        }
        else if(height < maxHeight) return height+=PPM;
        return height;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        batch.end();

        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);                    // Circle shadow
        shapeRenderer.setColor(0.4f,0.6f,1f, 0.6f);  //0.7
        shapeRenderer.rect(-5*PPM, maxHeight/2, PPM*10, -this.getHeightAnimation());
        shapeRenderer.end();

        gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        super.draw(batch, parentAlpha);
    }

    public void openMenu(Stage stage){
        stage.addActor(this);
        this.add(gameTitle).top().padBottom(PPM).row();
        this.add(debugButton).padBottom(PPM/2).row();
        this.add(settingsButton).padBottom(PPM/2).row();
        this.add(exitButton).width(7*PPM).height(3*PPM).row();
    }

    public void closeMenu(){
        this.closing = true;
    }
}
