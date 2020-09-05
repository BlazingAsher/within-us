package ca.davidhui.withinus.models;

import ca.davidhui.withinus.WithinUs;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TaskGroup extends Group {
    protected final WithinUs game;
    protected final Task boundTask;

    public TaskGroup(WithinUs game, Task boundTask){
        this.game = game;
        this.boundTask = boundTask;
    }
}
