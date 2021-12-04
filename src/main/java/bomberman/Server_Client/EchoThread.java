package bomberman.Server_Client;

import bomberman.GlobalVariable.GameVariables;
import bomberman.GlobalVariable.LANVariables;

import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {

        InputStream inp = null;
        BufferedReader brinp = null;
        BufferedWriter out = null;

        // khởi tạo luồng vào và ra cho luồng giao tiếp hiện tại
        try {
            socket.setTcpNoDelay(true);
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            return;
        }

        String line;
        while (true) {

            // đọc dữ liệu từ client
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    break;
                } else if (line.contains("GET")) {
                    // Nếu là yêu cầu lấy hình ảnh, gửi các lệnh render cho client
                    if ((GameVariables.commandList != null) && (GameVariables.commandList.length() > 0)) {
                        String s = GameVariables.commandList.toString() + '\n';
                        out.write(s);
                        out.flush();
                        //gọi client nhận dữ liệu và xử lý dữ liệu đó
                        GameVariables.PvP_Mode.decodeRenderCommand(LANVariables.client.readDataFromServer());
                    }
                } else {
                    // Nếu là yêu cầu thao tác nhân vật, xử lý yêu cầu đó
                    GameVariables.PvP_Mode.decodePlayerCommand(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
