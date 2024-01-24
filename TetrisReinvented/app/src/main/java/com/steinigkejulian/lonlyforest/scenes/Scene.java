package com.steinigkejulian.lonlyforest.scenes;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.steinigkejulian.lonlyforest.listeners.CollisionManager;
import com.steinigkejulian.lonlyforest.listeners.InteractionManager;
import com.steinigkejulian.lonlyforest.mechanics.BoostOrb;
import com.steinigkejulian.lonlyforest.mechanics.Checkpoint;
import com.steinigkejulian.lonlyforest.mechanics.Door;
import com.steinigkejulian.lonlyforest.mechanics.GameObject;
import com.steinigkejulian.lonlyforest.mechanics.Player;
import com.steinigkejulian.lonlyforest.mechanics.RectColider;
import com.steinigkejulian.lonlyforest.mechanics.RigidBlock;
import com.steinigkejulian.lonlyforest.mechanics.Glitch;
import com.steinigkejulian.lonlyforest.mechanics.resetBox;
import com.steinigkejulian.lonlyforest.utile.Camera;
import com.steinigkejulian.lonlyforest.utile.Genaral;
import com.steinigkejulian.lonlyforest.utile.Layout;

import java.util.ArrayList;

public abstract class Scene {

    protected Camera camera;
    protected Player player;
    protected int BackgroundColor;

    protected final static float screenX = 2160;
    protected final static float screenY = 1080;
    private static float yScreenDiemisions;
    private static float xScreenDiemisions;
    private static float xCanvas;
    private static float yCanvas;
    private static float dimensions;
    private static float xSidePercentage;
    private static float ySidePercentage;

    public Player getPlayer() {
        return player;
    }

    public static float getDimensions() {
        return dimensions;
    }

    protected Context context;
    ArrayList<View>[] views = new ArrayList[4];

    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    private static void setDiemisions(){

        int screenHeight = Genaral.getScreenHeight();
        int screenWidth = Genaral.getScreenWidth();

        xScreenDiemisions = screenY/screenX;
        yScreenDiemisions = screenX/screenY;



        if(screenWidth < screenHeight*yScreenDiemisions){
            xSidePercentage = 0;
            ySidePercentage = screenHeight - screenWidth*xScreenDiemisions;
            ySidePercentage = ySidePercentage / screenHeight;

            yCanvas = screenWidth*xScreenDiemisions;
            xCanvas = screenWidth;
            dimensions = yCanvas/screenY;

            return;
        }

        ySidePercentage = 0;
        xSidePercentage = screenWidth - screenHeight*yScreenDiemisions;
        xSidePercentage = (xSidePercentage / screenWidth) / 2;

        yCanvas = screenHeight;
        xCanvas = screenHeight * yScreenDiemisions;

        dimensions = yCanvas/screenY;
    }

    public static float getXCanvas(float x){

        int screenWidth = Genaral.getScreenWidth();

        int page = (int)(x/screenX);
        x %= screenX;
        x = (x* dimensions) + page*screenWidth + Math.signum(x)*xSidePercentage*screenWidth;
        return x;

    }

    public static float getYCanvas(float y){

        int screenHeight = Genaral.getScreenHeight();

        int page = (int)(y/screenY);
        y %= screenY;
        y = (y* dimensions) + page*screenHeight + Math.signum(y)*ySidePercentage*screenHeight;
        return y;


    }

    protected Scene(Context context, Integer pages){
        this.context = context;
        Scene.setDiemisions();

        InteractionManager.get().resetInteractions();
        CollisionManager.get().rest();

        player = new Player(context);

        for(int i = 0; i < views.length; i++){

            views[i] = new ArrayList<>();
        }

        BackgroundColor = Color.CYAN;

        if(pages == null ){
            return;
        }

        camera = new Camera(player, 0, Genaral.getScreenHeight(), 0, Genaral.getScreenWidth()*pages);
    }

    public void update(){
        if((Genaral.getGameFlags() & Genaral.SCENE_PAUSED_TRUE) > 0){

            return;
        }

        if(camera != null) {
            camera.update();
        }

        InteractionManager.get().update(player);
        CollisionManager.get().update(player);

        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).update();
        }

        player.update();


    }

    public abstract void setUp();

    public void setUpLayout(Layout layout){

        layout.setBackgroundColor(BackgroundColor);

        for(int i = 0; i < views.length; i++){
            layout.addGameView(views[i], i);
        }

    }

    private void addGameObject(int layer, GameObject gameObject){

        gameObjects.add(gameObject);
        View view = gameObject.getView();
        if(view != null){
            views[layer].add(gameObject.getView());
        }
        view = gameObject.getHitbox();
        if(view != null){
            views[3].add(gameObject.getHitbox());
            }

    }

    public void addEnteties(String entetie, int page ,float x, float y){

        x += screenX*page;

        switch(entetie){

            case "boostOrb":
            case "orb" :  addGameObject(2,new BoostOrb(context, x,y));
                        break;
            case "checkpoint" : addGameObject(2,new Checkpoint(context,x,y)); break;
            case "glitch" : addGameObject(2, new Glitch(context,x,y)); break;
        }
    }

    public void addDoor(int nextScene, int page, float x, float y){
        x += page * screenX;
        addGameObject(2, new Door(context,x,y,nextScene));
    }

    public void addRigid(int nature, int page, float left, float right, float top, float bottom){
        left += page*screenX;
        right += page*screenX;
        addGameObject(1, new RigidBlock(context, player, nature, left,right,top,bottom));
    }

    public void addReset(int page, float left, float right,float top, float bottom){
        left += page*screenX;
        right += page*screenX;
        addGameObject(1,new resetBox(context, left,right,top,bottom));
    }

    protected void setUpPlayer(int x, int y){
        player.hasGravity(true);
        player.setCords(x,y);
        player.setCheckpoint( new Checkpoint(x, y, context));

        views[3].add(player.getRectColider());
        views[2].add(player);
    }

    protected void addCameraTooObjects(){
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).setCamera(camera);
        }

        player.setCamera(camera);

    }

}
