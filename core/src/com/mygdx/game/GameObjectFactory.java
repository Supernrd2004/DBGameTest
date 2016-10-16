package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dan on 10/15/2016.
 */

public class GameObjectFactory {

    private World gameWorld;

    private long nextObjectID;

    private HashMap<String, Sprite> gameSprites;

    public GameObjectFactory(World gameWorld){

        this.gameWorld = gameWorld;
        this.gameSprites = new HashMap<String, Sprite>();
        this.nextObjectID = 1;
    }

    public HashMap<String,Sprite> GetSprites(){
        return this.gameSprites;
    }

    public GameObject CreateGameObject(Vector2 startingPosition, String spriteName, boolean isSensor){

        GameObject newGameObject;
        Sprite gameSprite;

        if (!gameSprites.containsKey(spriteName)){
            gameSprite = CreateSprite(spriteName);
        }else{
            gameSprite = gameSprites.get(spriteName);
        }

        Body newGameBody;
        Fixture newGameFixture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startingPosition.x / GameWorld.PIXELS_TO_METERS, startingPosition.y / GameWorld.PIXELS_TO_METERS);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(gameSprite.getWidth() /2 / GameWorld.PIXELS_TO_METERS, gameSprite.getHeight() /2 / GameWorld.PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = isSensor;

        newGameBody = gameWorld.createBody(bodyDef);
        newGameFixture = newGameBody.createFixture(fixtureDef);

        newGameObject = new GameObject(gameSprite,startingPosition,newGameBody,GetNextGameID());

        shape.dispose();

        return newGameObject;

    }

    public GameObject CreateStaticGameObject(Vector2 startingPosition, int width, int height, boolean isSensor){

        GameObject newGameObject;

        Body staticGameBody;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(startingPosition.x / GameWorld.PIXELS_TO_METERS, startingPosition.y / GameWorld.PIXELS_TO_METERS);

        PolygonShape newShape = new PolygonShape();
        newShape.setAsBox(width/2 / GameWorld.PIXELS_TO_METERS,height/2 / GameWorld.PIXELS_TO_METERS);

        staticGameBody = gameWorld.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = newShape;
        fixtureDef.isSensor = isSensor;

        staticGameBody.createFixture(fixtureDef);

        newGameObject = new GameObject(staticGameBody,width,height,GetNextGameID());

        newShape.dispose();

        return newGameObject;

    }

    public GameObject CreateStaticGameObject(Vector2 startingPosition, String spriteName, boolean isSensor){

        GameObject newGameObject;
        Sprite gameSprite;

        if (!gameSprites.containsKey(spriteName)){
            gameSprite = CreateSprite(spriteName);
        }else{
            gameSprite = gameSprites.get(spriteName);
        }

        Body staticGameBody;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(startingPosition.x / GameWorld.PIXELS_TO_METERS, startingPosition.y / GameWorld.PIXELS_TO_METERS);

        PolygonShape newShape = new PolygonShape();
        newShape.setAsBox(gameSprite.getWidth()/2 / GameWorld.PIXELS_TO_METERS,gameSprite.getHeight()/2 / GameWorld.PIXELS_TO_METERS);

        staticGameBody = gameWorld.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = newShape;
        fixtureDef.isSensor = isSensor;

        staticGameBody.createFixture(fixtureDef);

        newGameObject = new GameObject(gameSprite,startingPosition,staticGameBody,GetNextGameID());

        newShape.dispose();

        return newGameObject;

    }

    public long GetNextGameID(){
        long newID = nextObjectID;
        nextObjectID = nextObjectID + 1;
        return newID;
    }

    public long PeekNextGameID(){
        return nextObjectID;
    }

    public Sprite CreateSprite(String textureName){

        Texture newTexture = new Texture(textureName);
        Sprite newSprite = new Sprite(newTexture);
        gameSprites.put(textureName, newSprite);
        return newSprite;
    }


}

