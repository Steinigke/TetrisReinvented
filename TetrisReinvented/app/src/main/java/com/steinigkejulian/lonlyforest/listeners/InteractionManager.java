package com.steinigkejulian.lonlyforest.listeners;

import com.steinigkejulian.lonlyforest.utile.Genaral;
import com.steinigkejulian.lonlyforest.mechanics.Player;
import com.steinigkejulian.lonlyforest.utile.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class InteractionManager {

    public final static int INTERACTION_PRESSED = 1;
    public final static int INTERACTION_RELEASED = 2;

    private static InteractionManager instance;

    ArrayList<InteractionListener>[] interactionListeners = new ArrayList[10];

    int interactionLevel;
    int interactionIndex;

    int lastInteractionLevel;
    int lastInteractionIndex;


    private InteractionManager(){
        resetInteractions();
    }

    public static InteractionManager get(){
        if (instance == null) {
            instance = new InteractionManager();
        }
        return instance;
    }

    public boolean addInteractionListener(int level,InteractionListener interactionListener){

        if(level >= interactionListeners.length){
            return false;
        }

        interactionListeners[level].add(interactionListener);
        return true;

    }

    public void update(Player player){

        for (int i = 0; i < interactionListeners.length; i++){

            LinkedList<Float> unsortedDistance = new LinkedList<>();

            for(int e = 0; e < interactionListeners[i].size(); e++) {

                Vector2 playerCords = player.getCoordinates();
                playerCords.subVector(interactionListeners[i].get(e).getCoordinates());
                unsortedDistance.add(playerCords.absValue());

            }
            LinkedList<Float> sortedDistance = new LinkedList<>(unsortedDistance);
            Collections.sort(sortedDistance);

            for(int e = 0; e < sortedDistance.size(); e++) {

                int index = unsortedDistance.indexOf(sortedDistance.get(e));
                if(interactionListeners[i].get(index).couldInteract(player)){
                    interactionIndex = index;
                    interactionLevel = i;
                    i = interactionListeners.length;
                    break;
                }
            }
        }

        try {
            if (interactionListeners[lastInteractionLevel].get(lastInteractionIndex).couldInteract(player)){
                return;
            }

            if(interactionListeners[lastInteractionLevel].get(lastInteractionIndex).noRelease(player)){
                return;
            }

            lastInteractionLevel = -1;
            lastInteractionIndex = -1;

        }catch (IndexOutOfBoundsException ignored){}
    }

    public void interaction(Player player, int action){

        try {
            if(action == INTERACTION_RELEASED){
                interactionListeners[lastInteractionLevel].get(lastInteractionIndex).onInteraction(player, action);
                return;
            }

            if((Genaral.getGameFlags() & Genaral.IGNORE_INPUTS_TRUE) > 0){
                return;
            }

            if (interactionListeners[interactionLevel].get(interactionIndex).couldInteract(player)) {
                interactionListeners[interactionLevel].get(interactionIndex).onInteraction(player, action);
                lastInteractionIndex = interactionIndex;
                lastInteractionLevel = interactionLevel;
            }

        }catch(IndexOutOfBoundsException ignored){}


    }


    public void resetInteractions(){
        for(int i = 0; i < interactionListeners.length; i++) {
            interactionListeners[i] = new ArrayList<>();
        }
    }
}
