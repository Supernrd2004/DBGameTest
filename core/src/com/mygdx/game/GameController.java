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

    private float gravity = -98F;

    private ArrayList<GameObject> gameObjects;

    public GameController (){

        this.gameWorld = new World(new Vector2(0,gravity),true);
        gameSprites = new HashMap<String, Sprite>();
        gameObjects = new ArrayList<GameObject>();
    }

    public void InitializeGameObjects(){
        CreateGameObject(new Vector2(Gdx.graphics.getWidth() /2 ,Gdx.graphics.getHeight() /2),"badlogic.jpg");
    }

    public ArrayList<GameObject> GetGameObjects(){
        return gameObjects;
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

        newGameBody = gameWorld.createBody(bodyDef);
        newGameFixture = newGameBody.createFixture(fixtureDef);

        newGameObject = new GameObject(gameSprite,startingPosition,newGameBody);

        gameObjects.add(newGameObject);

        shape.dispose();

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
