package bomberman.Object.MovingObject;

import bomberman.GamePlay;

import bomberman.Object.NonMovingObject.Bomb;
import bomberman.Object.GameObject;

public abstract class MovingObject extends GameObject {
    /**
     * Constructor cho Moving Object.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public MovingObject(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    /**
     * Hướng di chuyển của object.
     */
    public enum ObjectDirection {
        LEFT_,
        RIGHT_,
        UP_,
        DOWN_
    }

    /**
     * Hướng hiện tại của object.
     */
    ObjectDirection objectDirection;

    /**
     * Tốc độ của object.
     */
    private double speed = 20;

    private double x_distance;
    private double y_distance;

    public ObjectDirection getObjectDirection() {
        return objectDirection;
    }

    public void setObjectDirection(ObjectDirection objectDirection) {
        this.objectDirection = objectDirection;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Tính khoảng cách tối đa có thể di chuyển.
     */
    public void calculateDistance() {
        x_distance = 0;
        y_distance = 0;

        switch (objectDirection) {
            case LEFT_:
                x_distance -= speed;
                break;
            case RIGHT_:
                x_distance += speed;
                break;
            case UP_:
                y_distance -= speed;
                break;
            case DOWN_:
                y_distance += speed;
                break;
        }
    }

    /**
     * Kiểm tra xem có thể đứng ở vị trí hiện tại không.
     */
    boolean checkCanMove() {
        int x1 = (int) (this.getX() / GamePlay.map.cellLength);
        int x2 = (int) ((this.getX() + this.getWidth() - 1) / GamePlay.map.cellLength);
        int y1 = (int) (this.getY() / GamePlay.map.cellLength);
        int y2 = (int) ((this.getY() + this.getLength() - 1) / GamePlay.map.cellLength);

        //đứng ở ô không cho phép
        if (GamePlay.map.isBlockCell(y1, x1)) {
            return false;
        }

        if (GamePlay.map.isBlockCell(y1, x2)) {
            return false;
        }

        if (GamePlay.map.isBlockCell(y2, x1)) {
            return false;
        }

        if (GamePlay.map.isBlockCell(y2, x2)) {
            return false;
        }

        //gặp bomb
        for (Bomb bomb : GamePlay.bombs) {
            if (bomb.checkIntersect(this) && bomb.getBlockStatus()) {
                return false;
            }
        }

        return true;
    }

    //hàm di chuyển, cần code lại, chỗ này đang for trâu và còn for ngược nữa :v
    public void move() {
        calculateDistance();
        double preX = this.getX();
        double preY = this.getY();
        this.setX(this.getX() + x_distance);
        this.setY(this.getY() + y_distance);

        while (!checkCanMove() && (x_distance + y_distance != 0)) {
            if (x_distance < 0) {
                x_distance++;
            }

            if (x_distance > 0) {
                x_distance--;
            }

            if (y_distance < 0) {
                y_distance++;
            }

            if (y_distance > 0) {
                y_distance--;
            }

            setX(preX + x_distance);
            setY(preY + y_distance);
        }

        //nếu người di chuyển ra khỏi bom mình đặt, đổi quả bom sang trạng thái block object
        for (Bomb x : GamePlay.bombs) {
            if (!x.checkIntersect(x.getOwner())) {
                x.setBlockStatus(true);
            }
        }
    }
}
