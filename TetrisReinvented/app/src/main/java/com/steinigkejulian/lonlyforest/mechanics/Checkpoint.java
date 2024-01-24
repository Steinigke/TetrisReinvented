package com.steinigkejulian.lonlyforest.mechanics;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.steinigkejulian.lonlyforest.R;
import com.steinigkejulian.lonlyforest.listeners.CollisionListener;
import com.steinigkejulian.lonlyforest.scenes.Scene;
import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Genaral;
import com.steinigkejulian.lonlyforest.utile.Vector2;

public class Checkpoint extends android.support.v7.widget.AppCompatImageView implements GameObject, CollisionListener {

    private float x;
    private float y;
    private float canvasX;
    private float canvasY;
    private int height = (int) (75 * Scene.getDimensions());
    private int hitboxWidth = (int) (75 * Scene.getDimensions());
    private int hitboxHeight = (int) (75 * Scene.getDimensions());
    private RectColider rectColider;
    private Camera camera;

    public float getCanvasX() {
        return canvasX;
    }

    public float getCanvasY() {
        return canvasY;
    }

    public Checkpoint(Context context, float x, float y){
        super(context);

        canvasX = x;
        canvasY = y;

        x = Scene.getXCanvas(x);
        y = Scene.getYCanvas(y);


        this.x = x - height/2;
        this.y = y - height/2;

        rectColider = new RectColider(context, Color.BLUE, x - hitboxWidth/2, y - hitboxHeight/2, x +hitboxWidth/2, y + hitboxHeight/2);

        this.addCollisionListener();

        this.setImageResource(R.drawable.checkpointinactive);
        this.setAdjustViewBounds(true);
        this.setMaxHeight(height);
    }

    public Checkpoint(float x, float y, Context context){
        super(context);

        canvasX = x;
        canvasY = y;

        this.x = Scene.getXCanvas(x) -height/2;
        this.y = Scene.getYCanvas(y) -height/2;

        this.setVisibility(View.INVISIBLE);
    }


    @Override
    public void update() {

        if(camera != null){

            this.setX(x + camera.getXOffset());
            this.setY(y + camera.getYOffset());
            rectColider.update(camera);
            return;
        }

        this.setX(x);
        this.setY(y);

    }

    @Override
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public View getHitbox() {
        return rectColider;
    }

    @Override
    public View getView() {
        return this;
    }


    @Override
    public void onCollision(Player player) {

        player.setCheckpoint(this);
        this.setImageResource(R.drawable.checkpointactive);
    }

    @Override
    public boolean collisionCheck(Player player) {
        return rectColider.colide(player.getRectColider());
    }

    @Override
    public Vector2 getCoordinates() {
        return new Vector2(x,y);
    }
}
