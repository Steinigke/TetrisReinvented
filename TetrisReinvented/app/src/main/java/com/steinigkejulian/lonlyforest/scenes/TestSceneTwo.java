package com.steinigkejulian.lonlyforest.scenes;

import android.content.Context;
import android.graphics.Color;

public class TestSceneTwo extends Scene{
    public TestSceneTwo(Context context) {
        super(context, 1);
    }

    @Override
    public void setUp(){

        BackgroundColor = Color.RED;

        addRigid(1,0,0,screenX+1,800,1200);
        addDoor(10,0,1080,799);


        setUpPlayer(75, 100);
        addCameraTooObjects();

    }
}
