package bomberman.GlobalVariable;

public class GameVariables {
    /**
     * Độ lớn của một ô ảnh trong resource (đơn vị pixel).
     */
    public static double imageSize = 16;

    /**
     * Độ lớn của một cell trong game.
     */
    public static double cellLength = 40;

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
