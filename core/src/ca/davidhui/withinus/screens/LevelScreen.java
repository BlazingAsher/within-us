package ca.davidhui.withinus.screens;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.actors.VentActor;
import ca.davidhui.withinus.enums.GameState;
import ca.davidhui.withinus.enums.PlayerType;
import ca.davidhui.withinus.enums.TaskType;
import ca.davidhui.withinus.models.Interactable;
import ca.davidhui.withinus.models.Task;
import ca.davidhui.withinus.models.Vent;
import ca.davidhui.withinus.stages.HUDStage;
import ca.davidhui.withinus.stages.LevelStage;
import ca.davidhui.withinus.stages.UIStage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelScreen implements Screen {

    final WithinUs game;

    private SpriteBatch spriteBatch;
    private OrthographicCamera levelCamera;

    private PlayerActor player;
    private Texture mapBk;
    private Music bgMusic;

    private LevelStage levelStage;
    private UIStage uiStage;
    private TiledMap levelMap;
    private MapProperties levelMapProperties;
    private int mapWidth;
    private int mapHeight;

    private OrthogonalTiledMapRenderer levelMapRenderer;

    private GameState gameState;

    private HUDStage hudStage;

    private final InputMultiplexer runningInput = new InputMultiplexer();

    public LevelScreen(final WithinUs game) {
        this.game = game;

        spriteBatch = new SpriteBatch(); // global SpriteBatch for all stages and map
        levelCamera = new OrthographicCamera(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);

        initLevelMap();

        levelStage = new LevelStage(new ExtendViewport(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT, levelCamera), spriteBatch, getMapCollision(), getMapTasks(), getMapVents(), this);

        Gdx.input.setInputProcessor(levelStage);

        player = new PlayerActor(new Texture("badlogic.jpg"), levelStage, this, PlayerType.CREWMATE);
        levelStage.setSelfPlayer(player);

//        mapBk = new Texture("images/testbk.png");

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ambient_piano.mp3"));
        bgMusic.setLooping(true);
        bgMusic.play();

        initLevelStage();
        initUIStage();
        initHUDStage();

        runningInput.addProcessor(hudStage);
        runningInput.addProcessor(levelStage);
        Gdx.input.setInputProcessor(runningInput);

        this.gameState = GameState.RUNNING;

    }

    private void initLevelMap() {
        levelMap = this.game.getAssetManager().get("maps/testmap.tmx", TiledMap.class);
        levelMapRenderer = new OrthogonalTiledMapRenderer(levelMap, 1, this.spriteBatch);
        levelMapRenderer.setView(this.levelCamera);

        this.levelMapProperties = this.levelMap.getProperties();
        this.mapWidth = this.getLevelMapProperties().get("width", Integer.class) * this.getLevelMapProperties().get("tilewidth", Integer.class);
        this.mapHeight = this.getLevelMapProperties().get("height", Integer.class) * this.getLevelMapProperties().get("tileheight", Integer.class);

    }

    private void initLevelStage() {
        levelStage.addPlayer(player);
        levelStage.addPlayer(new PlayerActor(new Texture("badlogic.jpg"), this.levelStage, this, PlayerType.CREWMATE)); // static actor to test camera/player movement (temporary)
        levelStage.setKeyboardFocus(player);
    }

    private void initUIStage() {
        uiStage = new UIStage(new FitViewport(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT), this.game, this, this.spriteBatch);
    }

    private void initHUDStage() {
        hudStage = new HUDStage(new StretchViewport(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT), game, this.spriteBatch, player);
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

        for (MapObject object : layer.getObjects()) {
            if (object instanceof RectangleMapObject && object.getProperties().get("type").equals("TASK")) {

                temp.add(
                        new Task(
                                TaskType.valueOf(object.getProperties().get("taskName", String.class)),
                                ((RectangleMapObject) object).getRectangle(), this.game
                        )
                );
            }

        }
        return temp;
    }

    private Map<Integer, Vent> getMapVents() {
        MapLayer layer = levelMap.getLayers().get(2);
        Map<Integer, Vent> temp = new HashMap<>();

        for (MapObject object : layer.getObjects()) {
            if (object instanceof RectangleMapObject && object.getProperties().get("type", String.class).equals("VENT")) {
                //temp.add(new Task(TaskType.valueOf((String) object.getProperties().get("taskName")), ((RectangleMapObject) object).getRectangle(), this.game));
                Integer ventID = object.getProperties().get("id", Integer.class);
                temp.put(ventID, new Vent(((RectangleMapObject) object).getRectangle(), ventID));
                // TODO: Build the vent network
            }

        }
        return temp;
    }

    public TiledMap getLevelMap() {
        return this.levelMap;
    }

    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    public UIStage getUiStage() {
        return uiStage;
    }

    public HUDStage getHudStage() {
        return hudStage;
    }

    public MapProperties getLevelMapProperties() {
        return levelMapProperties;
    }

    public int getMapWidth() {
        System.out.println(this.mapWidth);
        return this.mapWidth;
    }

    public int getMapHeight() {
        System.out.println(this.mapHeight);
        return this.mapHeight;
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

        hudStage.act(Gdx.graphics.getDeltaTime());
        hudStage.draw();

        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();

        if (this.gameState == GameState.RUNNING) {
            Gdx.input.setInputProcessor(runningInput);
        } else if (this.gameState == GameState.VOTING || this.gameState == GameState.DOING_TASK) {
            if(this.levelStage.getSelfPlayer() != null){
                this.levelStage.getSelfPlayer().stopMovement();
            }
            Gdx.input.setInputProcessor(uiStage);
        }
    }

    @Override
    public void resize(int width, int height) {
        levelStage.getViewport().update(width, height, false);
        levelStage.getCamera().position.set(levelStage.getSelfPlayer().getX(), levelStage.getSelfPlayer().getY(), 0);
        uiStage.getViewport().update(width, height, true);
        hudStage.getViewport().update(width, height, true);
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
        for (Task levelTask : levelStage.getMapTasks()) {
            levelTask.getTaskPixMap().dispose();
        }
        levelStage.dispose();
        uiStage.dispose();
        hudStage.dispose();
        levelMapRenderer.dispose();
        levelMap.dispose();
        mapBk.dispose();
    }
}
