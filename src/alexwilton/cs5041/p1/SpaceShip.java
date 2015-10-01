package alexwilton.cs5041.p1;

import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;

class SpaceShip {
    private final static double SHIP_WIDTH = 10;
    private final static double SHIP_LENGTH = 15;
    private final static double SHIP_LINE_WIDTH_EXTERNAL = 2;
    private Color color;

    private Polygon shipPolygon; // main visual of the spaceShip

    private PVector position;
    private PVector velocity;
    private PVector acceleration;
    private double direction; // direction in radians

    public SpaceShip() {
        // main object for the graphics of the ship
        shipPolygon = new Polygon();
        shipPolygon.getPoints().addAll(-SHIP_WIDTH / 2, 0.0, +SHIP_WIDTH / 2, 0.0, 0.0, SHIP_LENGTH);
        color = Color.RED;
        shipPolygon.setStroke(Color.BLACK);
        shipPolygon.setFill(color);
        shipPolygon.setStrokeWidth(SHIP_LINE_WIDTH_EXTERNAL);

        position = new PVector(0,0);
        velocity = new PVector(0,0);
        acceleration = new PVector(0,0);
        direction = 0;
    }

    public Node getVisuals() {
        return shipPolygon;
    }

    public void updatePos() {
        double sizeSceneX  = App.WIDTH, sizeSceneY = App.HEIGHT;

        //apply drag
        velocity.mul(0.995);

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

        shipPolygon.setRotate(-90-direction*180/Math.PI); // setRotate works in degrees        
        shipPolygon.setTranslateX(position.x);
        shipPolygon.setTranslateY(position.y);

    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

//    public void increaseSpeed(double accel) {
//        double newSpeedX = speedX + Math.cos(-direction)*accel;
//        double newSpeedY = speedY + Math.sin(-direction)*accel;
//        if(distance(newSpeedX, newSpeedY) < 5){
//            speedX = newSpeedX;
//            speedY = newSpeedY;
//        }
//    }

    public void turn(double d) {
        direction = direction+d;
        if (direction > Math.PI*2) direction = 0;
        if (direction < 0) direction = Math.PI*2;
    }

    public double distance(double x, double y){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
    }

    public double getSpeed(){
        return distance(velocity.x, velocity.y);
    }

    public void setAcceleration(PVector acceleration) {
        this.acceleration = acceleration;
    }
}
