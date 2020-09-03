package ca.davidhui.withinus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class WithinUs extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture mapBk;
	private Music bgMusic;

	private OrthographicCamera camera;

	private Rectangle playerRect;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		mapBk = new Texture("images/testbk.png");

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ambient_piano.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1080, 720);

		playerRect = new Rectangle();
		playerRect.x = 30;
		playerRect.y = 20;
		playerRect.width = img.getWidth();
		playerRect.height = img.getHeight();
	}

	public int getAdjustedMovementSpeed(){
		// TODO: implement setting speed multiplier
		return GameConstants.BASE_MOVEMENT_SPEED;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		System.out.println(camera.position.x);

		// Left movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			playerRect.x -= getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime();

			// If player is past the padding, move camera too
			if(playerRect.x < camera.position.x - camera.viewportWidth/2 + GameConstants.CAMERA_PLAYER_PADDING){
				camera.translate(-getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime(), 0, 0);
			}
		}

		// Right movement
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			playerRect.x += getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime();

			if(playerRect.x > camera.position.x + camera.viewportWidth/2 - playerRect.width - GameConstants.CAMERA_PLAYER_PADDING){
				camera.translate(getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime(), 0, 0);
			}
		}

		// Up movement
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			playerRect.y += getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime();

			if(playerRect.y > camera.position.y + camera.viewportHeight/2 - playerRect.height - GameConstants.CAMERA_PLAYER_PADDING){
				camera.translate(0,getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime(),0);
			}
		}

		// Down movement
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			playerRect.y -= getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime();

			if(playerRect.y < camera.position.y - camera.viewportHeight/2 + GameConstants.CAMERA_PLAYER_PADDING){
				camera.translate(0, -getAdjustedMovementSpeed() * Gdx.graphics.getDeltaTime(), 0);
			}
		}

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(mapBk, 0, 0);
		batch.draw(img, playerRect.x, playerRect.y);
		batch.draw(img, 0, 0);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		mapBk.dispose();
	}
}
