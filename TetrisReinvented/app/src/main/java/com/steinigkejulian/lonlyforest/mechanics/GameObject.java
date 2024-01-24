package com.steinigkejulian.lonlyforest.mechanics;

import android.view.View;

import com.steinigkejulian.lonlyforest.utile.Camera;

public interface GameObject {

    void update();
    void setCamera(Camera camera);
    View getHitbox();
    View getView();

}
