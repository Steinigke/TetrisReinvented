package com.steinigkejulian.lonlyforest.scenes;

import android.content.Context;
import android.graphics.Color;

import com.steinigkejulian.lonlyforest.utile.Genaral;

public class SceneTwo extends Scene{

    public SceneTwo(Context context) {
        super(context,4);
    }

    @Override
    public void setUp() {

        BackgroundColor = Color.rgb(50,50,56);

        addRigid(1,0,-1,5,0,1200);

        addRigid(1,0,0,2010,930,1200);

        addEnteties("glitch", 0,666.6f,880);
        addEnteties("glitch", 0,666.6f,780);
        addEnteties("glitch",0,1333.2f,880);
        addEnteties("glitch",0,1333.2f,780);
        addEnteties("checkpoint",0,1000,650);

        addRigid(1,0,2000,screenX+160,700,1200);

        //Page 2

        addReset(1,0,screenX-1,1200,1300);

        addEnteties("checkpoint",1,80,520);

        addRigid(1,1,1800,screenX+160,830,1200);

        //Page 3 ???

        addRigid(1,2,0,790,930,1200);

        addRigid(1,2,780,980,830,1200);
        addRigid(1,2,1180,1380,830,1200);
        addReset(2,1170,1390,1200,1300);

        addEnteties("glitch", 2, 470,880);
        addEnteties("checkpoint", 2,80,650);
        addEnteties("glitch", 2,1690,880);

        addRigid(1,2,1370,2000,930,1200);
        addRigid(1,2,2000,screenX+160,830,1200);

        //Page last

        addRigid(1,3,300,500,425,1200);
        addRigid(1,3,880,1280,930,1200);
        addRigid(2,3,880,945,735,930);
        addRigid(3,3,840,1330,325,400);

        addRigid(3,3,1450,1650,465,540);
        addRigid(3,3,1750,1950,910,985);
        addRigid(3,3,1315,1515,1000,1075);

        addReset(3,0,screenX+1,1200,1300);

        addEnteties("glitch",3,250,475);
        addEnteties("glitch",3,840,450);
        addEnteties("glitch",3,1355,475);
        addEnteties("glitch",3,1720,850);
        addEnteties("checkpoint", 3,60,650);

        addDoor(3,3,1080,931);

        setUpPlayer(80,525);
        addCameraTooObjects();

    }
}
