package bomberman.Server_Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import bomberman.GlobalVariable.GameVariables;
import bomberman.GlobalVariable.LANVariables;
import bomberman.Object.MovingObject.MovingObject;
import bomberman.Server_Client.Server;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.json.JSONException;
import org.json.JSONObject;

public class Client{

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
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String command = is.readLine();
            if ((command == null) || (command.length() == 0) || (command.charAt(0) != '[')) {
                return "NOT COMMAND";
            } else {
                //System.out.println(command);
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
}