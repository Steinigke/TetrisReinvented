package com.steinigkejulian.lonlyforest.mechanics;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.steinigkejulian.lonlyforest.R;
import com.steinigkejulian.lonlyforest.activitys.GameActivity;
import com.steinigkejulian.lonlyforest.listeners.CollisionListener;
import com.steinigkejulian.lonlyforest.scenes.Scene;
import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Vector2;

public class Door extends android.support.v7.widget.AppCompatImageView implements GameObject, CollisionListener {

    private float width = 225 * Scene.getDimensions();
    private float height = 225 * Scene.getDimensions();
    private float hitboxHeight = 225;
    private float hitboxWidth = 90;

    private float x;
    private float y;
    private int nextScene;

    private RectColider rectColider;
    private Camera camera;

    public Door(Context context,float x,float y, int nextScene) {
        super(context);

        x = Scene.getXCanvas(x);
        y = Scene.getYCanvas(y);

        this.x = x - width/2;
        this.y = y - height;
        rectColider = new RectColider(context, Color.YELLOW,x - hitboxWidth/2,y - hitboxHeight,x + hitboxWidth/2,y);
        this.nextScene = nextScene;
        addCollisionListener();

        this.setImageResource(R.drawable.door);
        this.setAdjustViewBounds(true);
        this.setMaxHeight((int)height);
    }

    @Override
    public void onCollision(Player player) {
        GameActivity.changeScene(nextScene);
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
