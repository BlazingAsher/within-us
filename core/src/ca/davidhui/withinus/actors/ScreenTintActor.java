package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.GameConstants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ScreenTintActor extends Actor {
    private Pixmap taskPixMap;
    private Texture taskTextureRegion;
    private boolean isActive;
    private float accumulator;
    private boolean enabled;
    public ScreenTintActor() {
        this.taskPixMap = new Pixmap(GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT, Pixmap.Format.RGBA8888);
        this.taskPixMap.setColor(new Color(1,0,0,0.5f));
        this.taskPixMap.fillRectangle(0, 0, GameConstants.VIEWPORT_WIDTH, GameConstants.VIEWPORT_HEIGHT);
        this.taskTextureRegion = new Texture(this.taskPixMap);

        this.isActive = false;
        this.enabled = false;
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
        if(this.enabled){
            accumulator += delta;
            if(accumulator > 1.2f){
                this.isActive = !this.isActive;
                accumulator = 0;
            }
        }

    }

    public void enableAction() {
        this.enabled = true;
    }

    public void disableAction() {
        this.enabled = false;
        this.isActive = false;
    }
}
