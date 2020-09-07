package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.actors.VentActor;
import ca.davidhui.withinus.models.Task;
import ca.davidhui.withinus.models.Vent;
import ca.davidhui.withinus.screens.LevelScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelStage extends Stage {
    private final List<Task> mapTasks;
    private final List<Rectangle> collisionRectangles;
    private final Map<Integer, Vent> mapVents;
    private List<PlayerActor> players;
    private PlayerActor selfPlayer;
    private LevelScreen boundScreen;

    public LevelStage(Viewport viewport, Batch spriteBatch, List<Rectangle> collisionRectangles, List<Task> mapTasks, Map<Integer, Vent> mapVents, LevelScreen boundScreen) {
        super(viewport, spriteBatch);
        this.collisionRectangles = collisionRectangles;
        this.mapTasks = mapTasks;
        this.mapVents = mapVents;
        this.boundScreen = boundScreen;

        for(Task levelTask : mapTasks){
            this.addActor(levelTask.getTaskActor());
        }

        for(Vent levelVent : mapVents.values()){
            this.addActor(levelVent.getVentActor());
        }

        this.players = new ArrayList<>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Check for tasks that have been completed and not removed
        for(Task levelTask : mapTasks){
            if(levelTask.isComplete() && !levelTask.getRemoved()){
                // Remove the actor and cached rectangle
                levelTask.getTaskActor().remove();
                this.boundScreen.getUiStage().close();
                levelTask.setRemoved();
            }
        }
    }

    public void addPlayer(PlayerActor player){
        this.players.add(player);
        this.addActor(player);
    }

    public void setSelfPlayer(PlayerActor player){
        this.selfPlayer = player;
    }

    public List<Task> getMapTasks() {
        return mapTasks;
    }

    public List<Rectangle> getCollisionRectangles() {
        return collisionRectangles;
    }

    public List<PlayerActor> getPlayers() {
        return players;
    }

    public PlayerActor getSelfPlayer() {
        return selfPlayer;
    }

    public LevelScreen getBoundScreen() {
        return boundScreen;
    }

    public Map<Integer, Vent> getMapVents() {
        return mapVents;
    }

}
