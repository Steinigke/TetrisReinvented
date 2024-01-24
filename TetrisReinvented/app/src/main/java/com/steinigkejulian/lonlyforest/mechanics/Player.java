package com.steinigkejulian.lonlyforest.mechanics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import static  java.lang.Math.*;

import com.steinigkejulian.lonlyforest.R;
import com.steinigkejulian.lonlyforest.activitys.GameActivity;
import com.steinigkejulian.lonlyforest.scenes.Scene;
import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Genaral;
import com.steinigkejulian.lonlyforest.utile.Vector2;
import com.steinigkejulian.lonlyforest.listeners.InteractionListener;
import com.steinigkejulian.lonlyforest.listeners.InteractionManager;


public class Player extends android.support.v7.widget.AppCompatImageView implements InteractionListener {

    private int hitboxWidth = (int) (50    * Scene.getDimensions());
    private int hitboxHeight = (int) (200   * Scene.getDimensions());
    private int width = (int) (50          * Scene.getDimensions()) ;
    private int height = (int) (200         * Scene.getDimensions());
    private float x;
    private float y;

    ArrayList<RectColider> rigid;

    //Movement Variabels
    private final float maxXVelocity = 100       * Scene.getDimensions();
    private final float maxYVelocity = 100       * Scene.getDimensions(); //redundent???##
    public float MAX_PLAYER_SPEED = 18.6f          * Scene.getDimensions();
    private Vector2 aimVelocity = new Vector2();
    private float floorSlipperiness = 1;
    private final float MAX_X_EXEL = 1.4f       * Scene.getDimensions();
    private final float MAX_Y_EXEL = 100        * Scene.getDimensions();
    private float gravity;
    private Vector2 velocity = new Vector2();

    //Jumping
    private int maxJumps = 2;
    private float jumpMulti = 0.165f              * Scene.getDimensions();
    private final float JUMP_HEIGHT = -7.6f       * Scene.getDimensions();
    private int jumps = 0;
    private float jumpHeight = JUMP_HEIGHT;
    private boolean jumping = false;

    //Boost
    private float boostMax = 50f               * Scene.getDimensions();
    double riseMulti = 0.9f       * Scene.getDimensions();
    double rise = 10000    * Scene.getDimensions();
    float minimum = 20f         * Scene.getDimensions();
    int boostTimer = 0;
    int boostTime = 18;
    int boostDecelerationTime = 10;
    Vector2 boostDeceleration = new Vector2();

    private RectColider rectColider;

    private Camera camera;
    private Checkpoint currentCheckpoint;

    public void setCheckpoint(Checkpoint checkpoint){

        currentCheckpoint.setImageResource(R.drawable.checkpointinactive);
        this.currentCheckpoint = checkpoint;

    }
    public void setCamera(Camera camera){
        this.camera = camera;
    }
    public void addSolidBlock(RectColider rectColider){
        rigid.add(rectColider);
    }

    public RectColider getRectColider() {
        return rectColider;
    }

    public Player(Context context) {
        super(context);
        init(context, 100, 100);

    }

    public Player(Context context, float xStart, float yStart) {
        super(context);

        init(context,xStart,yStart);

    }

    private void init(Context context, float x, float y){

        this.setImageResource(R.drawable.playeridel);

        x = Scene.getXCanvas(x);
        y = Scene.getYCanvas(y);


        rectColider = new RectColider(context, Color.GREEN, x - hitboxWidth/2, y - hitboxHeight /2, x +hitboxWidth/2, y + hitboxHeight /2);
        rigid = new ArrayList<>();

        this.x = x - width/2 ;
        this.y = y - height/2;

        currentCheckpoint = new Checkpoint(context,x, y);
        addInteractionListener(9);

//        this.setX(100);
//        this.setY(100);

        this.setMaxHeight(height);
        this.setAdjustViewBounds(true);
        this.setVisibility(VISIBLE);
    }

    public void setCordsPercentage(float dx, float dy){
         dx *= Genaral.getScreenWidth();
         dy *= Genaral.getScreenHeight();

        setCords(dx, dy);
    }

    public void setCords(float dx, float dy){

        dx = Scene.getXCanvas(dx);
        dy = Scene.getYCanvas(dy);

        rectColider.setCords(dx,dy);
        x = dx - (float) width/2;
        y = dy - (float) height/2;
        this.setX(x);
        this.setY(y);
    }

