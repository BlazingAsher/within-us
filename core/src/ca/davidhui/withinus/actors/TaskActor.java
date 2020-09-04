package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.models.Task;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TaskActor extends Actor {
    private Task boundTask;

    public TaskActor(Task boundTask){
        this.boundTask = boundTask;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.boundTask.getTaskTextureRegion(), this.boundTask.taskRectangle.x, this.boundTask.taskRectangle.y, this.boundTask.taskRectangle.width, this.boundTask.taskRectangle.height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
