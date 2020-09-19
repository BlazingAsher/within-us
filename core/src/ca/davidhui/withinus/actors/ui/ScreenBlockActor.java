package ca.davidhui.withinus.actors.ui;

import ca.davidhui.withinus.GameConstants;
import ca.davidhui.withinus.actors.PlayerActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ScreenBlockActor extends Actor {
    private Pixmap taskPixMap;
    private Texture taskTextureRegion;
    private boolean isActive;
    private float accumulator;
    private boolean enabled;

    private PlayerActor boundPlayer;

    public ScreenBlockActor() {
        this.taskPixMap = new Pixmap(GameConstants.VIEWPORT_WIDTH*2, GameConstants.VIEWPORT_HEIGHT*2, Pixmap.Format.RGBA8888);
        this.taskPixMap.setBlending(Pixmap.Blending.None);
        this.taskPixMap.setColor(new Color(0,0,0,1));
        this.taskPixMap.fillRectangle(0, 0, GameConstants.VIEWPORT_WIDTH*2, GameConstants.VIEWPORT_HEIGHT*2);
        this.taskPixMap.setColor(new Color(0,0,0,0));
        this.taskPixMap.fillCircle(this.taskPixMap.getWidth()/2, this.taskPixMap.getHeight()/2, 200);
//        this.taskPixMap.setColor(new Color);
        this.taskTextureRegion = new Texture(this.taskPixMap);

        this.taskPixMap.dispose();

        this.isActive = true; // currently tinting the screen
//        this.enabled = false; // tinting timer is active, may not be tinting the screen though
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        if(this.isActive){
            batch.draw(this.taskTextureRegion, getX(), getY(), this.getOriginX(), this.getOriginY(), this.taskTextureRegion.getWidth(), this.taskTextureRegion.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                    this.taskTextureRegion.getWidth(),this.taskTextureRegion.getHeight(),false,false);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        if(this.enabled){
//            accumulator += delta;
//            if(accumulator > 1.2f){
//                this.isActive = !this.isActive;
//                accumulator = 0;
//            }
//        }
//        System.out.println(getX());

    }

    public void centerAt(float x, float y){
        this.setX(x+this.boundPlayer.getWidth()/2-this.taskTextureRegion.getWidth()/2f);
        this.setY(y+this.boundPlayer.getHeight()/2-this.taskTextureRegion.getHeight()/2f);
    }

    public void bindPlayer(PlayerActor playerActor){
        this.boundPlayer = playerActor;
    }

    public void enableAction() {
        this.enabled = true;
    }

    public void disableAction() {
        this.enabled = false;
        this.isActive = false;
    }
}
