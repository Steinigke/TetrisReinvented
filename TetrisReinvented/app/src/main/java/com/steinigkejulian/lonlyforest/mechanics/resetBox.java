package com.steinigkejulian.lonlyforest.mechanics;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;

import com.steinigkejulian.lonlyforest.listeners.CollisionListener;
import com.steinigkejulian.lonlyforest.scenes.Scene;
import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Vector2;

public class resetBox implements GameObject, CollisionListener {

    private float x;
    private float y;
    private RectColider colider;
    private Camera camera;

    public resetBox(Context context, float left, float right, float top, float bottom){
        left = Scene.getXCanvas(left);
        right = Scene.getXCanvas(right);
        top = Scene.getYCanvas(top);
        bottom = Scene.getYCanvas(bottom);

        x = right - left/2;
        y = bottom - top/2;

        colider = new RectColider(context, Color.RED, left, top, right,bottom);
        addCollisionListener();
    }
    @Override
    public void onCollision(Player player) {
        player.resetPlayer();
    }

    @Override
    public boolean collisionCheck(Player player) {
        return colider.colide(player.getRectColider());
    }

    @Override
    public Vector2 getCoordinates() {
        return new Vector2(x,y);
    }

    @Override
    public void update() {

        if(camera != null){
            colider.update(camera);
        }

    }

    @Override
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public View getHitbox() {
        return colider;
    }

    @Override
    public View getView() {
        return null;
    }
}
