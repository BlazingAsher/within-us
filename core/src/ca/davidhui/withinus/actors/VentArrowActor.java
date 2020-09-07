package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.models.Vent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VentArrowActor extends Actor {
    private PlayerActor boundPlayer;
    private WithinUs game;
    private Texture arrowTexture;
    private Vent pointingTo;

    public VentArrowActor(final PlayerActor boundPlayer, WithinUs game, final Vent pointingTo, float x, float y){
        this.boundPlayer = boundPlayer;
        this.game = game;
        this.pointingTo = pointingTo;

        this.arrowTexture = this.game.getAssetManager().get("images/graphics/VentArrow.png", Texture.class);

        this.setBounds(x, y, arrowTexture.getWidth(), arrowTexture.getHeight());
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);

        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked");
                System.out.println(this);
                boundPlayer.teleportTo(pointingTo.boundRectangle.x+pointingTo.boundRectangle.width/2, pointingTo.boundRectangle.y+pointingTo.boundRectangle.height/2, true);
                boundPlayer.clearVentArrows();
                boundPlayer.drawVentsUI(pointingTo);
                return true;
            }
        });
//        System.out.print("vent original coords: " + getX() + "," + getY() + "," + getOriginX() + "," + getOriginY());
    }

    public void moveToCenter() {
        this.setX(getX() - this.arrowTexture.getWidth()/2f);
        this.setY(getY() - this.arrowTexture.getHeight()/2f);
//        System.out.print("vent current coords: " + getX() + "," + getY() + "," + getOriginX() + "," + getOriginY());
    }

    public Rectangle getRectangle() {
        return new Rectangle(this.getX(), this.getY(), this.arrowTexture.getWidth(), this.arrowTexture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        batch.draw(arrowTexture, getX(), getY(), this.getOriginX(), this.getOriginY(), this.arrowTexture.getWidth(), this.arrowTexture.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                arrowTexture.getWidth(),arrowTexture.getHeight(),false,false);
    }

}
