package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameWorld extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture img2;

	private boolean loggingEnabled = true;

	private OrthographicCamera camera;
	int width = 1920;
	int height = 1080;

	float objPosX = 0;
	float objPosY = 0;
	float objDirection = 5.0f;

	private double timeStart;
	private double timeEnd;
	private java.lang.String fpsString;
	private double fpsCounter;
	private BitmapFont font;
	private FPSLogger fpsLog;
	private int framesPerSecondLabel;

	@Override
	public void create () {

		SetupFPSCounter();
		SetupCamera();

		fpsLog = new FPSLogger();

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		img2 = new Texture("Jellyfish.jpg");
		WriteToLog("Create Method Finished");
	}

	@Override
	public void render () {

		timeStart = TimeUtils.millis() / 1000.0;
		fpsString = "FPS" + fpsCounter;

		fpsLog.log();
		framesPerSecondLabel = Gdx.graphics.getFramesPerSecond();

		Integer.toString(framesPerSecondLabel);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(img,objPosX, objPosY);
		batch.draw(img2, 400, 400);
		font.draw(batch,"FPS: " + Integer.toString(framesPerSecondLabel),50, camera.viewportHeight - 50);
		batch.end();

		objPosX += objDirection;
		if (objPosX + img.getWidth() > camera.viewportWidth){
			objDirection = -objDirection;
		} else if (objPosX < 0){
			objDirection = -objDirection;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		img2.dispose();
	}

	private void SetupFPSCounter(){
		timeEnd = 0L;
		timeStart = 0L;
		fpsString = "FPS:";
		fpsCounter = 0;

		font = new BitmapFont();
		font.setColor(Color.GREEN);
		font.getData().setScale(4.0f);
		WriteToLog("FPS Counter Initialized");
	}

	private void SetupCamera(){

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
	}

	private void WriteToLog(String message){
		if (loggingEnabled){
			System.out.println(message);
		}
	}
}
