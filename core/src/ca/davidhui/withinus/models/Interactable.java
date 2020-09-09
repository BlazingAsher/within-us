package ca.davidhui.withinus.models;

import com.badlogic.gdx.math.Rectangle;

public abstract class Interactable {
    public final Rectangle boundRectangle;
    public abstract void setOutlined();
    public Interactable(Rectangle boundRectangle){
        this.boundRectangle = boundRectangle;
    }
}
