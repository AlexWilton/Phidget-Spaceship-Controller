package alexwilton.cs5041.p1;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SpaceShipLaser {
    private SpaceShip spaceShip;
    private Line laser = new Line();


    private final int LASER_LENGTH = 130;
    private final int COOLDOWN_TIME = 5 * 60; //5 seconds (typically 60 frames per second)
    private final int FIRING_TIME = 2 * 60; //2 seconds
    private LaserState laserState;

    public Node getVisuals() {
        return laser;
    }

    public enum LaserState { READY, FIRING, COOLING_DOWN}

    private int timeRemaining; //time (in frames) left in current state


    public SpaceShipLaser(SpaceShip spaceShip){
        this.spaceShip = spaceShip;
        laserState = LaserState.READY;
    }


    public boolean fireLaser(){
        if(laserState != LaserState.READY) return false;

        timeRemaining = FIRING_TIME; // fire for 2 seconds
        laserState = LaserState.FIRING;
        return true;
    }

    public void updateForFrame() {
        switch (laserState){
            case READY:
                return;
            case FIRING:
                timeRemaining--;
                drawLaser();
                if(timeRemaining == 0){
                    laserState = LaserState.COOLING_DOWN;
                    laser.setVisible(false);
                    timeRemaining = COOLDOWN_TIME;
                }
                break;
            case COOLING_DOWN:
                timeRemaining--;
                if(timeRemaining == 0) {
                    laserState = LaserState.READY;
                }
                break;
        }
    }

    private void drawLaser() {
        PVector pos = spaceShip.getPosition();
        double direction = spaceShip.getDirection();
        double targetX = pos.x + LASER_LENGTH * Math.cos(direction);
        double targetY = pos.y + LASER_LENGTH * -Math.sin(direction);

        laser.setVisible(true);
        laser.setStartX(pos.x);
        laser.setStartY(pos.y);
        laser.setEndX(targetX);
        laser.setEndY(targetY);
        laser.setStrokeWidth(3.0f);
        laser.setStroke(Color.RED);
    }


    public boolean isLaserFiring(){
        return laserState == LaserState.READY;
    }

    public LaserState getLaserState() {
        return laserState;
    }
}
