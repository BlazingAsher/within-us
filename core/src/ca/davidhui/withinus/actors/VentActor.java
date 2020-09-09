package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.models.Vent;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class VentActor extends Actor {

    private Vent boundVent;
    private boolean outlineActive = false;
    public VentActor(Vent boundVent){
        this.boundVent = boundVent;
    }

    public void setOutlined() {
        this.outlineActive = true;
    }

}
