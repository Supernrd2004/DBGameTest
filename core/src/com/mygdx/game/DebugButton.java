package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Dan on 10/15/2016.
 */

public class DebugButton extends GameObject {

    public DebugButton(Sprite gameSprite, Vector2 startingPosition, Body gameBody, long ID){

        super(gameSprite, startingPosition, gameBody, ID);
    }

    public void TouchAction(){

    }
}
