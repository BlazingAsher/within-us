package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.Utils;
import ca.davidhui.withinus.listeners.PlayerInputListener;
import ca.davidhui.withinus.screens.LevelScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class PlayerActor extends Actor {
    private final Texture playerTexture;
    private final Stage boundLevelStage;
    private final LevelScreen boundLevelScreen;

    private int xDirection = 0;
    private int yDirection = 0;

    public PlayerActor(Texture img, Stage boundLevelStage, LevelScreen boundLevelScreen){
        this.playerTexture = img;
        this.boundLevelStage = boundLevelStage;
        this.boundLevelScreen = boundLevelScreen;

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

    private Rectangle checkCollision(){



        return null;
    }

    private float getPermittedMovement(float desiredAmount, boolean isX){
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
            for (Rectangle collisionRectangle : boundLevelScreen.getCollisionRectangles()) {
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

    private float getPermittedXMovement(float desiredAmount) {
//        float oldX = getX();
//        desiredAmount = Math.abs(desiredAmount);
//
//        // TODO: This might be a bit imprecise going up by 1
//        for(int i = 0;i<=desiredAmount;i++){
//            for (Rectangle collisionRectangle : boundLevelScreen.getCollisionRectangles()) {
//                setX(getX() + xDirection);
//                if(collisionRectangle.overlaps(getRectangle())){
//                    setX(getX() - xDirection);
//                    return (i-1)*xDirection;
//                }
//            }
//        }
//
//        setX(oldX + desiredAmount*xDirection);
//        return desiredAmount*xDirection;
        return getPermittedMovement(desiredAmount, true);
    }

    private float getPermittedYMovement(float desiredAmount) {
//        System.out.println(desiredAmount);
//        desiredAmount = Math.abs(desiredAmount);
//        float oldY = getY();
//
//        for(int i = 0;i<=desiredAmount;i++){
//            for (Rectangle collisionRectangle : boundLevelScreen.getCollisionRectangles()) {
//                float newY = getY() + i*yDirection;
//
//                if(collisionRectangle.overlaps(getRectangle())){
//                    setY(getY() + -yDirection);
//                    return (i-1)*yDirection;
//                }
//            }
//        }
//
//        setY(oldY + desiredAmount*yDirection);
//        return desiredAmount*yDirection;
        return getPermittedMovement(desiredAmount, false);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        float oldX = getX();

        if(xDirection == -1){
            float permittedXMovement = getPermittedXMovement(Utils.getAdjustedMovementSpeed() * delta);
//            setX(getX() + permittedXMovement);

            // If player is past the padding, move camera too
            if(getX() < boundLevelStage.getCamera().position.x - boundLevelStage.getCamera().viewportWidth/2 + GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(permittedXMovement, 0, 0);
            }
//            System.out.println("moved");
//            System.out.println(Utils.getAdjustedMovementSpeed() * delta);
//            System.out.println(permittedXMovement);
//            System.out.println(oldX-getX());
//            System.out.println(oldX);
//            System.out.println(getX());
//            System.out.println("finished movement");
        }

        // Right movement
        if(xDirection == 1){
            float permittedXMovement = getPermittedXMovement(Utils.getAdjustedMovementSpeed() * delta);
//            setX(getX() + permittedXMovement);

            if(getX() > boundLevelStage.getCamera().position.x + boundLevelStage.getCamera().viewportWidth/2 - playerTexture.getWidth() - GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(permittedXMovement, 0, 0);
            }
        }

        // Up movement
        if(yDirection == 1){
            float permittedYMovement = getPermittedYMovement(Utils.getAdjustedMovementSpeed() * delta);
//            setY(getY() + permittedYMovement);

            if(getY() > boundLevelStage.getCamera().position.y + boundLevelStage.getCamera().viewportHeight/2 - playerTexture.getHeight() - GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(0,permittedYMovement,0);
            }
        }

        // Down movement
        if(yDirection == -1){
            float permittedYMovement = getPermittedYMovement(Utils.getAdjustedMovementSpeed() * delta);
//            setY(getY() + permittedYMovement);

            if(getY() < boundLevelStage.getCamera().position.y - boundLevelStage.getCamera().viewportHeight/2 + GameConstants.CAMERA_PLAYER_PADDING){
                boundLevelStage.getCamera().translate(0, permittedYMovement, 0);
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
