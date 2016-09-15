package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;

public class GameWorld extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private boolean loggingEnabled = true;

	private double timeStart;
	private double timeEnd;
	private java.lang.String fpsString;
	private double fpsCounter;
	private BitmapFont font;

	@Override
	public void create () {

		SetupFPSCounter();

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		WriteToLog("Create Method Finished");
	}

	@Override
	public void render () {

		timeStart = TimeUtils.millis() / 1000.0;
		fpsString = "FPS" + fpsCounter;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		font.draw(batch,fpsString,10.0f, 10.0f);
		batch.end();
	}

	//test comment
	//test another comment
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
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

	private void WriteToLog(String message){
		if (loggingEnabled){
			System.out.println(message);
		}
	}
}
