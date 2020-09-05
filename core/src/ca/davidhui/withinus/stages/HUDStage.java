package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.actors.ui.InteractButton;
import ca.davidhui.withinus.models.Interactable;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUDStage extends Stage {
    private final WithinUs game;
    private InteractButton useButton;
    private TextButton reportButton;
    private final PlayerActor playerActor;

    public HUDStage(Viewport viewport, WithinUs game, SpriteBatch batch, PlayerActor playerActor) {
        super(viewport, batch);
        this.game = game;
        this.playerActor = playerActor;
        this.initHUD();
    }

    private void initHUD() {
        reportButton = new TextButton("Report", game.skin, "round") {
            @Override
            public void act(float delta) {
                super.act(delta);
//                System.out.println("per frame");
            }
        };
        reportButton.setPosition(GameConstants.VIEWPORT_WIDTH - 200, 50);
        reportButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("reporting");
            }
        });
        this.addActor(reportButton);

        useButton = new InteractButton(game.skin, "round");
        useButton.setPosition(GameConstants.VIEWPORT_WIDTH - 200, 100);
        useButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("clicked use");
                playerActor.processInteract();
            }
        });
        this.addActor(useButton);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setUIStatusPerFrame();
    }

    private void setUIStatusPerFrame() {
        Interactable interactable = playerActor.getCurrentInteractableOverlap();
        useButton.setDisabled(interactable == null);

        PlayerActor deadPlayer = playerActor.getCurrentPlayerOverlap();
        reportButton.setDisabled(deadPlayer == null);
    }
}
