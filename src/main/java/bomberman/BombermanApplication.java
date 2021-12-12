package bomberman;

import bomberman.GlobalVariable.*;
import bomberman.Server_Client.Client;
import bomberman.Server_Client.EchoThread;
import bomberman.Server_Client.Server;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

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
        FXMLLoader loaderScene1 = new FXMLLoader(getClass().getResource("/start.fxml"));
        Scene scene1 = new Scene(loaderScene1.load(), 1240, 520);
        Scene scene = RenderVariable.scene;

        root.getChildren().add(canvas);
        scene.setRoot(root);

        stage.setTitle("Bomberman");
        stage.setScene(scene1);
        Group showS = (Group) scene1.lookup("#showStart");
        Group showS1 = (Group) scene1.lookup("#showStart1");
        Group joinSv = (Group) scene1.lookup("#joinSv");
        Group createSv = (Group) scene1.lookup("#createSv");
        showS.toFront();
        showS1.toBack();
        joinSv.toBack();
        createSv.toBack();
        ImageView imgPvB = (ImageView) scene1.lookup("#playPvB");
        imgPvB.setOnMouseClicked(mouseEvent -> {
            // ********************** HANDLE GAME **********************************************************

            GameVariables.PvB_Mode = new PvB_GamePlay();

            GameVariables.PvB_Mode.render();
            GameVariables.PvB_Mode.playPlayGroundAudio();

            scene.setOnKeyPressed(GameVariables.PvB_Mode::inputKeyPress);
            scene.setOnKeyReleased(GameVariables.PvB_Mode::inputKeyRelease);

            GameVariables.PvB_Mode.setGameStatus(PvB_GamePlay.gameStatusType.PLAYING_);
            new AnimationTimer() {
                boolean stopped = false;
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
            stage.setScene(scene);
            stage.show();
        });
        ImageView imgPvP = (ImageView) scene1.lookup("#playPvP");
        imgPvP.setOnMouseClicked(mouseEvent -> {
            showS.toBack();
            showS1.toFront();
            ImageView imgCreate = (ImageView) scene1.lookup("#createSV");
            ImageView imgJoin = (ImageView) scene1.lookup("#joinSV");

            imgCreate.setOnMouseClicked(mouseEvent1 -> {
                createSv.toFront();
                joinSv.toBack();
                try {
                    TextArea textIP = (TextArea) scene1.lookup("#textIP");
                    textIP.setText(String.valueOf(InetAddress.getLocalHost()));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                // thử cho 1 client kết nối tới server
                LANVariables.client = new Client();
                Socket socket = null;
                //tạo server mới ở máy mình
                LANVariables.server = new Server();

                // khởi tạo bản thân như client 1
                LANVariables.client = new Client();

                // tạo luồng giao tiếp giữa server và client 1
                try {
                    socket = LANVariables.server.serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                new EchoThread(socket).start();

                GameVariables.PvP_Mode = new PvP_GamePlay();
                GameVariables.PvP_Mode.play();

                // đợi client 2 kết nối
                while (true) {
                    // thử tạo luồng giao tiếp cho client 2, nếu thành công thì không đợi nữa
                    try {
                        Socket socket_2 = LANVariables.server.serverSocket.accept();
                        new EchoThread(socket_2).start();
                        break;
                    } catch (IOException e) {
                        continue;
                    }
                }
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
                stage.setScene(scene);
                stage.show();
            });
            imgJoin.setOnMouseClicked(mouseEvent1 -> {
                joinSv.toFront();
                createSv.toBack();
                Button join = (Button) scene1.lookup("#join");
                join.setOnMouseClicked(mouseEvent2 -> {
                    TextArea IP = (TextArea) scene1.lookup("#byIP");
                    if (!Client.createClientWithIP(IP.getText())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("IP invalid!");
                        alert.show();
                    }
                    else {
                        // kết nối thành công tức là mình là client 2
                        GameVariables.playerRole = GameVariables.role.PLAYER_2;
                        scene.setOnKeyPressed(Client::inputKeyPress);
                        scene.setOnKeyReleased(Client::inputKeyRelease);

                        new AnimationTimer() {
                            boolean stopped = false;
                            public void handle(long currentNanoTime) {

                                if (GameVariables.playerRole == GameVariables.role.PLAYER_2) {
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
                                        if(GameVariables.playerRole == GameVariables.role.PLAYER_2) {
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
                        stage.setScene(scene);
                        stage.show();
                    }
                });

            });

        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}