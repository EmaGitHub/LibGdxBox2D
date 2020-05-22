package GameEntities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.GlobalVar;

import static com.mygdx.game.Utils.GlobalVar.PPM;
import static com.mygdx.game.Utils.GlobalVar.UHM;
import static com.mygdx.game.Utils.GlobalVar.screenHeight;
import static com.mygdx.game.Utils.GlobalVar.screenWidth;

public class Controllers {

    private Viewport viewport;
    private OrthographicCamera cam;
    float x, y;
    private Stage stage;

    protected boolean freezeButtonVisible = true;
    protected boolean moveButtonVisible = true;

    private MenuButton menuButton;
    private ScoreBoard scoreBoard;
    private MenuPanel menu;
    private FreezeButton freezeButton;
    private MoveButton moveButton;

    public Controllers(float relativeX, float relativeY, boolean freezeButtonVisible, boolean moveButtonVisible, AppGame game){
        this.x = relativeX;
        this.y = relativeY;
        this.freezeButtonVisible = freezeButtonVisible;
        this.moveButtonVisible = moveButtonVisible;
        cam = new OrthographicCamera();
        viewport = new StretchViewport(screenWidth,
                screenHeight + GlobalVar.safeAreaInsetBottom, cam);
        stage = new Stage(viewport);

        menu = new MenuPanel(x, y, game);
        menuButton = new MenuButton(x, y);
        freezeButton = new FreezeButton(x+4*PPM, y-GlobalVar.heightInUHM*UHM/2);
        moveButton = new MoveButton(-6*PPM, -GlobalVar.heightInUHM*UHM/2);
        scoreBoard = new ScoreBoard(0, GlobalVar.heightInUHM*UHM/2 - PPM - PPM/2 - GlobalVar.safeAreaInsetTop);

        stage.addActor(scoreBoard);
        stage.addActor(menuButton);
        if(freezeButtonVisible) stage.addActor(freezeButton);
        if(moveButtonVisible) stage.addActor(moveButton);
        cameraUpdate();
    }

    public Controllers(float relativeX, float relativeY, AppGame game){
        this(relativeX, relativeY, true, true, game);
    }

    public FreezeButton getFreezeButton(){
        return freezeButton;
    }

    public MoveButton getMoveButton(){
        return moveButton;
    }

    public MenuPanel getMenu(){
        return menu;
    }

    public MenuButton getMenuButton() {
        return menuButton;
    }

    public void updateValue(int value){
        this.scoreBoard.updateValue(value);
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    protected void cameraUpdate() {
        Vector3 position = cam.position;
        position.x = 0;                                          //player.getPosition().x * PPM;
        position.y = 0 - GlobalVar.safeAreaInsetBottom;         //player.getPosition().y * PPM;
        cam.position.set(position);
        cam.update();
    }

    public void draw(){

        stage.setDebugAll(GlobalVar.DEBUG);
        stage.draw();
    }
}
