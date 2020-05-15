package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.AppGame;

public class SplashScreen extends AbstractScreen {

    private Image splashImg;
    Pixmap pixmapGreater;
    Pixmap pixmapOk;

    public SplashScreen(AppGame game){
        super(game);
        menuButtonVisible = false;
        freezeButtonVisible = false;
        moveButtonVisible = false;
        scoreBoardVisible = false;
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();

        pixmapGreater = new Pixmap(Gdx.files.internal("Images/sphere.png"));
        pixmapOk = new Pixmap(200, 200, pixmapGreater.getFormat());
        pixmapOk.drawPixmap(pixmapGreater,
                0, 0, pixmapGreater.getWidth(), pixmapGreater.getHeight(),
                0, 0, pixmapOk.getWidth(), pixmapOk.getHeight()
        );
        Texture texture = new Texture(pixmapOk);

        splashImg = new Image(texture);
        splashImg.setPosition(-texture.getWidth()/2, -texture.getHeight()/2);
        splashImg.toFront();
        stage.addActor(splashImg);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.begin();
        game.font.setColor(Color.VIOLET);
        game.font.draw(game.batch, "Splashcreen", -1*PPM, -9*UHM);
        game.batch.end();
    }

    @Override
    public void touched(){
        this.game.setScreen(game.homeScreen);
    }

    @Override
    public void hide() {
        super.hide();
        pixmapGreater.dispose();
        pixmapOk.dispose();
    }
}