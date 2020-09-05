package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.WithinUs;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUDStage extends Stage {
    private final WithinUs game;

    public HUDStage(Viewport viewport, WithinUs game, SpriteBatch batch) {
        super(viewport, batch);
        this.game = game;
        this.initHUD();
    }

    private void initHUD() {
        TextButton report = new TextButton("Report", game.skin, "round") {
            @Override
            public void act(float delta) {
                super.act(delta);
//                System.out.println("per frame");
            }
        };

        report.setPosition(GameConstants.VIEWPORT_WIDTH - 200, 50);
        report.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("clicked");
            }
        });
        this.addActor(report);
    }
}
