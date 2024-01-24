package com.steinigkejulian.lonlyforest.utile;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Layout extends RelativeLayout {

    ArrayList<View>[] views = new ArrayList[4];


    public Layout(Context context) {
        super(context);
        this.setBackgroundColor(Color.BLACK);
        for(int i = 0; i < views.length; i++){
            views[i] = new ArrayList<>();
        }

    }

    public void addGameView(ArrayList<View> view, int layer){
        try {
            views[layer].addAll(view);
        }catch(IndexOutOfBoundsException ignored){}

    }

    public void updateLayout(){

        for(int i = 0; i < views.length; i++){
            views[i].forEach(this::addView);
        }
    }

    public void clearLayout(){

        for(int i = 0; i < views.length; i++){
            views[i].forEach(this::removeView);
            views[i].clear();
        }
    }
}










