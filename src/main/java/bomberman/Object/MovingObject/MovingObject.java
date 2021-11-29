package bomberman.Object.MovingObject;

import bomberman.PvB_GamePlay;

import bomberman.Object.NonMovingObject.Bomb;
import bomberman.Object.GameObject;
import bomberman.Object.Map.PlayGround;
import javafx.scene.SubScene;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

        setObjectDirection(ObjectDirection.NONE_, true);

        currentState = ObjectMovementState.HORIZONTAL_;
    }

    // MOVEMENT STATE ---------------------------------------------------------------------------

    /**
     * Hướng di chuyển của object.
     */
    public enum ObjectDirection {
        LEFT_,
        RIGHT_,
        UP_,
        DOWN_,
        NONE_
    }

    /**
     * Hàng chờ hướng di chuyển.
     */
    private ArrayList<ObjectDirection> queueDirection = new ArrayList<>();

    /**
     * Trạng thái hiện tại.
     */
    private ObjectDirection currentDirection;

    /**
     * Set hướng di chuyển cho object.
     *
     * @param objectDirection hướng di chuyển
     * @param tempDirection   trạng thái có hoặc không
     */
    public void setObjectDirection(ObjectDirection objectDirection, boolean tempDirection) {
        queueDirection.remove(objectDirection);

        if (tempDirection) {
            queueDirection.add(objectDirection);
        }

        currentDirection = queueDirection.get(queueDirection.size() - 1);
    }

    /**
     * Trạng thái di chuyển dọc hoặc ngang của object.
     */
    public enum ObjectMovementState {
        VERTICAL_,
        HORIZONTAL_
    }

    /**
     * Trạng thái di chuyển hiện tại của object.
     */
    private ObjectMovementState currentState;

    //---------------------------------------------------------------------------------------------

    /**
     * Tốc độ của object.
     */
    private double speed = 3; // DEFAULT SPEED

    /**
     * Độ lệch x giữa điểm cần di chuyển tới và điểm hiện tại.
     */
    private double deltaX;

    /**
     * Độ lệch y giữa điểm cần di chuyển tới và điểm hiện tại.
     */
    private double deltaY;

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
        deltaX = 0;
        deltaY = 0;

        switch (currentDirection) {
            case LEFT_:
                deltaX -= speed;
                break;
            case RIGHT_:
                deltaX += speed;
                break;
            case UP_:
                deltaY -= speed;
                break;
            case DOWN_:
                deltaY += speed;
                break;
        }
    }

    /**
     * Kiểm tra xem có thể đứng ở vị trí hiện tại không.
     *
     * @param temp_x tạo độ trái trên x
     * @param temp_y tọa độ trái trên y
     * @return có hoặc không đứng được
     */
    boolean checkCanMove(double temp_x, double temp_y) {
        int x1 = (int) (temp_x / PvB_GamePlay.map.cellLength);
        int x2 = (int) ((temp_x + this.getWidth() - 1) / PvB_GamePlay.map.cellLength);
        int y1 = (int) (temp_y / PvB_GamePlay.map.cellLength);
        int y2 = (int) ((temp_y + this.getLength() - 1) / PvB_GamePlay.map.cellLength);

        //gặp bomb
        for (Bomb bomb : PvB_GamePlay.bombs) {
            if (bomb.checkIntersect(temp_x, temp_x + this.getWidth() - 1,
                    temp_y, temp_y + this.getLength() - 1) &&
                    bomb.checkBlockStatusWithObject(this)) {
                return false;
            }
        }

        //đứng ở ô không cho phép
        return !PvB_GamePlay.map.isBlockCell(y1, x1) &&
                !PvB_GamePlay.map.isBlockCell(y1, x2) &&
                !PvB_GamePlay.map.isBlockCell(y2, x1) &&
                !PvB_GamePlay.map.isBlockCell(y2, x2);
    }

    /**
     * Xử lí object di chuyển.
     */
    public void move() {
        calculateDistance();

        if (deltaX == 0 && deltaY == 0) {
            return;
        }

        double cellLength = PvB_GamePlay.map.cellLength;

        double nowX = getX();
        double nowY = getY();

        //Biến này cộng vào tọa độ để object luôn nằm giữa cell.
        double adjustPosition = (cellLength - this.getLength()) / 2;

        if (currentState == ObjectMovementState.HORIZONTAL_) {
            if (currentDirection == ObjectDirection.LEFT_ || currentDirection == ObjectDirection.RIGHT_) {
                if (deltaX != 0 && checkCanMove(nowX + deltaX, nowY)) {
                    setX(nowX + deltaX);
                }
            } else if (currentDirection == ObjectDirection.UP_ || currentDirection == ObjectDirection.DOWN_) {
                int temp_x = (int) (this.getCenterX() / cellLength);

                double newPositionX = temp_x * cellLength + adjustPosition;

                if (deltaY != 0 && checkCanMove(newPositionX, nowY + cellLength * (deltaY > 0 ? 1 : -1))) {
                    setX(newPositionX);
                    setY(nowY + deltaY);

                    currentState = ObjectMovementState.VERTICAL_;
                }
            }
        } else if (currentState == ObjectMovementState.VERTICAL_) {
            if (currentDirection == ObjectDirection.UP_ || currentDirection == ObjectDirection.DOWN_) {
                if (deltaY != 0 && checkCanMove(nowX, nowY + deltaY)) {
                    setY(nowY + deltaY);
                }
            } else if (currentDirection == ObjectDirection.LEFT_ || currentDirection == ObjectDirection.RIGHT_) {
                int temp_y = (int) (this.getCenterY() / cellLength);

                double newPositionY = temp_y * cellLength + adjustPosition;

                if (deltaX != 0 && checkCanMove(nowX + cellLength * (deltaX > 0 ? 1 : -1), newPositionY)) {
                    setX(nowX + deltaX);
                    setY(newPositionY);

                    currentState = ObjectMovementState.HORIZONTAL_;
                }
            }
        }
    }

}