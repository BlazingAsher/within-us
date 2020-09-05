package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.models.Task;
import ca.davidhui.withinus.screens.LevelScreen;
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
    private LevelScreen boundScreen;

    public LevelStage(Viewport viewport, Batch spriteBatch, List<Rectangle> collisionRectangles, List<Task> mapTasks, LevelScreen boundScreen) {
        super(viewport, spriteBatch);
        this.collisionRectangles = collisionRectangles;
        this.mapTasks = mapTasks;
        this.boundScreen = boundScreen;

        for(Task levelTask : mapTasks){
            this.addActor(levelTask.getTaskActor());
        }

        this.players = new ArrayList<>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for(Task levelTask : mapTasks){
            if(levelTask.completed && !levelTask.removed){
                // Remove the actor and cached rectangle
                levelTask.getTaskActor().remove();
                this.collisionRectangles.remove(levelTask.boundRectangle);
                this.boundScreen.getUiStage().close();
                levelTask.removed = true;
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
}
