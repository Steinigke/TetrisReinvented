package com.steinigkejulian.lonlyforest.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.steinigkejulian.lonlyforest.R;
import com.steinigkejulian.lonlyforest.mechanics.BoostOrb;
import com.steinigkejulian.lonlyforest.mechanics.Player;
import com.steinigkejulian.lonlyforest.mechanics.RectColider;
import com.steinigkejulian.lonlyforest.listeners.InteractionManager;
import com.steinigkejulian.lonlyforest.scenes.*;
import com.steinigkejulian.lonlyforest.scenes.TestScene;
import com.steinigkejulian.lonlyforest.utile.Genaral;
import com.steinigkejulian.lonlyforest.utile.Layout;
import com.steinigkejulian.lonlyforest.utile.Vector2;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements Runnable {

    Context context = GameActivity.this;

    //Fullscreen  Variables
    private final int UI_HIDE = View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

    private final int hideDelay = 1000;
    private View decor;
    Handler handler = new Handler();
    Runnable hide = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    //GameLoop Variables
    private Thread gameLoop;
    public static float frameRateCap = 60;
    private final double MAX_FRAME_RATE = 1 / frameRateCap;
    private static boolean loopIsActive;
    private boolean gameIsRunning;

    //Game Variables
    private Layout gameLayout;
    private Scene currentScene;
    private static int nextScene;
    private Player player;

    //Input Controller Variables
    private static int left;
    private static int right;
    private static Vector2 lastInputCords;

    public static Vector2 getLastInputCords() {
        return lastInputCords;
    }
    public static int getDirection(){return right - left; }

    //Controlls Variables
    private final float SIDE_BUTTON_PERCENTAGE = 0.17f;
    private Button leftClick;
    private Button middleClick;
    private Button rightClick;

    private ImageView buttonView;
    private Button pauseGame;

    public static Intent makeIntent(Context context){
        return new Intent(context, GameActivity.class);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decor = getWindow().getDecorView();

        gameLayout = new Layout(GameActivity.this);
        setContentView(gameLayout);
        gameLayout.setVisibility(View.VISIBLE);

        getWindowManager().getDefaultDisplay().getMetrics(Genaral.getDisplayMetrics());


        // Fullscreen
        handler.postDelayed(hide,hideDelay);
        decor.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener(){

            @Override
            public void onSystemUiVisibilityChange(int i) {

                int StatusBar =  View.SYSTEM_UI_FLAG_FULLSCREEN & i;

                if(StatusBar == 0) {
                    handler.removeCallbacks(hide);
                    handler.postDelayed(hide, hideDelay);
                }
            }
        });

        gameIsRunning = true;

        //Controlls
        leftClick = new Button(context);
        leftClick.setVisibility(View.VISIBLE);
        leftClick.setBackgroundColor(Color.TRANSPARENT);
        leftClick.setOnTouchListener(listener);
        leftClick.setX(0f);
        leftClick.setY(0f);
        leftClick.setWidth((int)(Genaral.getScreenWidth() * SIDE_BUTTON_PERCENTAGE));
        leftClick.setHeight(Genaral.getScreenHeight());
        gameLayout.addView(leftClick);

        middleClick = new Button(context);
        middleClick.setVisibility(View.VISIBLE);
        middleClick.setBackgroundColor(Color.TRANSPARENT);
        middleClick.setOnTouchListener(listener);
        middleClick.setX((int)(Genaral.getScreenWidth() * SIDE_BUTTON_PERCENTAGE));
        middleClick.setY(0f);
        middleClick.setWidth((int)(Genaral.getScreenWidth() * ( 1 - 2 * SIDE_BUTTON_PERCENTAGE)));
        middleClick.setHeight(Genaral.getScreenHeight());
        gameLayout.addView(middleClick);

        rightClick = new Button(context);
        rightClick.setVisibility(View.VISIBLE);
        rightClick.setBackgroundColor(Color.TRANSPARENT);
        rightClick.setOnTouchListener(listener);
        rightClick.setX((int)(Genaral.getScreenWidth() - (Genaral.getScreenWidth() * SIDE_BUTTON_PERCENTAGE) ));
        rightClick.setY(0f);
        rightClick.setHeight(Genaral.getScreenHeight());
        rightClick.setWidth((int)(Genaral.getScreenWidth() * SIDE_BUTTON_PERCENTAGE));
        gameLayout.addView(rightClick);

        pauseGame = new Button(GameActivity.this);
        pauseGame.setVisibility(View.VISIBLE);
        pauseGame.setBackgroundColor(Color.TRANSPARENT);
        pauseGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseGame(view);
            }
        });
        pauseGame.setY(Genaral.getScreenHeight()*0.07f);
        pauseGame.setX(Genaral.getScreenWidth()*0.93f);
        pauseGame.setWidth((int)(Genaral.getScreenHeight()*0.07f));
        pauseGame.setHeight((int)(Genaral.getScreenHeight()*0.07f));

        buttonView = new ImageView(context);
        buttonView.setImageResource(R.drawable.settings);
        buttonView.setAdjustViewBounds(true);
        buttonView.setMaxHeight((int)(Genaral.getScreenHeight()*0.07f));
        buttonView.setY(Genaral.getScreenHeight()*0.07f);
        buttonView.setX(Genaral.getScreenWidth()*0.93f);
        buttonView.setVisibility(View.VISIBLE);

        gameLayout.addView(buttonView);
        gameLayout.addView(pauseGame);

        lastInputCords = new Vector2();
        gameLoop = new Thread(GameActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Genaral.resetGameFlags();
        changeScene(1);

        gameIsRunning = true;
        loopIsActive = false;
        handler.postDelayed(() -> gameLoop.start(), 1500);




    }



    View.OnTouchListener listener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if(view == pauseGame){
                pauseGame(view);
                return true;
            }

            lastInputCords.setVector(event.getX(), event.getY());
            lastInputCords.addVector(new Vector2(view.getX(),view.getY()));

            if(view == middleClick && event.getAction() == MotionEvent.ACTION_UP){
                InteractionManager.get().interaction(player,InteractionManager.INTERACTION_RELEASED);
            }

            if((Genaral.getGameFlags() & Genaral.IGNORE_INPUTS_TRUE) > 0){
                left = 0;
                right = 0;
                return false;

            }

            switch(event.getAction()){

                case MotionEvent.ACTION_DOWN:
                    if(view == leftClick){
                        left = 1;

                    }

                    if(view == middleClick){
                        InteractionManager.get().interaction(player,InteractionManager.INTERACTION_PRESSED);

                    }
                    if(view == rightClick){
                        right = 1;
                    }

                    return  false;

                case MotionEvent.ACTION_UP:
                    if(view == leftClick){
                        left = 0;

                    }

                    if(view == rightClick){
                        right = 0;

                    }
                    return  false;
            }

            return  false;
        }
    };


    private void hide(){
        decor.setSystemUiVisibility(UI_HIDE);
    }

    public static void changeScene(int scene){

        nextScene = scene;
        loopIsActive = false;
    }

    @Override
    public void run(){

        while(gameIsRunning) {

            if(Genaral.showButtons){
                middleClick.setBackgroundColor(Color.argb(0.2f, 1f,1f,0f));

            }else{
                middleClick.setBackgroundColor(Color.TRANSPARENT);
            }
            loopIsActive = true;

            //Load World
            switch(nextScene){
                case -1: currentScene = new TestSceneTwo(context);break;
                case 0: currentScene = new TestScene(context); break;
                case 1: currentScene = new SceneOne(context); break;
                case 2: currentScene = new SceneTwo(context); break;
                case 3: currentScene = new ThridScene(context); break;
                case 10:gameIsRunning = false;
                        loopIsActive = false;
                        runOnUiThread(()->setContentView(R.layout.winnig_screen));
                        continue;
                default: currentScene = null;

            }
            //setupWorld
            currentScene.setUp();
            currentScene.setUpLayout(gameLayout);
            player = currentScene.getPlayer();

            //Add Graphics to layout
            this.runOnUiThread(()->{
                gameLayout.clearLayout();
                currentScene.setUpLayout(gameLayout);
                gameLayout.updateLayout();
                buttonView.bringToFront();
            });

            //set Up GameLoop
            double startTime;
            double endTime = System.nanoTime() / 1000000000.0;
            double deltaTime;
            double proccesingTime = 0;

            while (loopIsActive) {

                startTime = System.nanoTime() / 1000000000.0;
                deltaTime = startTime - endTime;
                proccesingTime += deltaTime;
                endTime = startTime;

            while(proccesingTime >= 0){
                    proccesingTime -= MAX_FRAME_RATE;

                    currentScene.update();

                }//End of GameLoop

            }
            System.out.println("GameLoopEnded");


        }

        System.out.println("GameEnded");

    }

    public void endGame(){
        setContentView(R.layout.winnig_screen);
    }

    public void backToMenu(View view){

        startActivity(MenuActivity.makeIntent(GameActivity.this));
        finish();
    }

    public void restartGame(View view){
        startActivity(GameActivity.makeIntent(GameActivity.this));
        finish();
    }

    public void pauseGame(View view){
        Genaral.setGameFlag(Genaral.SCENE_PAUSED_TRUE);
        Genaral.setGameFlag(Genaral.IGNORE_INPUTS_TRUE);
        setContentView(R.layout.pause_screen);
    }

    public void continueGame(View view ){
        Genaral.setGameFlag(Genaral.SCENE_PAUSED_FALSE);
        Genaral.setGameFlag(Genaral.IGNORE_INPUTS_FALSE);
        setContentView(gameLayout);

    }


}







