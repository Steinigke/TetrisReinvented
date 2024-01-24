package com.steinigkejulian.lonlyforest.utile;

import android.util.DisplayMetrics;

public abstract class Genaral {

    //Screen Dimesions
    private static DisplayMetrics displayMetrics = new DisplayMetrics();

    private static int screenWidth;
    private static int screenHeight;

    public static DisplayMetrics getDisplayMetrics() {
        return displayMetrics;
    }

    public static int getScreenWidth() {
        screenWidth = displayMetrics.widthPixels;
        return screenWidth;
    }

    public static int getScreenHeight() {
        screenHeight = displayMetrics.heightPixels;
        return screenHeight;
    }

    //Genarl Variables

    public static final float GRAVITY = 1.1f;
    public static final float SLOW_MOTION_DIV = 2;

    //Game Flags
    public static final int IGNORE_INPUTS_TRUE = 1;
    public static final int IGNORE_INPUTS_FALSE = ~IGNORE_INPUTS_TRUE;
    public static final int SLOW_MOTION_TRUE = 2;
    public static final int SLOW_MOTION_FALSE = ~SLOW_MOTION_TRUE;
    public static final int SCENE_PAUSED_TRUE = 4;
    public static final int SCENE_PAUSED_FALSE = ~SCENE_PAUSED_TRUE;
    private static int gameFlags;

    public static void setGameFlag(int flag){
        switch (flag){

            case IGNORE_INPUTS_TRUE:
            case SLOW_MOTION_TRUE:
            case SCENE_PAUSED_TRUE:
                gameFlags |= flag;
                break;
            case IGNORE_INPUTS_FALSE:
            case SLOW_MOTION_FALSE:
            case SCENE_PAUSED_FALSE:
                gameFlags &= flag;
                break;
            default: System.out.println("Unknown Flag");
        }
    }

    public static int getGameFlags() {
        return gameFlags;
    }

    public static void resetGameFlags(){
        gameFlags = 0;
    }

    //debugging
    public static boolean showHitboxes = false;
    public static boolean showButtons = false;

}





























