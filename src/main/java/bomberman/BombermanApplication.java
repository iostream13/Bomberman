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

import bomberman.GlobalVariable.RenderVariable;

/**
 * Chương trình chính.
 */
public class BombermanApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        Group root = RenderVariable.root;
        Canvas canvas = RenderVariable.canvas;
        Scene scene = RenderVariable.scene;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        scene.setRoot(root);

        GamePlay.render();

        stage.setTitle("Bomberman");
        stage.setScene(scene);

        scene.setOnKeyPressed(GamePlay::inputKeyPress);
        scene.setOnKeyReleased(GamePlay::inputKeyRelease);

        final long startNanoTime = System.nanoTime();
        final long[] lastNanoTime = {System.nanoTime()};

        GamePlay.gameStatus = GamePlay.gameStatusType.PLAYING_;

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapseTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;

                lastNanoTime[0] = currentNanoTime;

                if (GamePlay.gameStatus == GamePlay.gameStatusType.PLAYING_) {
                    GamePlay.play();
                } else {
                    return;
                }
            }
        }.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}