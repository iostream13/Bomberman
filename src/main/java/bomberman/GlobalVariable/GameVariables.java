package bomberman.GlobalVariable;

import bomberman.PvB_GamePlay;
import bomberman.PvP_GamePlay;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

public class GameVariables {
    /**
     * Chế độ chơi PvB.
     * (Khởi tạo biến này khi chế độ PvB bắt đầu chơi.
     */
    public static PvB_GamePlay PvB_Mode;

    /**
     * Chế độ PvP trên 2 máy thông qua LAN
     */
    public static PvP_GamePlay PvP_Mode;

    /**
     * Độ lớn của một cell trong game.
     */
    public static final double cellLength = 40;

    /**
     * Hàm trả về giá trị đầu vào chia cho độ lớn của cell để ra chỉ số ô cần thiết.
     *
     * @param inputValue giá trị đầu vào
     * @return kết quả
     */
    public static int calculateCellIndex(double inputValue) {
        return (int) (inputValue / cellLength);
    }

    /**
     * âm thanh.
     * @param filePath đường dẫn
     */
    public static void playSound(String filePath) {
        filePath = "src/main/java/resources/sound/" + filePath;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public static enum role {
        PLAYER_1,
        PLAYER_2,
        NOT,
    }

    public static role playerRole;

    public static JSONArray commandList = new JSONArray();
}
