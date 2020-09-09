package ca.davidhui.withinus.screens;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.enums.GameResult;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VictoryDefeatScreen implements Screen {

    private final WithinUs game;

    private final Stage ui;

    public VictoryDefeatScreen(final WithinUs game, GameResult gameResult) {
        this.game = game;
        ui = new Stage();
        Label title = new Label(gameResult.toString(), game.skin, "title");
        title.setPosition(640 - title.getWidth() / 2, 100);
        ui.addActor(title);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
