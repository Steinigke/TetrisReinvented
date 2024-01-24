package com.steinigkejulian.lonlyforest.listeners;

import com.steinigkejulian.lonlyforest.mechanics.Player;
import com.steinigkejulian.lonlyforest.utile.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class CollisionManager {

    private static CollisionManager instance;

    LinkedList<CollisionListener> listeners = new LinkedList<>();

    private CollisionManager(){


    }

    public static CollisionManager get(){
        if(instance == null){
            instance = new CollisionManager();
        }
        return instance;
    }

    public void addCollisionManger(CollisionListener listener){

        listeners.add(listener);

    }

    public void update(Player player) {

        LinkedList<Float> unsortedDistance = new LinkedList<>();

        for (int e = 0; e < listeners.size(); e++) {

            Vector2 playerCords = player.getCoordinates();
            playerCords.subVector(listeners.get(e).getCoordinates());
            unsortedDistance.add(playerCords.absValue());

        }
        LinkedList<Float> sortedDistance = new LinkedList<>(unsortedDistance);
        Collections.sort(sortedDistance);

        for (int e = 0; e < sortedDistance.size(); e++) {

            int index = unsortedDistance.indexOf(sortedDistance.get(e));
            if (listeners.get(index).collisionCheck(player)) {
                listeners.get(index).onCollision(player);
                break;
            }

        }
    }

    public void rest(){
        listeners = new LinkedList<>();
    }

}
