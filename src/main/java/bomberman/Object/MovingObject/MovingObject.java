package bomberman.Object.MovingObject;

import java.util.ArrayList;

import bomberman.GlobalVariable.GameVariables;

import bomberman.PvB_GamePlay;

import bomberman.Object.NonMovingObject.Bomb;
import bomberman.Object.GameObject;
import javafx.scene.image.Image;

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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Độ lệch x giữa điểm cần di chuyển tới và điểm hiện tại.
     */
    private double deltaX;

    /**
     * Độ lệch y giữa điểm cần di chuyển tới và điểm hiện tại.
     */
    private double deltaY;

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
        int x1 = GameVariables.calculateCellIndex(temp_x);
        int x2 = GameVariables.calculateCellIndex(temp_x + this.getWidth() - 1);
        int y1 = GameVariables.calculateCellIndex(temp_y);
        int y2 = GameVariables.calculateCellIndex(temp_y + this.getLength() - 1);

        //gặp bomb
        for (Bomb bomb : PvB_GamePlay.map.bombs) {
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

        double cellLength = GameVariables.cellLength;

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
                int temp_x = GameVariables.calculateCellIndex(this.getCenterX());

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
                int temp_y = GameVariables.calculateCellIndex(this.getCenterY());

                double newPositionY = temp_y * cellLength + adjustPosition;

                if (deltaX != 0 && checkCanMove(nowX + cellLength * (deltaX > 0 ? 1 : -1), newPositionY)) {
                    setX(nowX + deltaX);
                    setY(newPositionY);

                    currentState = ObjectMovementState.HORIZONTAL_;
                }
            }
        }
    }

    // *************************** GRAPHIC **********************************************************

    // Các biến hình ảnh (Cần được cấp thông tin trong setGraphicInformation
    protected Image leftImage;
    protected Image rightImage;
    protected Image upImage;
    protected Image downImage;
    protected Image lastImage;

    /**
     * Nhập ảnh. (Phải được sử dụng trong setGraphicData)
     */
    public void setImageData(Image tempUp, Image tempDown, Image tempLeft, Image tempRight) {
        upImage = tempUp;
        downImage = tempDown;
        leftImage = tempLeft;
        rightImage = tempRight;
        lastImage = leftImage;
    }

    @Override
    public Image getImage() {
        switch (currentDirection) {
            case LEFT_:
                lastImage = leftImage;
                break;
            case RIGHT_:
                lastImage = rightImage;
                break;
            case UP_:
                lastImage = upImage;
                break;
            case DOWN_:
                lastImage = downImage;
                break;
        }

        return lastImage;
    }

    @Override
    public void draw() {
        if (currentDirection == ObjectDirection.NONE_) {
            this.setGameFrameCount(0);
        }

        super.draw();
    }
}