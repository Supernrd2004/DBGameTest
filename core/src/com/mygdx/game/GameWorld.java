package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	World gameWorld;

	GameObject testGameObject;

	private OrthographicCamera camera;
	private int screenWidth = 1920;
	private int screenHeight = 1080;

	private boolean monitorFPS =  true;
	private String fpsString;
	private double fpsCounter;
	private double timeElapsed;
	private BitmapFont font;

	@Override
	public void create () {

		batch = new SpriteBatch();
		SetupCamera(screenWidth, screenHeight);
		SetupFPSCounter();
		InitGameWorld();
		InitGameObjects();
	}

	@Override
	public void render () {

		//Add the previous render time to our total elapsed time
		float deltaTime = Gdx.graphics.getDeltaTime();
		timeElapsed = timeElapsed + deltaTime;

		////// Game Physics Section ////////

		gameWorld.step(deltaTime,6,2);

		///////////////////////////////////

		/////// Object Update Section //////

		//For each game object
		testGameObject.UpdateSpritePosition();
		////////////////////////////////////


		//Clear the screen with the color black;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//update the camera and apply it to the spritebatch
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		//////////////////////////////SPRITE BATCH START///////////////////////////////////////
		batch.begin();

		batch.draw(testGameObject.GetSprite().getTexture(), testGameObject.GetSprite().getX(), testGameObject.GetSprite().getY());

		//Logging section
		if (monitorFPS){
			font.draw(batch,fpsString,50, camera.viewportHeight - 50);
		}

		batch.end();
		//////////////////////////////SPRITE BATCH END/////////////////////////////////////////

		//Update the FPS counter
		if (monitorFPS){
			UpdateFPSCounter();
		}
	}

	//test comment
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	//Initialize components needed for FPS counter
	private void SetupFPSCounter(){
		fpsString = "FPS: ";
		fpsCounter = 0.0;
		timeElapsed = 0.0;
		font = new BitmapFont();
		font.setColor(Color.GREEN);
		font.getData().setScale(4.0f);
	}

	//Update the FPS counter with each render step
	private void UpdateFPSCounter(){
		fpsCounter = fpsCounter + 1;
		if (timeElapsed > 1.0f){
			fpsString = "FPS: " + Double.toString(fpsCounter);
			fpsCounter = 0.0;
			timeElapsed = 0.0;
		}
	}

	//Setup the Orthographic camera
	private void SetupCamera(int width, int height){
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
	}


	private void InitGameWorld(){

		gameWorld = new World(new Vector2(0,-98f),true);

	}

	private void InitGameObjects(){

		img = new Texture("badlogic.jpg");
		Sprite testGameSprite = new Sprite(img);
		testGameObject = new GameObject(gameWorld,testGameSprite,new Vector2(Gdx.graphics.getWidth() /2 ,Gdx.graphics.getHeight() /2));
	}
}
