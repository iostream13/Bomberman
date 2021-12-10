package bomberman;

import bomberman.GlobalVariable.*;
import bomberman.Server_Client.Client;
import bomberman.Server_Client.EchoThread;
import bomberman.Server_Client.Server;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.json.JSONArray;

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

        runningMode runMode = runningMode.PvB;

        if (runMode == runningMode.PvB) {
            // ********************** HANDLE GAME **********************************************************

            GameVariables.PvB_Mode = new PvB_GamePlay();

            GameVariables.PvB_Mode.render();
            GameVariables.PvB_Mode.playPlayGroundAudio();

            scene.setOnKeyPressed(GameVariables.PvB_Mode::inputKeyPress);
            scene.setOnKeyReleased(GameVariables.PvB_Mode::inputKeyRelease);

            GameVariables.PvB_Mode.setGameStatus(PvB_GamePlay.gameStatusType.PLAYING_);


            new AnimationTimer() {
                boolean stopped =false;
                public void handle(long currentNanoTime) {

                    if (stopped) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        stage.hide();
                    }

                    if (GameVariables.PvB_Mode.getGameStatus() == PvB_GamePlay.gameStatusType.PLAYING_) {
                        GameVariables.PvB_Mode.play();
                    } else {
                        stopped = true;
                    }

                    if (!(stage.isShowing())) {
                        GameVariables.PvB_Mode=null;
                        this.stop();
                    }
                }
            }.start();

            // *********************************************************************************************

            stage.show();
        } else {

            scene.setOnKeyPressed(Client::inputKeyPress);
            scene.setOnKeyReleased(Client::inputKeyRelease);

            new AnimationTimer() {
                boolean stopped = false;
                public void handle(long currentNanoTime) {

                    if (GameVariables.playerRole == GameVariables.role.PLAYER_1) {
                        if (GameVariables.PvP_Mode.getGameStatus() == PvP_GamePlay.gameStatusType.PLAYING_) {
                            GameVariables.PvP_Mode.play();
                        }
                    }

                    if (stopped) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        stage.hide();
                    }

                    LANVariables.client.sendDataToServer("GET");

                    //gọi client nhận dữ liệu và xử lý dữ liệu từ server
                    GameVariables.commandListString = LANVariables.client.readDataFromServer();

                    stopped = Client.decodeRenderCommand(GameVariables.commandListString);

                    if (!(stage.isShowing())) {
                        try {
                            if(GameVariables.playerRole == GameVariables.role.PLAYER_1) {
                                LANVariables.server.serverSocket.close();
                            }
                            LANVariables.client.socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LANVariables.client=null;
                        LANVariables.server=null;
                        GameVariables.playerRole=null;
                        GameVariables.commandList = new JSONArray();
                        GameVariables.tempCommandList = new JSONArray();
                        GameVariables.commandListString = new String();
                        GameVariables.PvP_Mode=null;
                        this.stop();
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