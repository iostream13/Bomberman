package bomberman;

import javafx.scene.image.Image;

public abstract class Object {
    //tọa độ x, y và kích thước của object
    //x là width sẽ ứng với chiều ngang, dùng để tính chỉ số cột
    //y và length sẽ ứng với chiều dài, dùng để tính chỉ số hàng
    private float x;
    private float y;
    private float width;
    private float length;

    public Object(float x, float y, float width, float length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    // kiểm tra điểm có nằm trong object không
    boolean checkPointInside(float _x, float _y) {
        if (x<_x && _x<x+width && y<_y && _y<y+length) return true;
        return false;
    }

    // kiểm tra 2 object có chạm nhau không
    public boolean checkIntersect(Object other) {
        if (x >= other.getX() + other.getWidth()) return false;
        if (y >= other.getY() + other.getLength()) return false;
        if (x + width <=  other.getX()) return false;
        if (y + length <=  other.getY()) return false;
        return true;
    }

    //trả về hình ảnh hiện tại của object
    public Image getImage() {
        return Images.Brick;
    }

    //vẽ object ra
    public void draw() {
        BombermanApplication.gc.drawImage(getImage(), x, y, width, length);
    }
}


