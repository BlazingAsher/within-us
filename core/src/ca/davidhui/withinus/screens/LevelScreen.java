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
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

public class LevelScreen implements Screen {

    final WithinUs game;

    OrthographicCamera camera;

    private PlayerActor player;
    private Texture mapBk;
    private Music bgMusic;

    private Stage levelStage;
    private TiledMap levelMap;

    private OrthogonalTiledMapRenderer levelMapRenderer;
    private ArrayList<Rectangle> collisionRectangles;

    public LevelScreen(final WithinUs game){
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera(1280, 720);

        levelStage = new LevelStage(new ExtendViewport(1280, 720, camera));
        System.out.println(levelStage.getCamera() == camera);
        Gdx.input.setInputProcessor(levelStage);

        player = new PlayerActor(new Texture("badlogic.jpg"), levelStage, this);

        mapBk = new Texture("images/testbk.png");

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ambient_piano.mp3"));
        bgMusic.setLooping(true);
        bgMusic.play();

        initLevelStage();
        setupMapCollision();
        setupTasks();


    }

    private void initLevelStage() {
        levelStage.addActor(player);
        levelStage.addActor(new PlayerActor(new Texture("badlogic.jpg"), levelStage, this)); // static actor to test camera/player movement (temporary)
        levelStage.setKeyboardFocus(player);

        levelMap = this.game.getAssetManager().get("maps/testmap.tmx", TiledMap.class);
        levelMapRenderer = new OrthogonalTiledMapRenderer(levelMap, 1, levelStage.getBatch());
        levelMapRenderer.setView((OrthographicCamera) levelStage.getCamera());
    }

    private void setupMapCollision() {
        MapLayer layer = levelMap.getLayers().get(3);
        this.collisionRectangles = new ArrayList<>();

        for (RectangleMapObject rectangleObject : layer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            this.collisionRectangles.add(rectangle);
        }
    }

    private void setupTasks() {
        MapLayer layer = levelMap.getLayers().get(2);
        for (MapObject object : layer.getObjects()){
            System.out.println(object.getProperties().get("taskName"));

        }
    }

    public TiledMap getLevelMap() {
        return this.levelMap;
    }

    public ArrayList<Rectangle> getCollisionRectangles() {
        return collisionRectangles;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        levelMapRenderer.setView((OrthographicCamera) levelStage.getCamera());
        levelMapRenderer.render();

        levelStage.act(Gdx.graphics.getDeltaTime());
        levelStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        levelStage.getViewport().update(width, height, true);
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
