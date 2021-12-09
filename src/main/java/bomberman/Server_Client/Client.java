package bomberman.Server_Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import bomberman.GlobalVariable.FilesPath;
import bomberman.GlobalVariable.GameVariables;
import bomberman.GlobalVariable.LANVariables;
import bomberman.GlobalVariable.RenderVariable;
import bomberman.Object.MovingObject.MovingObject;
import bomberman.Server_Client.Server;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {

    // địa chỉ máy chủ
    InetAddress host = null;
    // socket
    public Socket socket = null;
    // luồng đẩy dữ liệu đến server
    public BufferedWriter os = null;
    // luồng nhận dữ liệu từ server
    public BufferedReader is = null;

    // khởi tạo đối tượng client mới
    public Client() {
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), LANVariables.PORT);
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socket.setTcpNoDelay(true);
        } catch (UnknownHostException e) {
            socket = null;
        } catch (IOException e) {
            socket = null;
        }
    }

    // gửi dữ liệu tới server
    public void sendDataToServer(JSONObject jsonObject) {
        try {
            os.write(jsonObject.toString() + "\n");
            os.flush();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }

    public void sendDataToServer(String s) {
        try {
            os.write(s + "\n");
            os.flush();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }

    // nhận data từ server
    public String readDataFromServer() {
        try {
            String command = is.readLine();
            if ((command == null) || (command.length() == 0) || (command.charAt(0) != '[')) {
                return "NOT COMMAND";
            } else {
                return command;
            }
        } catch (IOException e) {
            System.out.println("can not read data from server");
            return "NOT COMMAND";
        }
    }

    /**
     * Xử lí thao tác ấn phím. Biến nó thành lệnh gửi đến server
     *
     * @param e Key Event
     */
    public static void inputKeyPress(KeyEvent e) {
        JSONObject jsonObject = new JSONObject();
        if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.RIGHT_);
                jsonObject.put("status", true);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.LEFT_);
                jsonObject.put("status", true);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.UP_);
                jsonObject.put("status", true);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.DOWN_);
                jsonObject.put("status", true);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e.getCode() == KeyCode.SPACE) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", "placeBomb");
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else return;

        // Gửi lệnh đến server
        LANVariables.client.sendDataToServer(jsonObject);
    }

    /**
     * Xử lí thao tác nhả phím. Biến nó thành lệnh gửi tới server
     *
     * @param e Key Event
     */
    public static void inputKeyRelease(KeyEvent e) {
        JSONObject jsonObject = new JSONObject();
        if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.RIGHT_);
                jsonObject.put("status", false);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.LEFT_);
                jsonObject.put("status", false);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.UP_);
                jsonObject.put("status", false);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
            try {
                jsonObject.put("player", GameVariables.playerRole);
                jsonObject.put("direction", MovingObject.ObjectDirection.DOWN_);
                jsonObject.put("status", false);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else return;

        // gửi lệnh đến server
        LANVariables.client.sendDataToServer(jsonObject);
    }

    // giải mã các lệnh in từ server
    public static boolean decodeRenderCommand(String command) {
        if (command == "NOT COMMAND") return false;
        try {
            JSONArray commandList = new JSONArray(command);
            for (int i = 0; i < commandList.length(); i++) {
                JSONObject object = (JSONObject) commandList.get(i);

                if (object.has("player") && object.get("player").equals("PLAYER_1") && GameVariables.playerRole == GameVariables.role.PLAYER_1) {
                    double x = Double.parseDouble((String) object.get("x"));
                    double y = Double.parseDouble((String) object.get("y"));
                    double width = Double.parseDouble((String) object.get("width"));
                    double length = Double.parseDouble((String) object.get("length"));
                    RenderVariable.gc.drawImage(
                            FilesPath.decodeImageName((String) object.get("Image")),
                            x,
                            y,
                            width,
                            length
                    );
                    return true;
                } else if (object.has("player") && object.get("player").equals("PLAYER_2") && GameVariables.playerRole == GameVariables.role.PLAYER_2) {
                    double x = Double.parseDouble((String) object.get("x"));
                    double y = Double.parseDouble((String) object.get("y"));
                    double width = Double.parseDouble((String) object.get("width"));
                    double length = Double.parseDouble((String) object.get("length"));
                    RenderVariable.gc.drawImage(
                            FilesPath.decodeImageName((String) object.get("Image")),
                            x,
                            y,
                            width,
                            length
                    );
                    return true;
                }

                if (object.has("player")) continue;

                double imageX = Double.parseDouble((String) object.get("imageX"));
                double imageY = Double.parseDouble((String) object.get("imageY"));
                double imageWidth = Double.parseDouble((String) object.get("imageWidth"));
                double imageLength = Double.parseDouble((String) object.get("imageLength"));
                double x = Double.parseDouble((String) object.get("x"));
                double y = Double.parseDouble((String) object.get("y"));
                double width = Double.parseDouble((String) object.get("width"));
                double length = Double.parseDouble((String) object.get("length"));
                RenderVariable.gc.drawImage(
                        FilesPath.decodeImageName((String) object.get("Image")),
                        imageX,
                        imageY,
                        imageWidth,
                        imageLength,
                        x,
                        y,
                        width,
                        length
                );
            }
            return false;
        } catch (JSONException e) {
            return false;
        }
    }
}
