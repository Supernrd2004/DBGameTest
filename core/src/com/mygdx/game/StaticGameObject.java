package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by Dan on 10/11/2016.
 */

public class StaticGameObject {

    private int width;
    private int height;

    private Sprite objectSprite;
    private Body objectBody;
    private Fixture objectFixture;

    public StaticGameObject(Vector2 startingPosition, Body gameBody, int width, int height){
        this.width = width;
        this.height = height;
        objectBody = gameBody;
    }

    public Vector2 GetPosition(){
        return objectBody.getPosition();
    }

    public Sprite GetSprite(){
        return objectSprite;
    }

    public Body GetBody(){
        return objectBody;
    }

    public int GetWidth(){
        return this.width;
    }

    public int GetHeight(){
        return this.height;
    }

    public boolean HasSprite(){
        return false;
    }

}
