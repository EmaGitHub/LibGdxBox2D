package GameEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.Utils.GlobalVar;

public class MoveButton extends Button {

    private ShapeRenderer shapeRenderer;
    private float PPM = GlobalVar.PPM;
    private float diameter = PPM;
    private float radius = diameter/2;

    private boolean gameInPause = false;

    public MoveButton() {
        setSize(diameter*2, diameter*2);
        shapeRenderer = new ShapeRenderer();
        this.setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.toFront();
        Vector2 coords = new Vector2(getX(),getY());
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);                            //shadow
        shapeRenderer.setColor(0, 0, 0, 0.6f);
        shapeRenderer.circle(coords.x+diameter, coords.y+diameter, diameter);
        shapeRenderer.end();

        gl.glDisable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.circle(coords.x+diameter, coords.y+diameter, radius);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(coords.x+diameter, coords.y+diameter, radius/3);
        shapeRenderer.end();
        batch.begin();
    }

    public void switchState(){
        this.gameInPause = !this.gameInPause;
    }
}