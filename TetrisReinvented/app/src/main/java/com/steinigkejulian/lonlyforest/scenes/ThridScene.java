package com.steinigkejulian.lonlyforest.scenes;

import android.content.Context;
import android.graphics.Color;

import com.steinigkejulian.lonlyforest.utile.Genaral;

public class ThridScene extends Scene{


    public ThridScene(Context context) {
        super(context, 5);
    }

    @Override
    public void setUp() {

        BackgroundColor = Color.rgb(50,50,56);


        addRigid(1,0,-1,5,0,1200);

        addRigid(1,0,0,890,1000,1200);
        addRigid(1,0,880,1280,440,1200);

        addEnteties("orb",0,650,720);

        addRigid(1,0,1270,screenX+230,1000,1200);

        //page 2

        addRigid(1,1,1000,1500,500,1200);

        addRigid(4,1,1712.5f,1787.5f,0,550);

        addReset(1,1,screenX,1200,1300);

        addEnteties("orb",1,625,750);
        addEnteties("orb",1,1750,750);
        addEnteties("checkpoint",1,100,820);

        addRigid(1,1,2000,screenX+230, 600,1200);

        //Page 3

        addRigid(1,2,880,1280,400,1200);
        addReset(2,2,screenX,1200,1300);

        addEnteties("orb", 2,555,500);
        addEnteties("orb", 2,1605,500);
        addEnteties("glitch",2,930,400);
        addEnteties("glitch",2,1030,400);
        addEnteties("glitch",2,1130,400);
        addEnteties("glitch",2,1230,400);

        addEnteties("checkpoint",2,100,420);

        addRigid(1,2,1930,screenX + 230, 600,1200);


        //page 4

        addRigid(3,3,617.5f,692.5f,0,500);
        addRigid(3,3,617.5f,692.5f,850,1200);

        addRigid(1,3,930,1230,500,1200);

        addRigid(3,3,1467.5f,1532.5f,0,500);
        addRigid(2,3,1730,1930,790,865);


        addReset(3,0,screenX,1200,1300);

        addEnteties("orb", 3,655,675);
        addEnteties("orb", 3,1480,775);
        addEnteties("checkpoint",3,100,420);


        addRigid(1,3,1930,screenX + 230, 450,1200);


        //last page
        addRigid(3,4,1042.5f,1117.5f,0,515);
        addRigid(4,4,930,1230,900,975);

        addEnteties("orb",4,450.5f,250);
        addEnteties("orb",4,771f,250);

        addEnteties("orb",4,791f,737.5f);
        addEnteties("orb",4,1368f,737.5f);

        addEnteties("orb",4,1388f,250);
        addEnteties("orb",4,1708.5f,250);

        addReset(4,0,screenX,1200,1300);

        addDoor(10,4,2027.5f,901);
        addRigid(1,4,1825,2158,900,1200);
        addRigid(3,4,1825,1900,490,900);
        addRigid(1,4,2155,2180,0,1200);


        setUpPlayer(80,525);
        addCameraTooObjects();

    }
}
