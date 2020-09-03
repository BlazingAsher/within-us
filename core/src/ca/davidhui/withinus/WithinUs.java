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
	private Music bgMusic;

	private OrthographicCamera camera;

	private Rectangle playerRect;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ambient_piano.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1080, 720);

		playerRect = new Rectangle();
		playerRect.x = 30;
		playerRect.y = 20;
		playerRect.width = 64;
		playerRect.height = 64;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Trying to get the camera to move instead of player (when near edge)
		camera.position.set(playerRect.x, playerRect.y, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		//batch.draw(img, playerRect.x, playerRect.y);
		batch.draw(img, 0, 0);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.A)) playerRect.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.D)) playerRect.x += 200 * Gdx.graphics.getDeltaTime();
		if(playerRect.x < 0) playerRect.x = 0;
		if(playerRect.x > 800 - 64) playerRect.x = 800 - 64;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
