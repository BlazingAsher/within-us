package ca.davidhui.withinus.listeners;

import ca.davidhui.withinus.actors.PlayerActor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class PlayerInputListener extends InputListener {
    private final PlayerActor boundPlayer;

    public PlayerInputListener(PlayerActor boundPlayer){
        this.boundPlayer = boundPlayer;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if(keycode == Input.Keys.A){
            boundPlayer.setxDirection(-1);
        }
        if(keycode == Input.Keys.D){
            boundPlayer.setxDirection(1);
        }
        if(keycode == Input.Keys.W){
            boundPlayer.setyDirection(1);

        }
        if(keycode == Input.Keys.S){
            boundPlayer.setyDirection(-1);
        }
        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if(keycode == Input.Keys.A){
            // only stop moving if another key isn't held down
            if(boundPlayer.getxDirection() == -1){
                boundPlayer.setxDirection(0);
            }
        }
        if(keycode == Input.Keys.D){
            if(boundPlayer.getxDirection() == 1){
                boundPlayer.setxDirection(0);
            }
        }
        if(keycode == Input.Keys.W){
            if(boundPlayer.getyDirection() == 1){
                boundPlayer.setyDirection(0);
            }
        }
        if(keycode == Input.Keys.S){
            if(boundPlayer.getyDirection() == -1){
                boundPlayer.setyDirection(0);
            }
        }
        return true;
    }

    @Override
    public boolean keyTyped(InputEvent event, char character) {
        return super.keyTyped(event, character);
    }
}
