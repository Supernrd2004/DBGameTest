package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by Dan on 9/29/2016.
 */

public class GameController {

    private World gameWorld;
    private HashMap<String, Sprite> gameSprites;

    private float gravity = -500F;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<StaticGameObject> staticGameObjects;

    public GameController (int width, int height){

        this.gameWorld = new World(new Vector2(0,gravity),true);

        gameSprites = new HashMap<String, Sprite>();
        gameObjects = new ArrayList<GameObject>();
        staticGameObjects = new ArrayList<StaticGameObject>();

        InitializeGameObjects(width, height);
    }

    public void InitializeGameObjects( int width, int height){

        CreateStaticGameObject(new Vector2(width /2,100),width,50);

        for(int i = 0; i < 4; i++){
            float x = (float)Math.random() * 1000;
            float y = (float)Math.random() * 1000;

            CreateGameObject(new Vector2(x,y),"badlogic.jpg");
        }
    }

    public ArrayList<GameObject> GetGameObjects(){
        return gameObjects;
    }

    public ArrayList<StaticGameObject> GetStaticGameObjects(){
        return staticGameObjects;
    }

    public void CreateGameObject(Vector2 startingPosition, String spriteName){

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
        bodyDef.position.set(startingPosition.x, startingPosition.y);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(gameSprite.getWidth() /2, gameSprite.getHeight() /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        //set value of 'bounce' 0 to 1
        fixtureDef.restitution = 0.8f;

        newGameBody = gameWorld.createBody(bodyDef);
        newGameFixture = newGameBody.createFixture(fixtureDef);

        newGameObject = new GameObject(gameSprite,startingPosition,newGameBody);

        gameObjects.add(newGameObject);

        shape.dispose();

    }

    public void CreateStaticGameObject(Vector2 startingPosition, int width, int height){

        StaticGameObject newGameObject;

        Body staticGameBody;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(startingPosition.x, startingPosition.y);

        PolygonShape newShape = new PolygonShape();
        newShape.setAsBox(width/2,height/2);

        staticGameBody = gameWorld.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = newShape;

        staticGameBody.createFixture(fixtureDef);

        newGameObject = new StaticGameObject(startingPosition,staticGameBody,width,height);

        staticGameObjects.add(newGameObject);

        newShape.dispose();

    }

    public World GetWorld(){
        return gameWorld;
    }

    public Sprite GetSprite(String spriteName){
        return gameSprites.get(spriteName);
    }

    public Sprite CreateSprite(String textureName){

        Texture newTexture = new Texture(textureName);
        Sprite newSprite = new Sprite(newTexture);
        gameSprites.put(textureName, newSprite);
        return newSprite;

    }

}