    public void update(){

        for(RectColider g: rigid){

            if(rectColider.colide(g, 0, 0.1f)){
                jumps = maxJumps;
                break;
            }

        }

        if(boostTimer < boostDecelerationTime && boostTimer>0) {
            velocity.subVector(boostDeceleration);
        }

        if(boostTimer > 0){
            //Don´t add walk velocity while Boost
            boostTimer--;
        }else {
            //Normal add Velocity
            aimVelocity.setVector(MAX_PLAYER_SPEED * GameActivity.getDirection(),0);
            float xExel = aimVelocity.getNumber1() - velocity.getNumber1();
            float yExel = aimVelocity.getNumber2();

            if(jumping){

                if(jumpHeight == JUMP_HEIGHT){
                    velocity.setNumber2(0);
                }

                yExel += min(jumpHeight, 0);
                jumpHeight -= JUMP_HEIGHT * jumpMulti;
            }

            xExel = signum(xExel) * min(abs(xExel), MAX_X_EXEL*floorSlipperiness);
            yExel = signum(yExel) * min(abs(yExel) , MAX_Y_EXEL+gravity);
            yExel += gravity;

            velocity.addVector(new Vector2(xExel, yExel));

            velocity.setNumber1(min(maxXVelocity,velocity.getNumber1()));
            velocity.setNumber2(min(maxYVelocity,velocity.getNumber2()));
        }

        //Move Character according to velocity
        for(int i = 0; i < rigid.size(); i++){

            if(!rectColider.colide(rigid.get(i), velocity.getNumber1(), velocity.getNumber2())){
                continue;
            }

            Vector2 walkVector = new Vector2();
            Vector2 addVector = new Vector2(velocity);
            addVector.divVector(velocity.absValue());
            addVector.divVector(10);

            while( !rectColider.colide(rigid.get(i), walkVector.getNumber1() + addVector.getNumber1(), walkVector.getNumber2() + addVector.getNumber2())
                    && (abs(walkVector.getNumber1()) < abs(velocity.getNumber1()) || abs(walkVector.getNumber2()) < abs(velocity.getNumber2()) )){

                walkVector.addVector(addVector);

            }

            while(!rectColider.colide(rigid.get(i), walkVector.getNumber1() + addVector.getNumber1(), 0) && abs(walkVector.getNumber1()) < abs(velocity.getNumber1()) ) {

                walkVector.addVector(new Vector2(addVector.getNumber1(), 0f));
            }

            while(!rectColider.colide(rigid.get(i), 0f,walkVector.getNumber2() + addVector.getNumber2()) && abs(walkVector.getNumber2()) < abs(velocity.getNumber2())){

                walkVector.addVector(new Vector2(0f, addVector.getNumber2()));
            }

            velocity.setVector(walkVector.getNumber1(), walkVector.getNumber2());
            i = 0;

        }

        if((Genaral.getGameFlags() & Genaral.SLOW_MOTION_TRUE) > 0){

            velocity.divVector(Genaral.SLOW_MOTION_DIV);
        }

        float dx = velocity.getNumber1();
        float dy = velocity.getNumber2();

        rectColider.offset(dx, dy);

        x += dx;
        y += dy;

        //Graphics
        int s = (int) signum(velocity.getNumber1());

        if (s == 0) {
            this.setImageResource(R.drawable.playeridel);
        } else {
            this.setImageResource(R.drawable.playermove);
            this.setScaleX(s);
        }
        this.setMaxHeight(height);
        this.setMaxWidth(width);

        rectColider.update(camera);
        if(camera != null){

            this.setX(x + camera.getXOffset());
            this.setY(y + camera.getYOffset());
            return;
        }

        this.setX(x);
        this.setY(y);
        System.out.println(getX());
        System.out.println(getY());

    }

    public void orbBoost(){

        Vector2 boost = new Vector2(getX(),getY());

        boost.subVector(GameActivity.getLastInputCords());
        boost.divVector(new Vector2(Genaral.getScreenWidth(),Genaral.getScreenHeight()));

        float boostMultiplayer = (float)(pow(rise, boost.absValue()) * riseMulti +minimum);
        boostMultiplayer = min(boostMultiplayer, boostMax);
        boost.divVector(boost.absValue());
        boost.mulVector(boostMultiplayer);

        boostTimer = boostTime;
        boost.divVector(new Vector2(0,2));
        velocity.setVector(boost);
        boostDeceleration.setVector(velocity);
        boostDeceleration.divVector(boostDecelerationTime);

    }

    public void resetMovement(){
        velocity = new Vector2();
        aimVelocity = new Vector2();
    }

    public void resetPlayer(){

        resetMovement();
        this.setCords(currentCheckpoint.getCanvasX(), currentCheckpoint.getCanvasY());

    }

    public void hasGravity(boolean gravity){

        if(gravity){
            this.gravity = Genaral.GRAVITY;
            return;
        }
        this.gravity = 0f;
    }

    @Override
    public boolean couldInteract(Player player){
        return jumps > 1;

    }

    @Override
    public void onInteraction(Player player, int action) {
        switch(action){

            case InteractionManager.INTERACTION_PRESSED:
                jumps--;
                jumping = true;
                break;

            case InteractionManager.INTERACTION_RELEASED:
                jumping = false;
                jumpHeight = JUMP_HEIGHT;
                break;

        }

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectColider.draw(canvas);
    }

    @Override
    public Vector2 getCoordinates() {
        return new Vector2(x,y);
    }

    public float getScreenX(){return this.getX();}
    public float getScreenY(){return this.getY();}


}









































