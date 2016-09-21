package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameWorld extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

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
		img = new Texture("badlogic.jpg");

		SetupCamera(screenWidth, screenHeight);
		SetupFPSCounter();
	}

	@Override
	public void render () {

		//Add the previous render time to our total elapsed time
		float deltaTime = Gdx.graphics.getDeltaTime();
		timeElapsed = timeElapsed + deltaTime;

		//Clear the screen with the color black;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//update the camera and apply it to the spritebatch
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		//////////////////////////////SPRITE BATCH START///////////////////////////////////////
		batch.begin();

		batch.draw(img, 0, 0);

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
}
