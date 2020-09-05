package ca.davidhui.withinus.screens;

import ca.davidhui.withinus.WithinUs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen implements Screen {

    private final WithinUs game;

    private final Stage ui;

    public MainMenuScreen(final WithinUs game) {
        this.game = game;
        ui = new Stage();
        Label title = new Label("Within Us", game.skin, "title");
        title.setPosition(640 - title.getWidth() / 2, 100);
        ui.addActor(title);

        TextButton playButton = new TextButton("Play", game.skin);
        playButton.setPosition(640 - title.getWidth() / 2, 200);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new LevelScreen(game));
            }
        });
        ui.addActor(playButton);

        Gdx.input.setInputProcessor(ui);
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
