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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
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
        if (x >= other.getX() + other.getWidth()) {
            return false;
        }

        if (y >= other.getY() + other.getLength()) {
            return false;
        }

        if (x + width <= other.getX()) {
            return false;
        }

        if (y + length <= other.getY()) {
            return false;
        }

        return true;
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


