package alexwilton.phidgetSpaceship;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameModel {
    private SpaceShip spaceShip;
    private Enemy enemy;

    public GameModel(){
        spaceShip = new SpaceShip();
        enemy = new Enemy(200, 100, spaceShip);
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public Collection<? extends Node> getVisuals() {
        List<Node> visuals = new ArrayList<>();
        visuals.addAll(enemy.getVisuals());
        visuals.addAll(spaceShip.getVisuals());
        return visuals;
    }

    public void updateForFrame(){
        spaceShip.updateForFrame();
        spaceShip.checkForLaserIntersection(enemy);
        enemy.updateForFrame();
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
