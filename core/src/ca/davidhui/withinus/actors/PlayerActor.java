package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.Utils;
import ca.davidhui.withinus.listeners.PlayerInputListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PlayerActor extends Actor {
    private final Texture playerTexture;
    private final Stage boundLevelStage;

    private int xDirection = 0;
    private int yDirection = 0;

    public PlayerActor(Texture img, Stage boundLevelStage){
        this.playerTexture = img;
        this.boundLevelStage = boundLevelStage;

        setBounds(getX(), getY(), playerTexture.getWidth(), playerTexture.getHeight());

        addListener(new PlayerInputListener(this));

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(playerTexture, getX(), getY());
    }


    public Texture getPlayerTexture() {
        return playerTexture;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(xDirection == -1){
            setX(getX() - Utils.getAdjustedMovementSpeed() * delta);

            // If player is past the padding, move camera too
            if(getX() < boundLevelStage.getCamera().position.x - boundLevelStage.getCamera().viewportWidth/2 + GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(-Utils.getAdjustedMovementSpeed() * delta, 0, 0);
            }
        }

        // Right movement
        if(xDirection == 1){
            setX(getX() + Utils.getAdjustedMovementSpeed() * delta);

            if(getX() > boundLevelStage.getCamera().position.x + boundLevelStage.getCamera().viewportWidth/2 - playerTexture.getWidth() - GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(Utils.getAdjustedMovementSpeed() * delta, 0, 0);
            }
        }

        // Up movement
        if(yDirection == 1){
            setY(getY() + Utils.getAdjustedMovementSpeed() * delta);

            if(getY() > boundLevelStage.getCamera().position.y + boundLevelStage.getCamera().viewportHeight/2 - playerTexture.getHeight() - GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(0,Utils.getAdjustedMovementSpeed() * delta,0);
            }
        }

        // Down movement
        if(yDirection == -1){
            setY(getY() - Utils.getAdjustedMovementSpeed() * delta);

            if(getY() < boundLevelStage.getCamera().position.y - boundLevelStage.getCamera().viewportHeight/2 + GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(0, -Utils.getAdjustedMovementSpeed() * delta, 0);
            }
        }

        boundLevelStage.getCamera().update();
    }

    public void setxDirection(int direction){
        this.xDirection = direction;
    }

    public void setyDirection(int direction){
        this.yDirection = direction;
    }

    public int getxDirection() {
        return xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }
}
