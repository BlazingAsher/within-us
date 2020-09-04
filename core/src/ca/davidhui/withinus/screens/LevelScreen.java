package ca.davidhui.withinus.screens;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.stages.LevelStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LevelScreen implements Screen {

    final WithinUs game;

    OrthographicCamera camera;

    private PlayerActor player;
    private Texture mapBk;
    private Music bgMusic;

    private Stage levelStage;

    public LevelScreen(final WithinUs game){
        this.game = game;

        levelStage = new LevelStage();
        Gdx.input.setInputProcessor(levelStage);

//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 1080, 720);

        player = new PlayerActor(new Texture("badlogic.jpg"), levelStage);

        mapBk = new Texture("images/testbk.png");

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ambient_piano.mp3"));
        bgMusic.setLooping(true);
        bgMusic.play();

        initLevelStage();

    }

    private void initLevelStage() {
        levelStage.addActor(player);
        levelStage.addActor(new PlayerActor(new Texture("badlogic.jpg"), levelStage)); // static actor to test camera/player movement (temporary)
        levelStage.setKeyboardFocus(player);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        levelStage.act(Gdx.graphics.getDeltaTime());
        levelStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        player.dispose();
        mapBk.dispose();
    }
}
