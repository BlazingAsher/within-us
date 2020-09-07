package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.Utils;
import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.enums.GameState;
import ca.davidhui.withinus.enums.PlayerState;
import ca.davidhui.withinus.enums.PlayerType;
import ca.davidhui.withinus.listeners.PlayerInputListener;
import ca.davidhui.withinus.models.Interactable;
import ca.davidhui.withinus.models.Task;
import ca.davidhui.withinus.models.Vent;
import ca.davidhui.withinus.screens.LevelScreen;
import ca.davidhui.withinus.stages.LevelStage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.HashSet;

public class PlayerActor extends Actor {
    private final Texture playerTexture; // TODO: Change this to an animation!
    private final LevelStage boundLevelStage;
    private final LevelScreen boundLevelScreen;

    private PlayerState playerState;
    private PlayerType playerType;

    private Interactable currentInteractableOverlap; // Stores the current actor
    private PlayerActor currentPlayerOverlap;
    private PlayerActor currentKillableOverlap;

    private HashSet<VentArrowActor> boundVentArrowActors;

    private int xDirection = 0;
    private int yDirection = 0;

    private WithinUs game;

    public PlayerActor(Texture img, LevelStage boundLevelStage, LevelScreen boundLevelScreen, PlayerType playerType, WithinUs game) {
        this.game = game;
        this.playerTexture = img;
        this.boundLevelStage = boundLevelStage;
        this.boundLevelScreen = boundLevelScreen;

        this.playerType = playerType;
        this.playerState = PlayerState.DEAD;

        setBounds(getX(), getY(), playerTexture.getWidth(), playerTexture.getHeight());

        addListener(new PlayerInputListener(this));

        if(playerType == PlayerType.IMPOSTOR){
            this.boundVentArrowActors = new HashSet<>();
        }

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

    private float movePlayer(float desiredAmount, boolean isX) {
        float oldComponent = isX ? getX() : getY(); // store the starting x or y value
        int direction = isX ? xDirection : yDirection;

        desiredAmount = Math.abs(desiredAmount);
        //System.out.println("hello");

        // TODO: This might be a bit imprecise going up by 1
        for (int i = 0; i <= desiredAmount; i++) {

            // go one world unit in the desired direction
            if (isX) {
                setX(getX() + direction);
            } else {
                setY(getY() + direction);
            }

            // check if we collided with anything
            // only check if player is alive
            if(this.playerState == PlayerState.ALIVE){
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


            // make sure player is in map bounds
            if (isX) {
                if(getX() < 0 || getX() > this.boundLevelStage.getBoundScreen().getMapWidth()){
                    setX(getX() - direction);
                    return (i - 1) * direction;
                }

            } else {
                if(getY() < 0 || getY() > this.boundLevelStage.getBoundScreen().getMapHeight()){
                    setY(getY() - direction);
                    return (i - 1) * direction;
                }

            }

        }

        // If we can move most of the way there, just move the rest (will result in some overlapping)
        if (isX) {
            setX(oldComponent + desiredAmount * direction);
        } else {
            setY(oldComponent + desiredAmount * direction);
        }

        return desiredAmount * direction;

    }

    public void processInteract() {
        if (this.currentInteractableOverlap == null) {
            return;
        }

        if (this.currentInteractableOverlap instanceof Task) {
            Task selectedTask = (Task) this.currentInteractableOverlap;
            System.out.println("interacting with " + selectedTask.taskType);
            this.boundLevelScreen.setGameState(GameState.DOING_TASK);
            this.boundLevelScreen.getUiStage().setView(selectedTask.getUiGroup());
        }
        else if(this.currentInteractableOverlap instanceof Vent){
            Vent selectedVent = (Vent) this.currentInteractableOverlap;
            System.out.println("interacting with " + selectedVent);
            drawVentsUI(selectedVent);
        }
    }

    public void drawVentsUI(Vent baseVent) {
//        System.out.println(Arrays.toString(baseVent.nextNodes));
        Vector2 baseVentRectCenter = new Vector2();
        baseVent.boundRectangle.getCenter(baseVentRectCenter);
//        System.out.println("current center");
//        System.out.println(baseVentRectCenter);

        for(int nextNodeID : baseVent.nextNodes){
//            System.out.println("next vent-------------");
//            System.out.println(this.boundLevelStage.getMapVents().get(nextNodeID));
            Vent potentialNext = this.boundLevelStage.getMapVents().get(nextNodeID);
            Vector2 potentialNextRectCenter = new Vector2();
            potentialNext.boundRectangle.getCenter(potentialNextRectCenter);
//            System.out.println("next center");
//            System.out.println(potentialNextRectCenter);

            double degFromOriginVent = Math.toDegrees(Math.atan2(potentialNextRectCenter.y - baseVentRectCenter.y, potentialNextRectCenter.x - baseVentRectCenter.x));

            Vector2 nextPointerBounds = new Vector2();
            nextPointerBounds.x = (float) (baseVentRectCenter.x + Math.cos(Math.toRadians(degFromOriginVent))*50);
            nextPointerBounds.y = (float) (baseVentRectCenter.y + Math.sin(Math.toRadians(degFromOriginVent))*50);
//            System.out.println("pos degrees");
//            System.out.println(degFromOriginVent);
//            System.out.println("pointer origin");
//            System.out.println(nextPointerBounds);
            VentArrowActor nextPointer = new VentArrowActor(this, this.game, nextPointerBounds.x, nextPointerBounds.y);
            nextPointer.moveToCenter();


            nextPointer.rotateBy((float) degFromOriginVent);
            this.boundLevelStage.addActor(nextPointer);
            this.boundVentArrowActors.add(nextPointer);
//            System.out.println("------------------------");

        }
    }

    public void clearVentArrows() {
        for(VentArrowActor temp : this.boundVentArrowActors){
            temp.remove();
        }
        this.boundVentArrowActors.clear();
    }

    public void processKill() {
        if(this.currentKillableOverlap == null || this.playerType != PlayerType.IMPOSTOR){
            return;
        }

        this.currentKillableOverlap.beKilled();
    }

    public void beKilled() {
        this.playerState = PlayerState.DEAD;
    }

    private float movePlayerX(float desiredAmount) {
        return movePlayer(desiredAmount, true);
    }

    private float movePlayerY(float desiredAmount) {
        return movePlayer(desiredAmount, false);
    }

    /**
     * Moves the player as long as x/yDirection is set
     *
     * @param delta time since last tick
     */
    private void processMovement(float delta) {
        if (xDirection == -1) {
            float permittedXMovement = movePlayerX(Utils.getAdjustedMovementSpeed() * delta);

            // If player is past the padding, move camera too
            if (getX() < boundLevelStage.getCamera().position.x - boundLevelStage.getCamera().viewportWidth / 2 + GameConstants.CAMERA_PLAYER_PADDINGX) {
                boundLevelStage.getCamera().translate(permittedXMovement, 0, 0);
            }
        }

        // Right movement
        if (xDirection == 1) {
            float permittedXMovement = movePlayerX(Utils.getAdjustedMovementSpeed() * delta);

            if (getX() > boundLevelStage.getCamera().position.x + boundLevelStage.getCamera().viewportWidth / 2 - playerTexture.getWidth() - GameConstants.CAMERA_PLAYER_PADDINGX) {
                boundLevelStage.getCamera().translate(permittedXMovement, 0, 0);
            }
        }

        // Up movement
        if (yDirection == 1) {
            float permittedYMovement = movePlayerY(Utils.getAdjustedMovementSpeed() * delta);

            if (getY() > boundLevelStage.getCamera().position.y + boundLevelStage.getCamera().viewportHeight / 2 - playerTexture.getHeight() - GameConstants.CAMERA_PLAYER_PADDINGY) {
                boundLevelStage.getCamera().translate(0, permittedYMovement, 0);
            }
        }

        // Down movement
        if (yDirection == -1) {
            float permittedYMovement = movePlayerY(Utils.getAdjustedMovementSpeed() * delta);

            if (getY() < boundLevelStage.getCamera().position.y - boundLevelStage.getCamera().viewportHeight / 2 + GameConstants.CAMERA_PLAYER_PADDINGY) {
                boundLevelStage.getCamera().translate(0, permittedYMovement, 0);
            }
        }

        boundLevelStage.getCamera().update();
    }

    private void checkTaskCollision() {
        for (Task levelTask : boundLevelStage.getMapTasks()) {
            if (getRectangle().overlaps(levelTask.boundRectangle) && !levelTask.isComplete()) {
                //System.out.println("task!");
                this.currentInteractableOverlap = levelTask;
                return;
            }
        }
        if(this.playerType == PlayerType.IMPOSTOR){
            for (Vent levelVent : boundLevelStage.getMapVents().values()) {
                if (getRectangle().overlaps(levelVent.boundRectangle)) {
                    //System.out.println("task!");
                    this.currentInteractableOverlap = levelVent;
                    return;
                }
            }
        }
        this.currentInteractableOverlap = null;
    }

    private void checkPlayerCollision() {
        boolean hasOverlap = false;
        boolean hasKillable = false;

        for (PlayerActor otherPlayer : this.boundLevelStage.getPlayers()) {
            if (otherPlayer != this) {
                if (this.getRectangle().overlaps(otherPlayer.getRectangle())) {
                    if (this.playerState == PlayerState.ALIVE && otherPlayer.getPlayerState() == PlayerState.DEAD) {
                        System.out.println("overlap with other!");
                        currentPlayerOverlap = otherPlayer;
                        hasOverlap = true;
                    }
                    if(this.playerState == PlayerState.ALIVE && this.playerType == PlayerType.IMPOSTOR && otherPlayer.getPlayerState() == PlayerState.ALIVE){
                        System.out.println("can kill!");
                        currentKillableOverlap = otherPlayer;
                        hasKillable = true;
                    }
                }

            }
        }

        if(!hasOverlap){
            this.currentPlayerOverlap = null;
        }
        if(!hasKillable){
            this.currentKillableOverlap = null;
        }

    }

    public Interactable getCurrentInteractableOverlap() {
        return currentInteractableOverlap;
    }

    public PlayerActor getCurrentPlayerOverlap() {
        return currentPlayerOverlap;
    }

    public PlayerActor getCurrentKillableOverlap() {
        return currentKillableOverlap;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        processMovement(delta);
        checkTaskCollision();
        checkPlayerCollision();

        //System.out.println(currentInteractableOverlap);
        //System.out.println(currentKillableOverlap);
    }

    public void setxDirection(int direction) {
        this.xDirection = direction;
    }

    public void setyDirection(int direction) {
        this.yDirection = direction;
    }

    public int getxDirection() {
        return xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public PlayerType getPlayerType(){
        return playerType;
    }

    public void stopMovement () {
        this.xDirection = 0;
        this.yDirection = 0;
    }
}
