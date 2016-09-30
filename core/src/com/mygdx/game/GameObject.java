package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


/**
 * Created by Dan on 9/28/2016.
 */
public class GameObject {

    private Sprite objectSprite;
    private Body objectBody;
    private Fixture objectFixture;

    public GameObject(Sprite gameSprite, Vector2 startingPosition, Body gameBody){

        objectSprite = gameSprite;
        objectBody = gameBody;
        InitSpritePosition(objectBody.getPosition(), gameSprite);
    }

    private void InitSpritePosition(Vector2 startingPosition, Sprite gameSprite){
        gameSprite.setPosition(startingPosition.x, startingPosition.y);
    }

    public Vector2 GetPosition(){
        return objectBody.getPosition();
    }

    public void UpdateSpritePosition(){
        objectSprite.setPosition(objectBody.getPosition().x, objectBody.getPosition().y);
    }

    public Sprite GetSprite(){
        return objectSprite;
    }
}
