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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld extends ApplicationAdapter {
	SpriteBatch batch;

	private GameController controller;

	private ShapeRenderer shapeRenderer;

	//Debug objects
	private Box2DDebugRenderer debugRenderer;
	private Matrix4 debugMatrix;

	private OrthographicCamera camera;
	private int screenWidth = 1920;
	private int screenHeight = 1080;
	public static float PIXELS_TO_METERS = 100.0f;

	private boolean monitorFPS =  true;
	private String fpsString;
	private double fpsCounter;
	private double timeElapsed;
	private BitmapFont font;


	@Override
	public void create () {

		batch = new SpriteBatch();

		shapeRenderer = new ShapeRenderer();
		debugRenderer = new Box2DDebugRenderer();

		SetupCamera(screenWidth, screenHeight);
		SetupFPSCounter();

		controller = new GameController(screenWidth,screenHeight);

	}

	@Override
	public void render () {

		//Add the previous render time to our total elapsed time
		float deltaTime = Gdx.graphics.getDeltaTime();


		timeElapsed = timeElapsed + deltaTime; //This is used to calculate frames per second

		////// Game Physics Section ////////

		controller.Step(deltaTime);

		////////////////////////////////////

		//Clear the screen with the color black;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//update the camera and apply it to the spritebatch
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		//////////////////////////////SPRITE BATCH START///////////////////////////////////////
		batch.begin();

		//debugMatrix = batch.getProjectionMatrix().cpy();
		//debugRenderer.render(controller.GetWorld(),debugMatrix);

		for (GameObject o : controller.GetGameObjects()){
			o.UpdateSpritePosition();
			batch.draw(o.GetSprite(),
					o.GetSprite().getX() - o.GetSprite().getWidth()/2,
					o.GetSprite().getY() -o.GetSprite().getHeight()/2,
					o.GetSprite().getOriginX(),
					o.GetSprite().getOriginY(),
					o.GetSprite().getWidth(),
					o.GetSprite().getHeight(),
					o.GetSprite().getScaleX(),
					o.GetSprite().getScaleY(),
					o.GetSprite().getRotation());
		}

		//Logging section
		if (monitorFPS){
			font.draw(batch,fpsString,50, camera.viewportHeight - 50);
			font.draw(batch,controller.GetTicksPerSecondString(),50, camera.viewportHeight - 100);
		}

		batch.end();
		//////////////////////////////SPRITE BATCH END/////////////////////////////////////////

		//////////////////////////////SHAPE RENDERER START///////////////////////////////////////

		shapeRenderer.setAutoShapeType(true);

		shapeRenderer.begin();
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(camera.combined);

		for (StaticGameObject s : controller.GetStaticGameObjects()){
			shapeRenderer.rect(s.GetPosition().x * GameWorld.PIXELS_TO_METERS - s.GetWidth()/2 ,
					s.GetPosition().y * GameWorld.PIXELS_TO_METERS - s.GetHeight() /2 ,
					s.GetWidth(),s.GetHeight());
		}

		shapeRenderer.end();
		//////////////////////////////SHAPE RENDERER BATCH END/////////////////////////////////////////

		//Update the FPS counter
		if (monitorFPS){
			UpdateFPSCounter();
		}
	}

	//test comment
	@Override
	public void dispose () {
		batch.dispose();
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
