package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.CustomOrthogonalTiledMapRenderer;
import com.mygdx.game.Utils.GlobalVar;
import com.mygdx.game.Utils.TiledObjectUtil;

import GameEntities.FreezeButton;
import GameEntities.MenuButton;
import GameEntities.MenuPanel;
import GameEntities.MoveButton;
import GameEntities.ScoreBoard;

public class AbstractGameScreenTiled extends AbstractGameScreen {

    //Tiled Map
    protected CustomOrthogonalTiledMapRenderer tmr;
    protected TiledMap map;

    public AbstractGameScreenTiled(final AppGame game){
        super(game);
        super.freezeButtonVisible = false;
        super.moveButtonVisible = false;
        super.scoreBoardVisible = true;
        super.menuButtonVisible = true;
        map = new TmxMapLoader().load("Maps/test_map.tmx");
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("Collision-Layer").getObjects());
        tmr = new CustomOrthogonalTiledMapRenderer(map, GlobalVar.getScaleWidth(), GlobalVar.getScaleHeight());
    }

    @Override
    public void show(){                                                                     // Prima funzione chiamata
        if(scoreBoardVisible) {
            scoreBoard = new ScoreBoard();
            this.stage.addActor(scoreBoard);
            scoreBoard.setPosition(0 , 0);
        }
        if(menuButtonVisible) {
            menu = new MenuPanel(game);
            menuButton = new MenuButton();
            menuButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!PAUSED) {
                        pauseGame();
                        stage.addActor(menu);
                        menu.openMenu(stage);
                        return false;
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(PAUSED) {
                        menu.closeMenu();
                    }
                }
            });
            this.stage.addActor(menuButton);
        }
        if(freezeButtonVisible) {
            freezeButton = new FreezeButton();
            freezeButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!FREEZED && !PAUSED) {
                        freeze();
                        return false;
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(FREEZED && !PAUSED) unfreeze();
                }
            });
            this.stage.addActor(freezeButton);
        }
        if(moveButtonVisible) {
            moveButton = new MoveButton();
            moveButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!FREEZED && !PAUSED) {
                        freeze();
                        return false;
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(FREEZED && !PAUSED) unfreeze();
                }
            });
            this.stage.addActor(moveButton);
        }
        Gdx.input.setCatchKey(Input.Keys.BACK, true);               //evita la chiusura con bottone back
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        tmr.render();
    }

    @Override
    public void update() {
        super.update();
        tmr.setView(camera);
        game.batch.setProjectionMatrix(camera.projection);
    }

    @Override
    protected void cameraUpdate() {
        Vector3 position = camera.position;
        position.x =  GlobalVar.widthInPPM*PPM/2;                                         //player.getPosition().x * PPM;
        position.y = GlobalVar.heightInUHM*UHM/2;// - GlobalVar.safeAreaInsetBottom;         //player.getPosition().y * PPM;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void hide(){
//        tmr.dispose();
//        map.dispose();
        super.hide();
    }
}