package GameEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Utils.GlobalVar;

public class ScoreBoard extends Actor {

    private Label scoreLabel;
    private Label scoreValue;
    private float PPM = GlobalVar.PPM;
    private BitmapFont font;

    private ShapeRenderer shapeRenderer;

    private int score = 0;

    public ScoreBoard() {

        shapeRenderer = new ShapeRenderer();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/HennyPenny-Regular.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)PPM/2;
        font = generator.generateFont(parameter);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = font;
        titleStyle.fontColor = Color.WHITE;

        scoreLabel = new Label("Score ", titleStyle);
        scoreLabel.setSize(2*PPM, PPM);
        scoreLabel.setPosition(-4*PPM, 9*PPM + PPM/4);
        scoreLabel.setAlignment(Align.left);

        scoreValue = new Label("0", titleStyle);
        scoreValue.setSize(3*PPM, PPM);
        scoreValue.setPosition(2*PPM, 9*PPM + PPM/4);
        scoreValue.setAlignment(Align.center);
        this.scoreValue.setText(score);
    }

    public void draw(Batch batch, float parentAlpha) {
        this.toFront();

        batch.end();

        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(PPM + 2*PPM/3,GlobalVar.heightInPPM*PPM - PPM - PPM/2,PPM*2, PPM);
        shapeRenderer.rect(8*PPM,GlobalVar.heightInPPM*PPM - PPM - PPM/2,PPM*3, PPM);
        shapeRenderer.end();
        gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        this.scoreLabel.draw(batch, parentAlpha);
        this.scoreValue.draw(batch, parentAlpha);
    }

    public void updateValue(int value){
        this.score = value;
        this.scoreValue.setText(value);
    }
}
