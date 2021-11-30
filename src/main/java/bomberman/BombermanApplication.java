package bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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

        root.getChildren().add(canvas);
        scene.setRoot(root);

        stage.setTitle("Bomberman");
        stage.setScene(scene);

        // ********************** HANDLE GAME **********************************************************

        PvB_GamePlay.createGame();

        PvB_GamePlay.render();

        scene.setOnKeyPressed(PvB_GamePlay::inputKeyPress);
        scene.setOnKeyReleased(PvB_GamePlay::inputKeyRelease);

        PvB_GamePlay.setGameStatus(PvB_GamePlay.gameStatusType.PLAYING_);

        final long startNanoTime = System.nanoTime();
        final long[] lastNanoTime = {System.nanoTime()};

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapseTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;

                lastNanoTime[0] = currentNanoTime;

                if (PvB_GamePlay.getGameStatus() == PvB_GamePlay.gameStatusType.PLAYING_) {
                    PvB_GamePlay.play();
                }
            }
        }.start();

        // *********************************************************************************************

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}