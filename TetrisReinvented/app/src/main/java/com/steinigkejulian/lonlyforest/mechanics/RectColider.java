package com.steinigkejulian.lonlyforest.mechanics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Genaral;

public class RectColider extends View {



    private Paint p = new Paint();
    private int color;
    private RectF rectangel;

    private float x;
    private float y;
    private float width;
    private float height;

//    public float getWorldX() {
//        return x;
//    }
//
//    public float getWorldY() {
//        return y;
//    }

    public RectF getRectangel() {
        return rectangel;
    }

    public RectColider(Context context){
        super(context);
        rectangel = new RectF();

    }

    public RectColider(Context context, Integer color, float left, float top, float right, float bottom){
        super(context);

        rectangel = new RectF(left,top,right,bottom);
        x = left;
        y = top;
        width = right - left;
        height = bottom - top;

        this.setX(x);
        this.setY(y);

        if (color == null) {
            this.color = Color.argb(1f,1f,0f,0f);
            return;
        }
        this.color = color;
    }

//    public void setColider(float left, float top, float right, float bottom){
//
//        rectangel.set(left,top,right,bottom);
//
//    }

    public boolean colide(RectColider colider, float dx, float dy){

        rectangel.offset(dx, dy);

        if(RectF.intersects(rectangel, colider.getRectangel())
            || this.getRectangel().contains(colider.getRectangel()) ){

            rectangel.offset(-dx, -dy);
            return true;

        }

        rectangel.offset(-dx, -dy);

        return false;

    }

    public boolean colide(RectColider colider){

        if(RectF.intersects(this.getRectangel(),colider.getRectangel())
                || this.getRectangel().contains(colider.getRectangel()) ){

            return true;

        }

        return false;

    }

    public void offset(float dx, float dy){

        rectangel.offset(dx, dy);

        x += dx;
        y += dy;

    }

    public void setCords(float dx, float dy){

        this.x = dx - width/2;
        this.y = dy - height/2;

        rectangel.set(x, y, x + width, y + height);

    }

    public void update(Camera camera){
        if (camera == null) {
            this.setX(x);
            this.setY(y);
            return;
        }
        this.setX(x+camera.getXOffset());
        this.setY(y+camera.getYOffset());


    }

    //Debugging
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!Genaral.showHitboxes){
            return;
        }
        p.setStrokeWidth(0f);
        p.setColor(color);
        canvas.drawRect(0,0,width,height,p);

    }
}
