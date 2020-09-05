package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.Utils;
import ca.davidhui.withinus.enums.GameState;
import ca.davidhui.withinus.enums.PlayerState;
import ca.davidhui.withinus.enums.PlayerType;
import ca.davidhui.withinus.listeners.PlayerInputListener;
import ca.davidhui.withinus.models.Interactable;
import ca.davidhui.withinus.models.Task;
import ca.davidhui.withinus.screens.LevelScreen;
import ca.davidhui.withinus.stages.LevelStage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerActor extends Actor {
    private final Texture playerTexture; // TODO: Change this to an animation!
    private final LevelStage boundLevelStage;
    private final LevelScreen boundLevelScreen;

    private PlayerState playerState;
    private PlayerType playerType;

    private Interactable currentInteractableOverlap; // Stores the current actor

    private int xDirection = 0;
    private int yDirection = 0;

    public PlayerActor(Texture img, LevelStage boundLevelStage, LevelScreen boundLevelScreen, PlayerType playerType){
        this.playerTexture = img;
        this.boundLevelStage = boundLevelStage;
        this.boundLevelScreen = boundLevelScreen;

        this.playerType = playerType;
        this.playerState = PlayerState.ALIVE;

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

    public Rectangle getRectangle() {
        return new Rectangle(this.getX(), this.getY(), this.playerTexture.getWidth(), this.playerTexture.getHeight());
    }

    private float movePlayer(float desiredAmount, boolean isX){
        float oldComponent = isX ? getX() : getY(); // store the starting x or y value
        int direction = isX ? xDirection : yDirection;

        desiredAmount = Math.abs(desiredAmount);

        // TODO: This might be a bit imprecise going up by 1
        for(int i = 0;i<=desiredAmount;i++){

            // go one world unit in the desired direction
            if(isX){
                setX(getX() + direction);
            }
            else {
                setY(getY()+ direction);
            }

            // check if we collided with anything
            for (Rectangle collisionRectangle : boundLevelStage.getCollisionRectangles()) {
                // We did, so move back and report the amount we moved
                if (collisionRectangle.overlaps(getRectangle())) {
                    if (isX) {
                        setX(getX() - direction);
                    } else {
                        setY(getY() - direction);
                    }
                    return (i - 1) * direction;
                }
            }

        }

        // If we can move most of the way there, just move the rest (will result in some overlapping)
        if(isX){
            setX(oldComponent + desiredAmount*direction);
        }
        else {
            setY(oldComponent + desiredAmount*direction);
        }

        return desiredAmount*direction;

    }

    public void processInteract() {
        if(this.currentInteractableOverlap == null){
            return;
        }

        if(this.currentInteractableOverlap instanceof Task){
            Task selectedTask = (Task) this.currentInteractableOverlap;
            System.out.println("interacting with " + selectedTask.taskType);
            this.boundLevelScreen.setGameState(GameState.DOING_TASK);
            this.boundLevelScreen.getUiStage().setView(selectedTask.getUiGroup());
        }
    }

    private float movePlayerX(float desiredAmount) {
        return movePlayer(desiredAmount, true);
    }

    private float movePlayerY(float desiredAmount) {
        return movePlayer(desiredAmount, false);
    }

    /**
     * Moves the player as long as x/yDirection is setee
     * @param delta time since last tick
     */
    private void processMovement(float delta) {
        if(xDirection == -1){
            float permittedXMovement = movePlayerX(Utils.getAdjustedMovementSpeed() * delta);

            // If player is past the padding, move camera too
            if(getX() < boundLevelStage.getCamera().position.x - boundLevelStage.getCamera().viewportWidth/2 + GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(permittedXMovement, 0, 0);
            }
        }

        // Right movement
        if(xDirection == 1){
            float permittedXMovement = movePlayerX(Utils.getAdjustedMovementSpeed() * delta);

            if(getX() > boundLevelStage.getCamera().position.x + boundLevelStage.getCamera().viewportWidth/2 - playerTexture.getWidth() - GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(permittedXMovement, 0, 0);
            }
        }

        // Up movement
        if(yDirection == 1){
            float permittedYMovement = movePlayerY(Utils.getAdjustedMovementSpeed() * delta);

            if(getY() > boundLevelStage.getCamera().position.y + boundLevelStage.getCamera().viewportHeight/2 - playerTexture.getHeight() - GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(0,permittedYMovement,0);
            }
        }

        // Down movement
        if(yDirection == -1){
            float permittedYMovement = movePlayerY(Utils.getAdjustedMovementSpeed() * delta);

            if(getY() < boundLevelStage.getCamera().position.y - boundLevelStage.getCamera().viewportHeight/2 + GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(0, permittedYMovement, 0);
            }
        }

        boundLevelStage.getCamera().update();
    }

    private void checkTaskCollision() {
        for(Task levelTask : boundLevelStage.getMapTasks()){
            if(getRectangle().overlaps(levelTask.boundRectangle)){
                //System.out.println("task!");
                this.currentInteractableOverlap = levelTask;

            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        processMovement(delta);
        checkTaskCollision();
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
