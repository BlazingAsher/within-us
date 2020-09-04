package ca.davidhui.withinus.screens;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.enums.GameState;
import ca.davidhui.withinus.enums.PlayerType;
import ca.davidhui.withinus.enums.TaskType;
import ca.davidhui.withinus.models.Task;
import ca.davidhui.withinus.stages.LevelStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.List;

public class LevelScreen implements Screen {

    final WithinUs game;

    private SpriteBatch spriteBatch;
    private OrthographicCamera levelCamera;

    private PlayerActor player;
    private Texture mapBk;
    private Music bgMusic;

    private LevelStage levelStage;
    private TiledMap levelMap;

    private OrthogonalTiledMapRenderer levelMapRenderer;

    private GameState gameState;

    public LevelScreen(final WithinUs game){
        this.game = game;

        spriteBatch = new SpriteBatch(); // global SpriteBatch for all stages and map
        levelCamera = new OrthographicCamera(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);

        initLevelMap();

        levelStage = new LevelStage(new ExtendViewport(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT, levelCamera), spriteBatch, getMapCollision(), getMapTasks());

        Gdx.input.setInputProcessor(levelStage);

        player = new PlayerActor(new Texture("badlogic.jpg"), levelStage, this, PlayerType.CREWMATE);
        levelStage.setSelfPlayer(player);

        mapBk = new Texture("images/testbk.png");

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ambient_piano.mp3"));
        bgMusic.setLooping(true);
        bgMusic.play();

        initLevelStage();

        this.gameState = GameState.RUNNING;

    }

    private void initLevelMap() {
        levelMap = this.game.getAssetManager().get("maps/testmap.tmx", TiledMap.class);
        levelMapRenderer = new OrthogonalTiledMapRenderer(levelMap, 1, this.spriteBatch);
        levelMapRenderer.setView(this.levelCamera);
    }

    private void initLevelStage() {
        levelStage.addPlayer(player);
        levelStage.addPlayer(new PlayerActor(new Texture("badlogic.jpg"), this.levelStage, this, PlayerType.CREWMATE)); // static actor to test camera/player movement (temporary)
        levelStage.setKeyboardFocus(player);
    }

    private List<Rectangle> getMapCollision() {
        MapLayer layer = levelMap.getLayers().get(3);
        List<Rectangle> temp = new ArrayList<>();

        for (RectangleMapObject rectangleObject : layer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            temp.add(rectangle);
        }
        return temp;
    }

    private List<Task> getMapTasks() {
        MapLayer layer = levelMap.getLayers().get(2);
        List<Task> temp = new ArrayList<>();

        for (MapObject object : layer.getObjects()){
            if(object instanceof RectangleMapObject){
                temp.add(new Task(TaskType.valueOf((String) object.getProperties().get("taskName")), ((RectangleMapObject) object).getRectangle()));
            }

        }
        return temp;
    }

    public TiledMap getLevelMap() {
        return this.levelMap;
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
        for(Task levelTask : levelStage.getMapTasks()){
            levelTask.getTaskPixMap().dispose();
        }
        levelStage.dispose();
        levelMapRenderer.dispose();
        levelMap.dispose();
        mapBk.dispose();
    }
}
