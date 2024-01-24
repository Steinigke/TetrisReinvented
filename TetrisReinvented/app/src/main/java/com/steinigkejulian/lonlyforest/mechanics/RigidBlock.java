package com.steinigkejulian.lonlyforest.mechanics;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.steinigkejulian.lonlyforest.scenes.Scene;
import com.steinigkejulian.lonlyforest.utile.Camera;

public class RigidBlock extends View implements GameObject {

    private float left;
    private float top;
    private Context context;
    private float width;
    private float height;

    private int nature;
    private float strokeWidth = 50/16 * Scene.getDimensions();
    private Paint p;
    private int color1;
    private int color2;
    private int color3;

    Camera camera;

    RectColider rectColider;

    public RectColider getRectColider() {
        return rectColider;
    }

    public RigidBlock(Context context, Player player, float left, float right, float top, float bottom) {
        super(context);
        this.context = context;
        init(player,left,right,top,bottom);
        color1 = 0;
        color2 = 0;
        color3 = 0;

    }

    public RigidBlock(Context context, Player player, int nature, float left, float right, float top, float bottom) {
        super(context);
        this.context = context;
        init(player,left,right,top,bottom);

        switch (nature) {
            case 1: color1 = Color.rgb(104,96,107);
                    color2 = color1;
                    color3 = color1;
                    break;
            case 2: color1 = Color.rgb(28,77,63);
                    color2 = Color.rgb(21,52,54);
                    color3 = Color.rgb(49,131,47);
                    break;
            case 3: color1 = Color.rgb(189,75,30);
                    color2 = Color.rgb(125,46,30);
                    color3 = Color.rgb(235,136,23);
                    break;
            case 4: color1 = Color.rgb(65,26,74);
                    color2 = Color.rgb(39,28,64);
                    color3 = Color.rgb(117,30,109);
                    break;
            case 0:
            default: color1 = 0;
                     color2 = 0;
                     color3 = 0;



        }

    }

    private void init(Player player, float left, float right, float top, float bottom){

        left = Scene.getXCanvas(left);
        top = Scene.getYCanvas(top);
        right = Scene.getXCanvas(right);
        bottom = Scene.getYCanvas(bottom);
        this.left = left;
        this.top = top;
        width = right - left;
        height = bottom - top;

        rectColider = new RectColider(context, Color.BLACK, left, top, right, bottom);
        p = new Paint();

        player.addSolidBlock(rectColider);

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        rectColider.draw(canvas);

    }

    @Override
    public void update(){
        if(camera != null){

            this.setX(left + camera.getXOffset());
            this.setY(top + camera.getYOffset());
            rectColider.update(camera);
            return;
        }

        this.setX(left);
        this.setY(top);
    }

    @Override
    public void setCamera(Camera camera){
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
    public void onDraw(Canvas canvas){

        p.setStyle(Paint.Style.FILL);
        p.setColor(color3);
        p.setStrokeWidth(0f);
        canvas.drawRect(0,0,width,height,p);

        p.setColor(color2);
        canvas.drawRect(strokeWidth,strokeWidth,width - (strokeWidth),height - (strokeWidth),p);

        p.setColor(color1);
        canvas.drawRect(strokeWidth*2,strokeWidth*2,width - (strokeWidth*2),height - (strokeWidth*2),p);
    }
}
