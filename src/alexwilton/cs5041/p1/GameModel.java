package alexwilton.cs5041.p1;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameModel {
    private SpaceShip spaceShip;

    public GameModel(){
        spaceShip = new SpaceShip();
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public Collection<? extends Node> getVisuals() {
        List<Node> visuals = new ArrayList<>();
        visuals.addAll(spaceShip.getVisuals());
        return visuals;
    }

    public void updateForFrame(){
        spaceShip.updateForFrame();
    }
}
