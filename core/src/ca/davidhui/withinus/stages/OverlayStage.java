package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.ui.ScreenTintActor;
import ca.davidhui.withinus.screens.LevelScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OverlayStage extends Stage {
    private WithinUs game;
    private LevelScreen boundScreen;

    private ScreenTintActor tintActor;
    public OverlayStage(Viewport viewport, WithinUs game, LevelScreen boundScreen, SpriteBatch spriteBatch){
        super(viewport, spriteBatch);
        this.game = game;
        this.boundScreen = boundScreen;

        this.tintActor = new ScreenTintActor();
        this.addActor(this.tintActor);
    }
    public ScreenTintActor getTintActor() {
        return this.tintActor;
    }
}
