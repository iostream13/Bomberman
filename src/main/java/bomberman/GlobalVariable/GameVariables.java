package bomberman.GlobalVariable;

import bomberman.PvB_GamePlay;

public class GameVariables {
    /**
     * Chế độ chơi PvB.
     * (Khởi tạo biến này khi chế độ PvB bắt đầu chơi.
     */
    public static PvB_GamePlay PvB_Mode;

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
}
