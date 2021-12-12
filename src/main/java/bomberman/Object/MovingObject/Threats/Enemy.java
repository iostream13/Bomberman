package bomberman.Object.MovingObject.Threats;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import bomberman.GlobalVariable.GameVariables;

import bomberman.Map.PlayGround;

import bomberman.Object.MovingObject.MovingObject;

public abstract class Enemy extends MovingObject {
    private int type = 1;
    public boolean moveRandom = true;
    /**
     * Thời gian di chuyển theo hướng hiện tại
     */
    private final long duration = 250000000; // 0.25 giây

    private long startTime = System.nanoTime();

    /**
     * Constructor cho Enemy.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     * @param width    chiều rộng
     * @param length   chiều dài
     */
    public Enemy(PlayGround belongTo, double x, double y, double width, double length) {
        super(belongTo, x, y, width, length);

        setSpeed(2);
    }

    /**
     * Constructor cho Enemy.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     */
    public Enemy(PlayGround belongTo, double x, double y) {
        super(belongTo, x, y, 35, 35); // Kích thước mặc định

        setSpeed(2);
    }

    @Override
    public void move() {
        if (!moveRandom) {
            super.move();
            return;
        }
        if (System.nanoTime() - startTime >= duration) {
            boolean toUp, toRight, toDown, toLeft;

            toRight = ThreadLocalRandom.current().nextBoolean();

            if (toRight) {
                toLeft = false;
            } else {
                toLeft = ThreadLocalRandom.current().nextBoolean();
            }

            toUp = ThreadLocalRandom.current().nextBoolean();

            if (!toUp && !toRight && !toLeft) {
                toDown = true;
            } else if (toUp) {
                toDown = false;
            } else {
                toDown = ThreadLocalRandom.current().nextBoolean();
            }

            setObjectDirection(MovingObject.ObjectDirection.RIGHT_, toRight);
            setObjectDirection(MovingObject.ObjectDirection.LEFT_, toLeft);
            setObjectDirection(MovingObject.ObjectDirection.UP_, toUp);
            setObjectDirection(MovingObject.ObjectDirection.DOWN_, toDown);

            startTime = System.nanoTime();
        }

        super.move();
    }

    public void die() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
