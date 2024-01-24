package com.steinigkejulian.lonlyforest.mechanics;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.steinigkejulian.lonlyforest.R;
import com.steinigkejulian.lonlyforest.scenes.Scene;
import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Genaral;
import com.steinigkejulian.lonlyforest.utile.Vector2;
import com.steinigkejulian.lonlyforest.listeners.*;

public class BoostOrb extends android.support.v7.widget.AppCompatImageView implements InteractionListener ,GameObject {

    private float x;
    private float y;
    private final float resetTime = 100;
    private float timer = 0;
    private int radius = (int)(125      * Scene.getDimensions());

    private Camera camera;

    private RoundColider roundColider;

    public RoundColider getRoundColider() {
        return roundColider;
    }

    public BoostOrb(Context context, float x, float y) {
        super(context);

        x = Scene.getXCanvas(x);
        y = Scene.getYCanvas(y);

        this.x = x-radius;
        this.y = y-radius;

        roundColider = new RoundColider(context, x, y, radius);
        addInteractionListener(1);

        this.setImageResource(R.drawable.orbinaktive);
        this.setAdjustViewBounds(true);
        this.setMaxHeight(radius*2);
    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        roundColider.draw(canvas);

    }

    @Override
    public boolean couldInteract(Player player) {
        if(timer >= 0){
            this.setImageResource(R.drawable.orbdeaktive);
            return false;
        }
        if(roundColider.intersects(player.getRectColider())){

            this.setImageResource(R.drawable.orbaktive);
            return true;
        }
        this.setImageResource(R.drawable.orbinaktive);
        return false;
    }

    @Override
    public void onInteraction(Player player, int action) {
        switch (action){

            case InteractionManager.INTERACTION_PRESSED:
                Genaral.setGameFlag(Genaral.IGNORE_INPUTS_TRUE);
                Genaral.setGameFlag(Genaral.SLOW_MOTION_TRUE);
                break;

            case InteractionManager.INTERACTION_RELEASED:

                player.orbBoost();
                timer = resetTime;

                Genaral.setGameFlag(Genaral.IGNORE_INPUTS_FALSE);
                Genaral.setGameFlag(Genaral.SLOW_MOTION_FALSE);
                break;
        }
    }

    @Override
    public boolean noRelease(Player player) {
        Genaral.setGameFlag(Genaral.IGNORE_INPUTS_FALSE);
        Genaral.setGameFlag(Genaral.SLOW_MOTION_FALSE);
        return false;
    }

    @Override
    public Vector2 getCoordinates() {
        return new Vector2(x,y);
    }

    @Override
    public void update(){

        timer--;
        Math.max(timer, 0);

        if(camera != null){

            this.setX(x + camera.getXOffset());
            this.setY(y + camera.getYOffset());
            roundColider.update(camera);
            return;
        }

        this.setX(x);
        this.setY(y);
    }

    @Override
    public void setCamera(Camera camera){
        this.camera = camera;
    }

    @Override
    public View getHitbox() {
        return roundColider;
    }

    @Override
    public View getView() {
        return this;
    }

}












































