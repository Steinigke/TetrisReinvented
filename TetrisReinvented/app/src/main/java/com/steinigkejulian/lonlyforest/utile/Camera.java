package com.steinigkejulian.lonlyforest.utile;

import com.steinigkejulian.lonlyforest.mechanics.Player;
import static java.lang.Math.*;

public class Camera {

    private Player player;
    private int maxTop;
    private int maxBottom;
    private int maxLeft;
    private int maxRight;


    private Vector2 cords;
    private Vector2 velocity;
    private final float excel = 2.3f;
    private final float maxVel = 65;
    private Vector2 aimCords;

    public Camera(Player player, int top, int bottom, int left, int right){

        cords = new Vector2();
        velocity = new Vector2();
        aimCords = new Vector2();

        this.player = player;
        maxTop = top;
        maxBottom = bottom;
        maxLeft = left;
        maxRight = right;

    }

    public void update(){

        int screenWidth = Genaral.getScreenWidth();
        int screenHeight = Genaral.getScreenHeight();

        float pX = player.getScreenX();
        float pY = player.getScreenY();


        if(velocity.absValue() > 0){
            Genaral.setGameFlag(Genaral.IGNORE_INPUTS_TRUE);
            player.resetMovement();
        }else{
            Genaral.setGameFlag(Genaral.IGNORE_INPUTS_FALSE);
        }

        if(!(pX < screenWidth) && !(velocity.getNumber1() > 0)){
            pX =+ screenWidth;
        }else if(!(0 < pX) && !(velocity.getNumber1() < 0)){
            pX = -screenWidth;
        }else {
            pX = 0;
        }


        if(!(pY< screenHeight)){
            pY = +screenHeight;
        }else if(!(0 < pY)){
            pY = -screenHeight;
        }else{
            pY = 0;
        }


        pX += aimCords.getNumber1();
        pY += aimCords.getNumber2();

        pX = min(pX, (float) (maxRight -Genaral.getScreenWidth()));
        pX = max(pX, (float) maxLeft);

        pY = min(pY, (float) (maxBottom -Genaral.getScreenHeight()));
        pY = max(pY, (float) maxTop);


        aimCords.setVector(pX, pY);

        //Camera Movement
        if(aimCords.equalVector(cords)){
            velocity.setVector(0,0);
            return;
        }

        Vector2 diff = new Vector2(aimCords);
        diff.subVector(cords);

        float distance = diff.absValue();

        diff.divVector(distance);
        diff.mulVector(excel);
        diff.addVector(velocity);

        if(distance < diff.absValue()){

            diff.divVector(diff.absValue());
            diff.mulVector(distance);
            cords.addVector(diff);
            velocity.setVector(0,0);
            return;
        }

        if(diff.absValue() <= maxVel) velocity = diff;
        cords.addVector(velocity);

    }

    public float getXOffset(){
        return - cords.getNumber1();
    }

    public float getYOffset(){
        return - cords.getNumber2();
    }



}













































