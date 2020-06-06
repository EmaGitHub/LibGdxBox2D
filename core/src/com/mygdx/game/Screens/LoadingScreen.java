package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.AppGame;

public class LoadingScreen extends AbstractScreen {

    Timer.Task task = null;

    public LoadingScreen(final AppGame game){
        super(game);
    }

    @Override
    public void show(){
        super.show();
        System.out.println("Loading Screen");
        this.objectFactory.createLoadingSpinner(3*UHM);
        this.queueAssets();

        float delay = 1; // seconds
        if(this.task == null) this.task = new Timer.Task() {

            @Override
            public void run() {
                game.setScreen(new GameScreen(game));
                task.cancel();
            }
        };
        else System.out.println("Task not null");
        Timer.schedule(task, delay);
    }

    @Override
    public void render(float delta) {
        clear();
        super.render(delta);
        update(delta);
    }

    private void update(float delta){
        if(game.assetsManager.update()){
        }
    }

    private void queueAssets(){
        game.assetsManager.load("Sprites/globe-resized.png", Texture.class);
    }

    @Override
    public void hide(){
        super.hide();
    }
}