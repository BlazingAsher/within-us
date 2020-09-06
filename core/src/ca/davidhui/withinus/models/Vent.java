package ca.davidhui.withinus.models;

import ca.davidhui.withinus.actors.VentActor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Vent extends Interactable{
    public ArrayList<VentActor> nextNodes;
    private final VentActor ventActor;
    private final Integer id;

    public Vent(Rectangle boundRectangle, Integer id) {
        super(boundRectangle);

        this.nextNodes = new ArrayList<>();
        this.id = id;

        this.ventActor = new VentActor(this);
    }

    public VentActor getVentActor() {
        return ventActor;
    }
}
