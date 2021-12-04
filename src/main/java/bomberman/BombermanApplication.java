package bomberman;

import bomberman.GlobalVariable.FilesPath;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import bomberman.GlobalVariable.RenderVariable;
import bomberman.GlobalVariable.GameVariables;

import javax.sound.sampled.Clip;

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

        GameVariables.PvB_Mode = new PvB_GamePlay();

        GameVariables.PvB_Mode.render();
        GameVariables.PvB_Mode.playPlayGroundAudio();

        scene.setOnKeyPressed(GameVariables.PvB_Mode::inputKeyPress);
        scene.setOnKeyReleased(GameVariables.PvB_Mode::inputKeyRelease);

        GameVariables.PvB_Mode.setGameStatus(PvB_GamePlay.gameStatusType.PLAYING_);

        final long startNanoTime = System.nanoTime();
        final long[] lastNanoTime = {System.nanoTime()};

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapseTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;

                lastNanoTime[0] = currentNanoTime;

                if (GameVariables.PvB_Mode.getGameStatus() == PvB_GamePlay.gameStatusType.PLAYING_) {
                    GameVariables.PvB_Mode.play();
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