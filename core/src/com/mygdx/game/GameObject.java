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

    private float width;
    private float height;

    private boolean hasSprite;

    public GameObject(Sprite gameSprite, Vector2 startingPosition, Body gameBody){

        objectSprite = gameSprite;
        objectBody = gameBody;
        this.hasSprite = true;
        InitSpritePosition(objectBody.getPosition(), gameSprite);

    }

    public GameObject( Vector2 startingPosition, Body gameBody, float width, float height){

        objectBody = gameBody;
        this.hasSprite = false;
    }

    private void InitSpritePosition(Vector2 startingPosition, Sprite gameSprite){
        gameSprite.setPosition(startingPosition.x, startingPosition.y);
    }

    public Vector2 GetPosition(){
        return objectBody.getPosition();
    }

    public void UpdateSpritePosition(){
        objectSprite.setPosition(objectBody.getPosition().x * GameWorld.PIXELS_TO_METERS, objectBody.getPosition().y * GameWorld.PIXELS_TO_METERS);
        objectSprite.setRotation((float)Math.toDegrees(objectBody.getAngle()));
    }

    public Sprite GetSprite(){
        return objectSprite;
    }

    public Body GetBody(){
        return objectBody;
    }

    public boolean HasSprite(){
        return true;
    }
}
