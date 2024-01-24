package com.steinigkejulian.lonlyforest.mechanics;

import static java.lang.Math.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Genaral;
import com.steinigkejulian.lonlyforest.utile.Vector2;

public class RoundColider extends View {

    private float x;
    private float y;
    private float radius;

    private Paint p = new Paint();

    public RoundColider(Context context){
        super(context);


    }

    public RoundColider(Context context, float x, float y, float radius) {
        super(context);

        this.radius = radius;
        this.x = x - radius;
        this.y = y - radius;

        setX(this.x);
        setY(this.y);

    }

    public void setCords(float x, float y){

        this.x = x - radius;
        this.y = y - radius;

        setX(this.x);
        setY(this.y);
    }

    public void offSet(Vector2 offset){

        this.x += offset.getNumber1();
        this.y += offset.getNumber2();

    }

    public boolean intersects(float x, float y){

        float conRadius = (float) sqrt(pow((this.x + radius) - x ,2)+pow((this.y +radius) -y ,2));

//        System.out.println("Point x = " + x + " || Point y = " + y);
//        System.out.println("This  x = " + this.x + radius + " || This  y = " + this.y + radius);
//        System.out.println("Kugelgleichung = " + conRadius + "|| Radius = " + radius);
//        System.out.println("Max x direction = " + this.x + 2*radius +"|| Max y direction = " +  this.y + 2*radius);

        if(conRadius < radius){
//            System.out.println("Intersection = true");
//            System.out.println("------------------------------------------------------------");
            return true;

        }

//        System.out.println("Intersection = false");
//        System.out.println("------------------------------------------------------------");
        return false;

    }

    public boolean intersects(RectColider rectColider){

        RectF colider = rectColider.getRectangel();

        float x = this.x + radius;
        float y = this.y + radius;

        float rectX1 = colider.left;
        float rectX2 = colider.right;
        float dx1 = abs(rectX1 - x);
        float dx2 = abs(rectX2 - x);

        float rectY1 = colider.top;
        float rectY2 = colider.bottom;
        float dy1 = abs(rectY1 - y);
        float dy2 = abs(rectY2 - y);

        boolean useYcord = (rectX1 < x) && (rectX2 > x);
        boolean useXcord = (rectY1 < y) && (rectY2 > y);
        boolean useCorner = !(useYcord ||useXcord);


        if(dx1  > dx2){

//            dx1 = dx2;
            rectX1 = rectX2;

        }

        if(dy1 > dy2){

//            dy1 = dy2;
            rectY1 = rectY2;

        }
        if(useCorner){
            return intersects(rectX1, rectY1);
        }

        if(useXcord && useYcord){

            return true;

        }
        if(useXcord){
            return intersects(rectX1, y);

        }
        return intersects(x, rectY1);

    }

    public void update(Camera camera){

        this.setX(x + camera.getXOffset());
        this.setY(y + camera.getYOffset());

    }

    //Debugging
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!Genaral.showHitboxes){
            return;
        }

        p.setStrokeWidth(10);
        p.setColor(Color.BLUE);


        canvas.drawCircle(radius, radius,radius,p);
        p.setColor(Color.BLACK);
        canvas.drawCircle(radius,radius,5,p);
    }
}































































