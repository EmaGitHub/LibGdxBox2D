package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.AppGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SplashScreen extends AbstractScreen {

    private Image splashImg;
    Pixmap pixmapGreater;
    Pixmap pixmapOk;
    Texture texture;

    public SplashScreen(final AppGame game){
        super(game);

        pixmapGreater = new Pixmap(Gdx.files.internal("Images/sphere.png"));
        pixmapOk = new Pixmap(4*(int)PPM, 4*(int)PPM, pixmapGreater.getFormat());
        pixmapOk.drawPixmap(pixmapGreater,
                0, 0, pixmapGreater.getWidth(), pixmapGreater.getHeight(),
                0, 0, pixmapOk.getWidth(), pixmapOk.getHeight());
        texture = new Texture(pixmapOk);

        splashImg = new Image(texture);
        splashImg.setOrigin(splashImg.getWidth()/2, splashImg.getHeight()/2);
        splashImg.setPosition(-texture.getWidth()/2 + 2*PPM, -texture.getHeight()/2 + 4*UHM);
        splashImg.toFront();
    }

    @Override
    public void show(){                                             // Prima funzione chiamata
        super.show();
        stage.addActor(splashImg);
        splashImg.addAction(sequence(alpha(0.1f),scaleTo(0.1f, 0.1f),             //Actions.alpha
                parallel(fadeIn(2f, Interpolation.swing),
                        scaleTo(2f, 2f, 3f, Interpolation.pow3),
                        moveTo(-splashImg.getWidth()/2, -splashImg.getHeight()/2, 3f, Interpolation.swing))
                //,delay(1.5f), fadeOut(1f)
        ));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.batch.begin();
        game.font.setColor(Color.VIOLET);
        game.font.draw(game.batch, "Splashscreen", -5*PPM, -9*UHM);
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