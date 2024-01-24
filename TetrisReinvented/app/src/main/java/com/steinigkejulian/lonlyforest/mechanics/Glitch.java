package com.steinigkejulian.lonlyforest.mechanics;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.steinigkejulian.lonlyforest.R;
import com.steinigkejulian.lonlyforest.listeners.CollisionListener;
import com.steinigkejulian.lonlyforest.scenes.Scene;
import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Vector2;

public class Glitch extends android.support.v7.widget.AppCompatImageView implements CollisionListener, GameObject {

    private float x;
    private float y;
    private float width = 100       * Scene.getDimensions();
    private float height = 100      * Scene.getDimensions();

    private RectColider rectColider;
    private Camera camera;

    public Glitch(Context context, float x, float y){
        super(context);

        this.x = Scene.getXCanvas(x) - width/2;
        this.y = Scene.getYCanvas(y) - height/2;

        rectColider = new RectColider(context, Color.RED, this.x, this.y, this.x+width, this.y + height);

        addCollisionListener();

        this.setImageResource(R.drawable.glitch);
        this.setAdjustViewBounds(true);
        this.setMaxHeight((int)height);

    }


    @Override
    public void onCollision(Player player) {
        player.resetPlayer();
    }

    @Override
    public boolean collisionCheck(Player player) {
        return rectColider.colide(player.getRectColider());

    }

    @Override
    public Vector2 getCoordinates() {
        return new Vector2(x,y);
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
}
