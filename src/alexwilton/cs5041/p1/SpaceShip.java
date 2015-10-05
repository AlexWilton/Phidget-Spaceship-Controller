package alexwilton.cs5041.p1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SpaceShip {
    private static Image spaceshipImg = null;
    private ImageView shipImgView;

    private PVector position;
    private PVector velocity;
    private PVector acceleration;
    private double direction; // direction in radians

    private SpaceShipLaser spaceShipLaser;


    public SpaceShip() {
        setupInitGraphics();
        position = new PVector(App.WIDTH/2,App.HEIGHT/2);
        velocity = new PVector(0,0);
        acceleration = new PVector(0,0);
        direction = 0;
        spaceShipLaser = new SpaceShipLaser(this);
    }

    private void setupInitGraphics(){
        if(spaceshipImg == null){
            try {
                spaceshipImg = new Image(new FileInputStream("images/spaceship.png"));
            } catch (FileNotFoundException e) {
                System.out.println("Cannot Find Spaceship Image");
            }
        }
        shipImgView = new ImageView(spaceshipImg);
        shipImgView.setScaleX(0.5);
        shipImgView.setScaleY(0.5);
    }

    public List<Node> getVisuals() {
        List<Node> visuals = new ArrayList<>();
        visuals.add(spaceShipLaser.getVisuals());
        visuals.add(shipImgView); //ship on top!
        return visuals;
    }

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

        shipImgView.setRotate(-90 - direction * 180 / Math.PI); // setRotate works in degrees
        shipImgView.setTranslateX(position.x - spaceshipImg.getWidth()/2);
        shipImgView.setTranslateY(position.y - spaceshipImg.getHeight()/2);

        spaceShipLaser.updateForFrame();

    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setAcceleration(PVector acceleration) {
        this.acceleration = acceleration;
    }

    public void attemptToFireLaser() {
        spaceShipLaser.fireLaser();
    }

    public PVector getPosition() {
        return position;
    }

    public double getDirection() {
        return direction;
    }

    public SpaceShipLaser.LaserState getLaserState() {
        return spaceShipLaser.getLaserState();
    }
}
