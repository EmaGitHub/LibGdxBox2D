package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.AppGame;
import com.mygdx.game.RealObjects.LoadingSpinner;

public class LoadingScreen extends AbstractScreen {


    public LoadingScreen(final AppGame game){
        super(game);
    }

    @Override
    public void show(){
        super.show();
        System.out.println("Loading Screen");
        LoadingSpinner spinner = this.objectFactory.createLoadingSpinner(2*UHM);
        this.queueAssets();
    }

    @Override
    public void touched(){
        //System.out.println("Touched "+touchPoint.x+", "+touchPoint.y);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
    }

    private void update(float delta){
        if(game.assetsManager.update()){
            game.setScreen(new GameScreen(game));
        }
    }

    private void queueAssets(){
        for(int i = 0; i < 10000; i++) {
            game.assetsManager.load("Sprites/globe-resized.png", Texture.class);
        }
    }

    @Override
    public void hide(){
        super.hide();
    }
}