package com.mygdx.game.Factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpritesFactory {

    // Objects used
    private static Texture worldSheet;

    public static Animation<TextureRegion> getBallFrames(){
        int FRAME_COLS = 9;
        int FRAME_ROWS = 8;
        // Load the sprite sheet as a Texture
        worldSheet = new Texture(Gdx.files.internal("Sprites/globe-resized.png"));          //pref: globe-resized.png
        TextureRegion[][] tmp = TextureRegion.split(worldSheet,
                worldSheet.getWidth() / FRAME_COLS,
                worldSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        // Initialize the Animation with the frame interval and array of frames
        return new Animation<>(0.010f, walkFrames);
    }

    public static Animation<TextureRegion> getSpinnerFrames(){
        int FRAME_COLS = 8; //12;
        int FRAME_ROWS = 1; //4;
        // Load the sprite sheet as a Texture
        worldSheet = new Texture(Gdx.files.internal("Sprites/load.png"));          //pref: load.png
        TextureRegion[][] tmp = TextureRegion.split(worldSheet,
                worldSheet.getWidth() / FRAME_COLS,
                worldSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        // Initialize the Animation with the frame interval and array of frames
        return new Animation<>(0.05f, walkFrames);
    }
}
