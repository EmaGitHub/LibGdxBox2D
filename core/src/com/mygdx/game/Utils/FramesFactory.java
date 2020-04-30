package com.mygdx.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FramesFactory {

    // Objects used
    Animation<TextureRegion> worldAnimation; // Must declare frame type (TextureRegion)
    private static Texture worldSheet;

    public static Animation<TextureRegion> createBallFrames(){

        int FRAME_COLS = 8;
        int FRAME_ROWS = 6;
        // Load the sprite sheet as a Texture
        worldSheet = new Texture(Gdx.files.internal("Sprites/worldSprite.gif"));
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
        return new Animation<TextureRegion>(0.010f, walkFrames);
    }
}
