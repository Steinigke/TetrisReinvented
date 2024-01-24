package com.steinigkejulian.lonlyforest.scenes;

import android.content.Context;
import android.graphics.Color;

public class TestScene extends Scene{

    public TestScene(Context context) {
        super(context, 2);
    }

    public void setUp(){

        BackgroundColor = Color.rgb(50,50,56);

        addRigid(1,0,-1,160,725,1200);

        addRigid(1,0,150,2050,950,1200);
        addRigid(2,0,600,800,325,400);
        addRigid(3,0,1000,1200,325,400);
        addRigid(4,0,1400,1600,325,400);


        addEnteties("orb",0,1080,740);

        addRigid(1,0,2000,screenX+160,725,1200);

        addRigid(1,1,280,2050,950,1200);

        addRigid(1,1,2000,screenX+160,525,1200);

        addReset(1,0,screenX,1200,1300);
        addEnteties("glitch",1,1400,900);
        addEnteties("checkpoint",1,80,345);
        addDoor(-1,1,760,949);

        setUpPlayer(75, 100);
        addCameraTooObjects();
    }

}
