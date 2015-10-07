package alexwilton.cs5041.p1;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Enemy extends MovingObject{

    private static Image enemyImg = null;
    private ImageView enemyImgView;

    private MovingObject objToAvoid;
    private boolean destroyed = false;
    private final int REAPEAR_TIME = 7 * 60; //7 seconds
    private int reappearTimer;
    private Ellipse explosion = new Ellipse();

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

        if(destroyed){
            reappearTimer--;
            int radius = (reappearTimer - 100) / 10;
            if(radius < 0) radius = 0;
            explosion.setRadiusX(radius);
            explosion.setRadiusY(radius);
            if(reappearTimer % 2 == 0)
                explosion.setFill(Color.RED);
            else
                explosion.setFill(Color.YELLOW);

            if(reappearTimer < 0){
                enemyImgView.setVisible(true);
                destroyed = false;
            }else
                return;
        }

//        velocity.mul(1 / 0.99);

        //accelerate away from Spaceship. Strength of acceleration is proportional to 1/distance^2
        PVector acceleration = position.copy().minus(objToAvoid.getPosition());
        acceleration.normalise();
        double distanceSqBetweenObjects =   Math.pow(objToAvoid.getPosition().x - position.x, 2) +
                                            Math.pow(objToAvoid.getPosition().y - position.y,2);
        if(distanceSqBetweenObjects <= 0.01) distanceSqBetweenObjects = 0.01;
        acceleration.mul(100/distanceSqBetweenObjects);
        acceleration.add(new PVector(0.01,-0.01));
        acceleration.mul(50);
        setAcceleration(acceleration);

        enemyImgView.setRotate(-90 - direction * 180 / Math.PI); // setRotate works in degrees
        enemyImgView.setTranslateX(position.x - enemyImg.getWidth() / 2);
        enemyImgView.setTranslateY(position.y - enemyImg.getHeight() / 2);
    }

    @Override
    public List<Node> getVisuals() {
        ArrayList<Node> visuals = new ArrayList<>();
        visuals.add(enemyImgView);
        visuals.add(explosion);
        return visuals;
    }

    public double getWidth(){
        return enemyImg.getWidth();
    }

    public double getHeight(){
        return enemyImg.getHeight();
    }

    public void destroy() {
        destroyed = true;
        enemyImgView.setVisible(false);
        reappearTimer = REAPEAR_TIME;
        explosion.setCenterX(position.x);
        explosion.setCenterY(position.y);
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
