package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.models.Task;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class LevelStage extends Stage {
    private final List<Task> mapTasks;
    private final List<Rectangle> collisionRectangles;
    private List<PlayerActor> players;
    private PlayerActor selfPlayer;

    public LevelStage(Viewport viewport, Batch spriteBatch, List<Rectangle> collisionRectangles, List<Task> mapTasks) {
        super(viewport, spriteBatch);
        this.collisionRectangles = collisionRectangles;
        this.mapTasks = mapTasks;

        for(Task levelTask : mapTasks){
            this.addActor(levelTask.getTaskActor());
        }

        this.players = new ArrayList<>();
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
}
