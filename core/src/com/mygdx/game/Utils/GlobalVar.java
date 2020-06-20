package com.mygdx.game.Utils;

public final class GlobalVar {

    public static float screenWidth, screenHeight;
    public static float PPM;                    //Pixel Per Meters calculated in AppGame
    public static float widthInPPM = 12;
    public static float heightInUHM = 20;
    public static float UHM;

    public static float boardHeight;
    public static int boardCreated;

    public static float safeAreaInsetTop;
    public static float safeAreaInsetBottom;

    public static boolean DEBUG = false;

    public static float getScaleWidth(){ return widthInPPM*PPM/360f; }
    public static float getScaleHeight(){ return heightInUHM*UHM/640f; }
}
