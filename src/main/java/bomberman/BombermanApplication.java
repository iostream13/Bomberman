package bomberman;

import bomberman.GlobalVariable.*;
import bomberman.Server_Client.Client;
import bomberman.Server_Client.EchoThread;
import bomberman.Server_Client.Server;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;

/**
 * Chương trình chính.
 */
public class BombermanApplication extends Application {
    private enum runningMode {
        MENU,  //TODO: code this
        PvP,
        PvB,
    }
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        Group root = RenderVariable.root;
        Canvas canvas = RenderVariable.canvas;
        Scene scene = RenderVariable.scene;

        root.getChildren().add(canvas);
        scene.setRoot(root);

        stage.setTitle("Bomberman");
        stage.setScene(scene);

        runningMode runMode = runningMode.PvP;

        if (runMode == runningMode.PvB) {
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
        } else {

            // thử cho 1 client kết nối tới server
            LANVariables.client = new Client();
            Socket socket = null;

            if (LANVariables.client.socket == null || LANVariables.client.socket == null) {
                //nếu không kết nối được, tức là chưa có server

                //tạo server mới ở máy mình
                LANVariables.server = new Server();
                LANVariables.client = new Client();
                try {
                    socket = LANVariables.server.serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                // luồng giao tiếp giữa server và client 1
                new EchoThread(socket).start();
                GameVariables.PvP_Mode = new PvP_GamePlay();
                GameVariables.playerRole = GameVariables.role.PLAYER_1;
                GameVariables.PvP_Mode.setGameStatus(PvP_GamePlay.gameStatusType.PLAYING_);
            } else {
                GameVariables.playerRole = GameVariables.role.PLAYER_2;
                try {
                    socket = LANVariables.server.serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                // luồng giao tiếp giữa server và client 2
                new EchoThread(socket).start();
            }

            scene.setOnKeyPressed(Client::inputKeyPress);
            scene.setOnKeyReleased(Client::inputKeyRelease);

            new AnimationTimer() {
                public void handle(long currentNanoTime) {

                    if (GameVariables.PvP_Mode.getGameStatus() == PvP_GamePlay.gameStatusType.PLAYING_) {
                        GameVariables.PvP_Mode.play();
                    }
                }
            }.start();

            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}