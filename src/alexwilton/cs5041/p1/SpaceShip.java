package alexwilton.cs5041.p1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class SpaceShip extends MovingObject{
    private static Image spaceshipImg = null;
    private ImageView shipImgView;


    private SpaceShipLaser spaceShipLaser;


    public SpaceShip() {
        super(App.WIDTH/2, App.HEIGHT/2);
        setupInitGraphics();
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
        super.updateForFrame();

        shipImgView.setRotate(-90 - direction * 180 / Math.PI); // setRotate works in degrees
        shipImgView.setTranslateX(position.x - spaceshipImg.getWidth()/2);
        shipImgView.setTranslateY(position.y - spaceshipImg.getHeight() / 2);

        spaceShipLaser.updateForFrame();
    }


    public void attemptToFireLaser() {
        spaceShipLaser.fireLaser();
    }

    public SpaceShipLaser.LaserState getLaserState() {
        return spaceShipLaser.getLaserState();
    }

    public void checkForLaserIntersection(Enemy enemy) {
        spaceShipLaser.checkForIntersection(enemy);
    }
}
