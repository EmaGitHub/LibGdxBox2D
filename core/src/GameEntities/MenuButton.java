package GameEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.Utils.GlobalVar;

public class MenuButton extends Button {

    private ShapeRenderer shapeRenderer;
    private float PPM = GlobalVar.PPM;
    private float UHM = GlobalVar.UHM;
    private float diameter = PPM;
    private float radius = diameter/2;

    private float circleX;
    private float circleY;
    private float linesInitialX;
    private float linesFinalX;
    private float baseY;

    private boolean menuSelected = false;

    public MenuButton(float x, float y) {
        setSize(diameter*2, diameter*2);
        shapeRenderer = new ShapeRenderer();
        this.setTouchable(Touchable.enabled);
        setX(x-PPM);
        setY(y + GlobalVar.heightInUHM*UHM/2 - PPM*2 - GlobalVar.safeAreaInsetTop);

        Vector2 coords = new Vector2(x-PPM/2, y+GlobalVar.heightInUHM*UHM/2 - PPM - PPM/2 - GlobalVar.safeAreaInsetTop);
        this.circleX =  coords.x + radius;
        this.circleY = coords.y + radius;
        this.linesInitialX = coords.x + radius/3;
        this.linesFinalX = coords.x + PPM*5/6;
        this.baseY = GlobalVar.heightInUHM*UHM/2 - PPM - PPM/2 - GlobalVar.safeAreaInsetTop;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.toFront();
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);                    // Circle shadow
        shapeRenderer.setColor(0, 0, 0, 0.6f);
        shapeRenderer.circle(circleX, circleY, diameter);
        shapeRenderer.end();

        gl.glDisable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);                      // Circle line
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.circle(circleX, circleY, radius);
        shapeRenderer.circle(circleX, circleY, radius+1);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);                            // Menu bars
        shapeRenderer.rectLine(linesInitialX, baseY + (diameter / 5) + radius,
                linesFinalX, baseY + (diameter / 5) + radius, radius / 8);
        shapeRenderer.rectLine(linesInitialX, baseY + radius,
                linesFinalX, baseY + radius, radius / 8);
        shapeRenderer.rectLine(linesInitialX, baseY + radius - (diameter / 5),
                linesFinalX, baseY + radius - (diameter / 5), radius / 8);


        shapeRenderer.end();
        batch.begin();
    }

    public void switchState(){
        this.menuSelected = !this.menuSelected;
    }
}