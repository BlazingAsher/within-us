package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.ScreenTintActor;
import ca.davidhui.withinus.enums.GameState;
import ca.davidhui.withinus.screens.LevelScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UIStage extends Stage{
    private final WithinUs game;
    private final LevelScreen boundScreen;
    private ScreenTintActor tintActor;

    private Group currentView;
    public UIStage(Viewport viewport, WithinUs game, LevelScreen boundScreen, SpriteBatch batch){
        super(viewport, batch);
        this.game = game;
        this.boundScreen = boundScreen;

        this.tintActor = new ScreenTintActor();
        this.addActor(this.tintActor);
    }

    public void setView(Group newView){
        this.addActor(newView);
        this.currentView = newView;
    }

    public void close() {
        System.out.println("switching back to main stage");
        this.clear();
        this.addActor(this.tintActor);
        this.boundScreen.setGameState(GameState.RUNNING);
    }

    public ScreenTintActor getTintActor() {
        return this.tintActor;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if(keyCode == Input.Keys.ESCAPE){
            close();
        }

        return true;
    }
}
