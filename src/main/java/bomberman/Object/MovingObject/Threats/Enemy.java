package bomberman.Object.MovingObject.Threats;

import bomberman.Object.MovingObject.MovingObject;

public class Enemy extends MovingObject {
    /**
     * Constructor cho Enemy.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Enemy(double x, double y, double width, double length) {
        super(x, y, width, length);
    }
}
