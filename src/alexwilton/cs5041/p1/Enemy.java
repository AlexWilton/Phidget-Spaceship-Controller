package alexwilton.cs5041.p1;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends MovingObject{

    private static Image enemyImg = null;
    private ImageView enemyImgView;

    private MovingObject objToAvoid;

    public Enemy(int positionX, int positionY, MovingObject objToAvoid) {
        super(positionX, positionY);
        this.objToAvoid = objToAvoid;
        setup();
    }

    private void setup() {
        if(enemyImg == null){
            try {
                enemyImg = new Image(new FileInputStream("images/enemy.png"));
            } catch (FileNotFoundException e) {
                System.out.println("Cannot Find Spaceship Image");
            }
        }
        enemyImgView = new ImageView(enemyImg);
        enemyImgView.setScaleX(0.4);
        enemyImgView.setScaleY(0.4);
    }

    public void updateForFrame() {
        super.updateForFrame();

        velocity.mul(1/0.99);

        //accelerate away from Spaceship
        PVector acceleration = position.copy().minus(objToAvoid.getPosition());
        acceleration.normalise();
        acceleration.mul(0.02);
        setAcceleration(acceleration);

        enemyImgView.setRotate(-90 - direction * 180 / Math.PI); // setRotate works in degrees
        enemyImgView.setTranslateX(position.x - enemyImg.getWidth() / 2);
        enemyImgView.setTranslateY(position.y - enemyImg.getHeight() / 2);
    }

    @Override
    public List<Node> getVisuals() {
        ArrayList<Node> visuals = new ArrayList<>();
        visuals.add(enemyImgView);
        return visuals;
    }
}
