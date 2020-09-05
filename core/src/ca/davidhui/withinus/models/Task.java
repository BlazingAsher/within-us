package ca.davidhui.withinus.models;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.TaskActor;
import ca.davidhui.withinus.enums.TaskType;
import ca.davidhui.withinus.tasks.ClickButtonTask;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Task extends Interactable {
    public final TaskType taskType;

    private final Pixmap taskPixMap; // Using this for now to outline the task TODO: Change it to an Animation!
    private final TaskActor taskActor; // This is the Actor that will be added to the LEVEL STAGE
    private final TextureRegion taskTextureRegion;
    private final WithinUs game;

    private final TaskGroup uiGroup; // This is the Group that will be added to the UI STAGE

    private boolean completed = false;
    private boolean removed = false;

    public Task(TaskType taskType, Rectangle taskRectangle, WithinUs game) {
        super(taskRectangle);
        this.taskType = taskType;
        this.game = game;

        // Temporary task visualization
        this.taskPixMap = new Pixmap((int) Math.ceil(taskRectangle.width), (int) Math.ceil(taskRectangle.height), Pixmap.Format.RGBA8888);
        this.taskPixMap.setColor(Color.YELLOW);
        this.taskPixMap.fillRectangle(0, 0, (int) Math.ceil(taskRectangle.width), (int) Math.ceil(taskRectangle.height));
        this.taskTextureRegion = new TextureRegion(new Texture(this.taskPixMap));
        this.taskActor = new TaskActor(this);

        // Initialize the corresponding ui group for the UI Stage
        switch (taskType){
            case WATERFALL:
                uiGroup = new ClickButtonTask(this.game, this);
                break;
            default:
                uiGroup = new ClickButtonTask(this.game, this);
        }
    }

    public Pixmap getTaskPixMap() {
        return taskPixMap;
    }

    public TaskActor getTaskActor() {
        return taskActor;
    }

    public TextureRegion getTaskTextureRegion() {
        return taskTextureRegion;
    }

    public TaskGroup getUiGroup() {
        return uiGroup;
    }

    /**
     * Sets the Task as completed.
     */
    public void setComplete(){
        this.completed = true;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    /**
     * Gets whether the TaskActor has been removed from the LevelStage.
     * @return
     */
    public boolean getRemoved() {
        return this.removed;
    }

    /**
     * Sets the TaskActor as removed from the LevelStage.
     */
    public void setRemoved() {
        this.removed = true;
    }
}
