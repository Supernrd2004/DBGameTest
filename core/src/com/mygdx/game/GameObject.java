package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.javafx.geom.Point2D;


/**
 * Created by Dan on 9/28/2016.
 */
public class GameObject {

    private Sprite objectSprite;
    private Body objectBody;
    private Fixture objectFixture;

    private long gameObjectID;

    private float width;
    private float height;

    private boolean hasSprite;

    public GameObject(Sprite gameSprite, Vector2 startingPosition, Body gameBody, long ID){



        this.gameObjectID = ID;
        objectSprite = gameSprite;
        objectBody = gameBody;
        this.hasSprite = true;
        InitSpritePosition(objectBody.getPosition(), gameSprite);
        this.width = gameSprite.getWidth();
        this.height = gameSprite.getHeight();

    }

    public GameObject(Body gameBody, float width, float height, long ID){

        this.gameObjectID = ID;
        this.width = width;
        this.height = height;
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
        if (this.hasSprite){
            objectSprite.setPosition(objectBody.getPosition().x * GameWorld.PIXELS_TO_METERS, objectBody.getPosition().y * GameWorld.PIXELS_TO_METERS);
            objectSprite.setRotation((float)Math.toDegrees(objectBody.getAngle()));
        }
    }

    public float GetWidth(){
        return this.width;
    }

    public float GetHeight(){
        return this.height;
    }

    public Sprite GetSprite(){
        return objectSprite;
    }

    public Body GetBody(){
        return objectBody;
    }

    public boolean HasSprite(){
        return this.hasSprite;
    }

    public long GetGameObjectID(){
        return this.gameObjectID;
    }

    public boolean ObjectIsEqual(GameObject other){
        if (this.GetGameObjectID() == other.GetGameObjectID()){
            return true;
        }
        return false;
    }

    public boolean IsTouched(Vector2 touchLocation){
        for (Fixture f:this.GetBody().getFixtureList())
        {
            if (f.testPoint(touchLocation.x / GameWorld.PIXELS_TO_METERS,touchLocation.y / GameWorld.PIXELS_TO_METERS)){
                return true;
            }
        }
        return false;
    }

    public void TouchAction(){

    }

    public void CollisionAction(){

    }
}
