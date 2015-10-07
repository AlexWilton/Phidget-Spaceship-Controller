package alexwilton.cs5041.p1;

import javafx.scene.Node;

import java.util.List;

public abstract class MovingObject {

    protected PVector position;
    protected PVector velocity;
    protected PVector acceleration;
    protected double direction; // direction in radians

    public MovingObject(int positionX, int positionY) {
        position = new PVector(positionX,positionY);
        velocity = new PVector(0,0);
        acceleration = new PVector(0,0);
        direction = 0;
    }

    public abstract List<Node> getVisuals();

    public void updateForFrame() {
        double sizeSceneX  = App.WIDTH, sizeSceneY = App.HEIGHT;

        //apply drag
        velocity.mul(0.99);

        // update the position and velocity of the ship
        position.add(velocity);
        velocity.add(acceleration);

        // take into account size of screen (wrapping of space)
        if (position.x > sizeSceneX) {
            position.x = position.x % sizeSceneX;
        }
        if (position.y > sizeSceneY) {
            position.y = position.y % sizeSceneY;
        }
        if (position.x < 0) {
            position.x = sizeSceneX;
        }
        if (position.y < 0) {
            position.y = sizeSceneY;
        }
        if (direction>Math.PI*2) {
            direction = 0;
        }
        if (direction<0) {
            direction = Math.PI*2;
        }
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setAcceleration(PVector acceleration) {
        this.acceleration = acceleration;
    }

    public PVector getVelocity() {
        return velocity;
    }

    public PVector getAcceleration() {
        return acceleration;
    }

    public PVector getPosition() {
        return position;
    }

    public double getDirection() {
        return direction;
    }

}
