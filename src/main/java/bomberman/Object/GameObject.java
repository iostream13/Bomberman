package bomberman.Object;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.RenderVariable;
import bomberman.GlobalVariable.ImagesPath;

/**
 * Object của game.
 */
public abstract class GameObject {
    // Tọa độ x, y và kích thước của object.

    /**
     * Tọa độ x.
     */
    private double x;

    /**
     * Tọa độ y.
     */
    private double y;

    /**
     * Chiều rộng của object.
     */
    private double width;

    /**
     * Chiều dài của object.
     */
    private double length;

    /**
     * Tọa độ x của tâm object.
     */
    private double centerX;

    /**
     * Tọa độ y của tâm object.
     */
    private double centerY;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;

        calculateCenterPoint();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;

        calculateCenterPoint();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;

        calculateCenterPoint();
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;

        calculateCenterPoint();
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    /**
     * Constructor cho object của game.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public GameObject(double x, double y, double width, double length) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.length = length;

        calculateCenterPoint();
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
        return !(x > other.getX() + other.getWidth()) &&
                !(y > other.getY() + other.getLength()) &&
                !(x + width < other.getX()) &&
                !(y + length < other.getY());
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
        return !(x > temp_x_2) &&
                !(y > temp_y_2) &&
                !(x + width < temp_x_1) &&
                !(y + length < temp_y_1);
    }

    /**
     * Trả về image hiện tại của object.
     */
    public Image getImage() {
        return ImagesPath.Brick;
    }

    /**
     * Vẽ object.
     */
    public void draw() {
        RenderVariable.gc.drawImage(getImage(), x, y, width, length);
    }
}


