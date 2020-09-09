package ca.davidhui.withinus.models;

import ca.davidhui.withinus.actors.VentActor;
import com.badlogic.gdx.math.Rectangle;

public class Vent extends Interactable{
    public int[] nextNodes;
    private final VentActor ventActor;
    private final Integer id;

    public Vent(Rectangle boundRectangle, Integer id, String nextNodesS) {
        super(boundRectangle);

        // Parse vent network
        String[] numberStrs = nextNodesS.split(",");
        int[] numbers = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
        {
            // Note that this is assuming valid input
            // If you want to check then add a try/catch
            // and another index for the numbers if to continue adding the others (see below)
            numbers[i] = Integer.parseInt(numberStrs[i]);
        }

        this.nextNodes = numbers;

        this.id = id;

        this.ventActor = new VentActor(this);
    }

    public VentActor getVentActor() {
        return ventActor;
    }

    @Override
    public void setOutlined() {
        this.ventActor.setOutlined();
    }
}
