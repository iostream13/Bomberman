package bomberman.Object;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.RenderVariable;

import bomberman.Map.PlayGround;

/**
 * Object của game.
 */
public abstract class GameObject {
    /**
     * Tham chiếu đến PlayGround mà object này thuộc về trong đó.
     * (Đặt là belongTo để tránh trùng lặp với owner của bomb)
     */
    private PlayGround belongTo;

    public void setBelongTo(PlayGround belongTo) {
        this.belongTo = belongTo;
    }

    public PlayGround getBelongTo() {
        return belongTo;
    }

    /**
     * Tọa độ x.
     */
    private double x;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        calculateCenterPoint();
    }

    /**
     * Tọa độ y.
     */
    private double y;

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;

        calculateCenterPoint();
    }

    /**
     * Chiều rộng của object.
     */
    private double width;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;

        calculateCenterPoint();
    }

    /**
     * Chiều dài của object.
     */
    private double length;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;

        calculateCenterPoint();
    }

    /**
     * Tọa độ x của tâm object.
     */
    private double centerX;

    public double getCenterX() {
        return centerX;
    }

    /**
     * Tọa độ y của tâm object.
     */
    private double centerY;

    public double getCenterY() {
        return centerY;
    }

    /**
     * Số lượng frame của object này.
     */
    private int numberOfFrame;

    public int getNumberOfFrame() {
        return numberOfFrame;
    }

    public void setNumberOfFrame(int numberOfFrame) {
        this.numberOfFrame = numberOfFrame;
    }

    /**
     * Chỉ số frame hiện tại.
     * (Đánh số từ 0 đến numberOfFrame - 1)
     */
    private int currentFrame;

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Số lượng frame của game chạy để hiển thị 1 frame của object.
     * Giải thích : Chương trình mặc định chạy 60 frame/1 giây (60fps),
     * để kéo dài thời gian 1 frame của object hiển thị ra
     * thì cứ mỗi 1 hoặc 1 vài frame của chương trình thì hiển thị
     * 1 frame của object.
     * Ví dụ numberOfGameFramePerFrame là 2, thì vòng lặp play() của game
     * chạy 2 lần thì Object đổi 1 frame.
     */
    private int numberOfGameFramePerFrame;

    public int getNumberOfGameFramePerFrame() {
        return numberOfGameFramePerFrame;
    }

    public void setNumberOfGameFramePerFrame(int numberOfGameFramePerFrame) {
        this.numberOfGameFramePerFrame = numberOfGameFramePerFrame;
    }

    /**
     * Biến đếm game frame để tính toán currentFrame.
     */
    private int gameFrameCount;

    public int getGameFrameCount() {
        return gameFrameCount;
    }

    public void setGameFrameCount(int gameFrameCount) {
        this.gameFrameCount = gameFrameCount;
    }

    /**
     * Constructor cho object của game.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     * @param width    chiều rộng
     * @param length   chiều dài
     */
    public GameObject(PlayGround belongTo, double x, double y, double width, double length) {
        this.belongTo = belongTo;

        this.x = x;
        this.y = y;

        this.width = width;
        this.length = length;

        calculateCenterPoint();

        currentFrame = 0;
        gameFrameCount = 0;

        setGraphicData();
    }

    /**
     * Tính tọa độ tâm của object.
     */
    public void calculateCenterPoint() {
        centerX = x + width / 2;
        centerY = y + length / 2;
    }

    /**
     * Kiểm tra xem một điểm có nằm trong object hay không.
     *
     * @param _x tọa độ x
     * @param _y tọa độ y
     * @return có hoặc không
     */
    public boolean checkPointInside(double _x, double _y) {
        return x <= _x && _x <= x + width && y <= _y && _y <= y + length;
    }

    /**
     * Kiểm tra xem object này có chạm với object kia không.
     *
     * @param other object kia
     * @return có hoặc không
     */
    public boolean checkIntersect(GameObject other) {
        return !(x >= other.getX() + other.getWidth()) &&
                !(y >= other.getY() + other.getLength()) &&
                !(x + width <= other.getX()) &&
                !(y + length <= other.getY());
    }

    /**
     * Kiểm tra xem object này có chạm với khối kia không.
     *
     * @param temp_x_1 min x
     * @param temp_x_2 max x
     * @param temp_y_1 min y
     * @param temp_y_2 max y
     * @return có hoặc không
     */
    public boolean checkIntersect(double temp_x_1, double temp_x_2, double temp_y_1, double temp_y_2) {
        return !(x >= temp_x_2) &&
                !(y >= temp_y_2) &&
                !(x + width <= temp_x_1) &&
                !(y + length <= temp_y_1);
    }

    /**
     * Trả về image hiện tại của object.
     */
    public abstract Image getImage();

    /**
     * Set thông tin về frame và hình ảnh(với MovingObject) cho mỗi object riêng biệt.
     */
    public abstract void setGraphicData();

    /**
     * Vẽ object.
     */
    public void draw() {
        // Tính toán currentFrame
        if (gameFrameCount >= (numberOfFrame * numberOfGameFramePerFrame)) {
            gameFrameCount = gameFrameCount % (numberOfFrame * numberOfGameFramePerFrame);
        }

        currentFrame = gameFrameCount / numberOfGameFramePerFrame;

        gameFrameCount++;

        // Vị trí frame hiện tại trong ảnh
        double imageX = currentFrame * RenderVariable.imageSize;
        double imageY = 0;
        double imageWidth = RenderVariable.imageSize;
        double imageLength = RenderVariable.imageSize;

        RenderVariable.gc.drawImage(getImage(), imageX, imageY, imageWidth, imageLength, x, y, width, length);
    }
}


