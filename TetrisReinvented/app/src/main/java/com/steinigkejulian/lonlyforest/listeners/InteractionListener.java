package com.steinigkejulian.lonlyforest.listeners;

import com.steinigkejulian.lonlyforest.mechanics.Player;
import com.steinigkejulian.lonlyforest.utile.Vector2;

public interface InteractionListener {


    default boolean addInteractionListener(int level){

        return InteractionManager.get().addInteractionListener(level,this);
    }

    default boolean couldInteract(Player player){

        return true;
    }

    default boolean noRelease(Player player){

        return true;
    }

    void onInteraction(Player player, int action);

    Vector2 getCoordinates();



}
