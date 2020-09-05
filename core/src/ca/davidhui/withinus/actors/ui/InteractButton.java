package ca.davidhui.withinus.actors.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class InteractButton extends TextButton {
    public InteractButton(Skin skin, String styleName) {
        super("Use", skin, styleName);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
