package ca.davidhui.withinus.tasks;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.models.Task;
import ca.davidhui.withinus.models.TaskGroup;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ClickButtonTask extends TaskGroup {
    private final WithinUs game;
    private final Task boundTask;

    public ClickButtonTask(WithinUs game, final Task boundTask) {
        this.game = game;
        this.boundTask = boundTask;
        this.setBounds(0, 0, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(x);
                System.out.println(y);
                System.out.println("Yay! Completed!");
                boundTask.setComplete();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        Texture mainTexture = this.game.getAssetManager().get("images/ClickButtonTask.png", Texture.class);
        batch.draw(mainTexture, 0, 0);

    }
}
