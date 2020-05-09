package GameEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.GlobalVar;

public class MenuPanel extends Table {

    private AppGame appGame;

    ShapeRenderer shapeRenderer;
    private float PPM = GlobalVar.PPM;
    private float height = 0;
    private float maxHeight = PPM*10;

    public MenuPanel(final AppGame game){
        super();
        appGame = game;
        shapeRenderer = new ShapeRenderer();
        this.setPosition(0, 7*PPM);

        super.setSkin( new Skin(Gdx.files.internal("Skins/skin/glassy-ui.json")));
        Label gameTitle = new Label("Project", super.getSkin());
        gameTitle.setFontScale(1.5f);
        add(gameTitle).padBottom(10).padTop(10).row();

        TextButton button = new TextButton("Options", super.getSkin());
        TextButton exitButton = new TextButton("Exit ", super.getSkin());
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
        if(height < maxHeight) height+=25;
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
        shapeRenderer.setColor(0, 0.5f, 1, 0.6f);  //0.7
        shapeRenderer.rect(-4*PPM, 8*PPM, PPM*8, -this.getHeightAnimation());
        shapeRenderer.end();

        gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        super.draw(batch, parentAlpha);
    }
}
