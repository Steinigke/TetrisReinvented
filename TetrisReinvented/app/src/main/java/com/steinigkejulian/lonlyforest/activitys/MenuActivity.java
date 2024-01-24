package com.steinigkejulian.lonlyforest.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.steinigkejulian.lonlyforest.R;

public class MenuActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
            setContentView(R.layout.menu);
//        }catch ()

        decor = getWindow().getDecorView();
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
    }

    public void startGame(View view){
        startActivity(GameActivity.makeIntent(this));
        finish();

    }

    public void showControls(View view){
        setContentView(R.layout.controls
        );

    }

    public void exitControls(View view){
        setContentView(R.layout.menu);
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,MenuActivity.class);
    }

    private void hide(){
        decor.setSystemUiVisibility(UI_HIDE);
    }

}