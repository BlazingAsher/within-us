package ca.davidhui.withinus.screens;

import ca.davidhui.withinus.WithinUs;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenuScreen implements Screen {

    private final WithinUs game;

    private final Stage ui;

    public MainMenuScreen(final WithinUs game) {
        this.game = game;
        ui = new Stage();
        Label title = new Label("Within Us", game.skin, "title");
        title.setSize(500, 500);
        title.setPosition(640 - title.getWidth() / 2, 100);
        ui.addActor(title);

        TextButton playButton = new TextButton("Play", game.skin);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
    ui.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
