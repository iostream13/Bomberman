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

    // MOVEMENT STATE ---------------------------------------------------------------------------

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
     * Trạng thái đang đi sang trái hoặc không.
     */
    private boolean LEFT_STATE;

    /**
     * Trạng thái đang đi sang phải hoặc không.
     */
    private boolean RIGHT_STATE;

    /**
     * Trạng thái đang đi lên hoặc không.
     */
    private boolean UP_STATE;

    /**
     * Trạng thái đang đi xuống hoặc không.
     */
    private boolean DOWN_STATE;

    /**
     * Set trạng thái di chuyển cho object.
     *
     * @param objectDirection hướng di chuyển
     * @param tempState       trạng thái có hoặc không
     */
    public void setObjectDirection(ObjectDirection objectDirection, boolean tempState) {
        switch (objectDirection) {
            case LEFT_:
                LEFT_STATE = tempState;
                break;
            case RIGHT_:
                RIGHT_STATE = tempState;
                break;
            case UP_:
                UP_STATE = tempState;
                break;
            case DOWN_:
                DOWN_STATE = tempState;
                break;
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * Tốc độ của object.
     */
    private double speed = 2.5; // DEFAULT SPEED

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

        if (LEFT_STATE) {
            deltaX -= speed;
        }

        if (RIGHT_STATE) {
            deltaX += speed;
        }

        if (UP_STATE) {
            deltaY -= speed;
        }

        if (DOWN_STATE) {
            deltaY += speed;
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
        int x1 = (int) (temp_x / GamePlay.map.cellLength);
        int x2 = (int) ((temp_x + this.getWidth() - 1) / GamePlay.map.cellLength);
        int y1 = (int) (temp_y / GamePlay.map.cellLength);
        int y2 = (int) ((temp_y + this.getLength() - 1) / GamePlay.map.cellLength);

        //gặp bomb
        for (Bomb bomb : GamePlay.bombs) {
            if (bomb.checkIntersect(temp_x, temp_x + this.getWidth() - 1,
                                    temp_y, temp_y + this.getLength() - 1) &&
                    bomb.checkBlockStatusWithObject(this)) {
                return false;
            }
        }

        //đứng ở ô không cho phép
        return !GamePlay.map.isBlockCell(y1, x1) &&
                !GamePlay.map.isBlockCell(y1, x2) &&
                !GamePlay.map.isBlockCell(y2, x1) &&
                !GamePlay.map.isBlockCell(y2, x2);
    }

    /**
     * Xử lí object di chuyển.
     */
    public void move() {
        calculateDistance();

        if (deltaX == 0 && deltaY == 0) {
            return;
        }

        double result_x = this.getX();
        double result_y = this.getY();

        if (checkCanMove(this.getX() + deltaX, this.getY())) {
            result_x += deltaX;
        }

        if (checkCanMove(this.getX(), this.getY() + deltaY)) {
            result_y += deltaY;
        }

        setX(result_x);
        setY(result_y);

        //nếu người di chuyển ra khỏi bom mình đặt, đổi quả bom sang trạng thái block object
        for (Bomb x : GamePlay.bombs) {
            if (!x.checkIntersect(x.getOwner()) && !x.getBlockStatus()) {
                x.setBlockStatus(true);
            }
        }
    }
}
