package com.mygdx.game.Screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.AppGame;
import com.mygdx.game.Utils.CustomOrthogonalTiledMapRenderer;
import com.mygdx.game.Utils.GlobalVar;
import com.mygdx.game.Utils.TiledObjectUtil;

public class AbstractGameScreenTiled extends AbstractGameScreen {

    //Tiled Map
    protected CustomOrthogonalTiledMapRenderer tmr;
    protected TiledMap map;

    public AbstractGameScreenTiled(final AppGame game){
        super(game);
        super.freezeButtonVisible = false;
        super.moveButtonVisible = false;
        super.scoreBoardVisible = false;
        super.menuButtonVisible = false;
        map = new TmxMapLoader().load("Maps/test_map.tmx");
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("Collision-Layer").getObjects());
        tmr = new CustomOrthogonalTiledMapRenderer(map, GlobalVar.getScaleWidth(), GlobalVar.getScaleHeight());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(!GlobalVar.DEBUG) tmr.render();
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