package ca.davidhui.withinus.actors;

import ca.davidhui.withinus.models.Vent;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class VentActor extends Actor {

    private Vent boundVent;
    public VentActor(Vent boundVent){
        this.boundVent = boundVent;
    }


}
