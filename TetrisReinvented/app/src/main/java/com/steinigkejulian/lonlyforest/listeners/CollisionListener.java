package com.steinigkejulian.lonlyforest.listeners;

import com.steinigkejulian.lonlyforest.mechanics.Player;
import com.steinigkejulian.lonlyforest.utile.Vector2;

public interface CollisionListener {

    default void addCollisionListener(){

        CollisionManager.get().addCollisionManger(this);
    }

    void onCollision(Player player);

    boolean collisionCheck(Player player);

    Vector2 getCoordinates();

}
