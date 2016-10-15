package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    private float gravity = -2.50F;

    //Game loop should run at 60 ticks per second
    private float timeStep = 1.0f / 60.0f;
    private float accumulator = 0.0f;
    private int MAX_STEPS = 5;
    private float alpha = 0.0f;

    private float ticksPerSecondAccumulator = 0.0f;
    private float ticksPerSecond = 0.0f;
    private String tpsString = "Ticks Per Second: ";
    private String tpsValueString = "";

    private ArrayList<GameObject> gameObjects;
    private ArrayList<StaticGameObject> staticGameObjects;

    private boolean canControl = true;

    private GameObject myGameObject;

    public GameController (int width, int height){

        this.gameWorld = new World(new Vector2(0,gravity),true);

        gameSprites = new HashMap<String, Sprite>();
        gameObjects = new ArrayList<GameObject>();
        staticGameObjects = new ArrayList<StaticGameObject>();

        InitializeGameObjects(width, height);
    }

    public void InitializeGameObjects( int width, int height){
        CreateStaticGameObject(new Vector2(width /2,50),width,50);
        CreateStaticGameObject(new Vector2(width -50,height / 2),50,height);

        myGameObject = CreateGameObject(new Vector2(100,300),"GameSprite.png");
    }

    public void Step(float deltaTime){

        accumulator = accumulator + deltaTime;

        //calculate the number of logic steps we need to take based upon the amount of time that has passed
        int numberOfSteps = (int)Math.floor(accumulator / timeStep);

        //Remove the about-to-be-taken time steps from our accumulator
        if (numberOfSteps > 0){
            accumulator = accumulator - numberOfSteps * timeStep;
        }

        alpha = accumulator / timeStep;

        //Make sure we are not taking too many game steps
        numberOfSteps = Math.min(MAX_STEPS,numberOfSteps);

        //Step our physics engine
        for (int i = 0; i < numberOfSteps ; i++){
            this.ProcessInput();
            this.GameTick(timeStep);
            this.GetWorld().step(timeStep,6,2);
        }

        //Count the number of ticks per second
        ticksPerSecondAccumulator = ticksPerSecondAccumulator + deltaTime;
        ticksPerSecond = ticksPerSecond + numberOfSteps;

        //Each second, update the TPS String
        if (ticksPerSecondAccumulator > 1.0f){
            tpsValueString = Float.toString(ticksPerSecond);
            ticksPerSecondAccumulator = 0.0f;
            ticksPerSecond = 0.0f;
        }
    }

    private void ProcessInput(){
        if (Gdx.input.isTouched() && this.canControl){
            myGameObject.GetBody().applyLinearImpulse(2F, 2F,myGameObject.GetBody().getPosition().x,myGameObject.GetBody().getPosition().y,true);
            myGameObject.GetBody().applyAngularImpulse(-.07f,true);
            canControl = false;
        }
    }

    //Game logic goes here
    private void GameTick(float timeStep){
    }

    public ArrayList<GameObject> GetGameObjects(){
        return gameObjects;
    }

    public ArrayList<StaticGameObject> GetStaticGameObjects(){
        return staticGameObjects;
    }

    public GameObject CreateGameObject(Vector2 startingPosition, String spriteName){

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

        newGameBody = gameWorld.createBody(bodyDef);
        newGameFixture = newGameBody.createFixture(fixtureDef);

        newGameObject = new GameObject(gameSprite,startingPosition,newGameBody);

        gameObjects.add(newGameObject);

        shape.dispose();

        return newGameObject;

    }

    public void CreateStaticGameObject(Vector2 startingPosition, int width, int height){

        StaticGameObject newGameObject;

        Body staticGameBody;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(startingPosition.x / GameWorld.PIXELS_TO_METERS, startingPosition.y / GameWorld.PIXELS_TO_METERS);

        PolygonShape newShape = new PolygonShape();
        newShape.setAsBox(width/2 / GameWorld.PIXELS_TO_METERS,height/2 / GameWorld.PIXELS_TO_METERS);

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

    public String GetTicksPerSecondString(){
        return tpsString + tpsValueString;
    }

}
