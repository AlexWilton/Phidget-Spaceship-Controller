package alexwilton.cs5041.p1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    public final static int WIDTH = 800;
    public final static int HEIGHT = 600;

    private SpaceShip spaceShip;
    private Scene scene;
    private Controller controller;

    @Override
    public void start(Stage stage) {
        /* Setup MODEL*/
        spaceShip = new SpaceShip();

        /* Setup VIEW */
        setupStage(stage);

        /* Setup CONTROLLER */
        controller = new Controller(this);

        /* Setup and Start App Loop */
        (new AnimationTimer() {
            @Override
            public void handle(long now) {
                mainUpdate();
            }
        }).start();
    }

    private void setupStage(Stage stage){
        // root node object for the scene
        Pane root = new Pane();

        // add the spaceShip visuals
        root.getChildren().add(spaceShip.getVisuals());

        // create the main scene
        scene = new Scene(root, WIDTH, HEIGHT);

        stage.setTitle("Axteroids");
        stage.setScene(scene);
        stage.show();
    }

    public void mainUpdate() {
        spaceShip.updatePos();
        controller.updateSpaceShipAcceleration();
    }

    public static void start(){
        launch();
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }
}
