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

    public GameObject(World gameWorld, Sprite gameSprite, Vector2 startingPosition){

        objectSprite = gameSprite;
        SetupBodyDefinition(gameWorld, startingPosition, gameSprite);
        InitSpritePosition(objectBody.getPosition(), gameSprite);
    }

    private void InitSpritePosition(Vector2 startingPosition, Sprite gameSprite){
        gameSprite.setPosition(startingPosition.x, startingPosition.y);
    }

    private void SetupBodyDefinition(World gameWorld, Vector2 startingPosition, Sprite gameSprite){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startingPosition.x, startingPosition.y);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(gameSprite.getWidth() /2, gameSprite.getHeight() /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        objectBody = gameWorld.createBody(bodyDef);
        objectFixture = objectBody.createFixture(fixtureDef);

        shape.dispose();
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
