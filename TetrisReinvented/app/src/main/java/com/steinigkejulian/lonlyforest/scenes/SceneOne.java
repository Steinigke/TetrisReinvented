package com.steinigkejulian.lonlyforest.scenes;

import android.content.Context;
import android.graphics.Color;

import com.steinigkejulian.lonlyforest.utile.Genaral;

public class SceneOne extends Scene{

    public SceneOne(Context context){
        super(context, 5);

    }

    @Override
    public void setUp() {

        BackgroundColor = Color.rgb(50,50,56);

        //FirstPage
        addRigid(1,0,-1,5,0,1200);

        addRigid(1,0,0,215,650,1200);
        addRigid(1,0,140, 1030,900,1200);
        addRigid(1,0,1020,1370,815,1200);
        addRigid(1,0,1365,1600,680,1200);
        addRigid(1,0,1595,1885,480,1200);


        addRigid(1,0,1880,screenX + 260,700,1200);

        //SecondPage

        addRigid(1,1,250,2150,1000,1200);
        addRigid(1,1,830,1430,400,1200);

        addRigid(1,1, 1712.5f, screenX+165,-300,100);
        addRigid(1,1,2000,screenX+165,700,1200);

        //ThirdPage

        addRigid(3,2,535,665,0,725);
        addRigid(0,2,535,665,-725,0);
        addRigid(3,2,1135,1265,275,1000);
        addRigid(3,2,1735,1865,0,725);
        addRigid(0,2,1735,1865,-725,0);

        addRigid(2,2,665,900,550,600);
        addRigid(2,2,1500,1735,550,600);

        addRigid(1,2,160, screenX+160,1000,1200);

        //FourthPage

        addRigid(1,3,150,2100,1000,1200);

        addRigid(1,3,25,400,150,350);

        addRigid(4,3,250,400, 900,950);
        addRigid(4,3,500,650,800,850);
        addRigid(4,3,750,900,700,750);
        addRigid(4,3,1250,1400,500,550);
        addRigid(4,3,1750,1900,300,350);

        addRigid(1,3,2000,screenX+160,300, 1200);

        //LastPage

        addRigid(1,4,2155,2200,0,1200);
        addRigid(1,4,160,2158,1000,1200);
        addRigid(1,4,1565,1650,720,1200);
        addRigid(1,4,1565,1950,600,725);

        addRigid(3,4,484.25f,536.25f,0,700);
        addRigid(3,4,1188.75f,1238.75f,0,700);
        addRigid(4,4,837.50f,887.50f,0,400);
        addRigid(4,4,837.50f,887.50f,625,1000);

        addRigid(1,4,1565,1950,300,350);
        addRigid(1,4,1900,1950,325,625);
        addRigid(3,4,1238.75f,1370,465,525);

        addDoor(2,4,1800,1000);

        setUpPlayer(80,525);
        addCameraTooObjects();


    }
}
