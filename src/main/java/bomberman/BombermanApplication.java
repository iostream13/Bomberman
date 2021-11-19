package bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class BombermanApplication extends Application {
    public static Group root = new Group();
    public static Canvas canvas = new Canvas(1240,520);
    public static GraphicsContext gc = canvas.getGraphicsContext2D();
    public static Scene scene = new Scene(root);
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Management.importFromFile();
        System.out.println(Map.height);
        System.out.println(Map.weight);
        BombController test = new BombController();
        scene.setRoot(root);
        test.render();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        scene.setOnKeyPressed(Management::inputKeyPress);
        scene.setOnKeyReleased(Management::inputKeyRelease);
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                test.play();
            }
        }.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}