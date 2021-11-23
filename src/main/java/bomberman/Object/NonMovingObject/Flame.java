package bomberman.Object.NonMovingObject;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.ImagesPath;

import bomberman.GamePlay;

import bomberman.Object.GameObject;

public class Flame extends GameObject {
    /**
     * List of flame type.
     */
    public enum FlameType {
        LEFT_,
        RIGHT_,
        UP_,
        DOWN_,
        CENTER_,
        HORIZONTAL_,
        VERTICAL_
    }

    /**
     * This flame's type.
     */
    private FlameType type;

    /**
     * Flame start time.
     */
    private long startTime;

    /**
     * Flame duration.
     */
    private final long duration = 500000000; // 0.5 giây

    /**
     * Constructor cho flame.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Flame(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    /**
     * Constructor cho flame.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     * @param type   loại của flame
     */
    public Flame(double x, double y, double width, double length, FlameType type) {
        super(x, y, width, length);

        this.type = type;

        startTime = System.nanoTime();
    }

    /**
     * Xử lý flame nổ vào các ô.
     */
    public static void handleIntersectCell(GameObject cell) {
        if (cell instanceof Item) {
            if (((Item) cell).isInitialState()) {
                ((Item) cell).setBlockState(Block.BlockState.EXPLODING_STATE_);
                ((Item) cell).setStartExplodingTime(System.nanoTime());
            }
        }

        if (cell instanceof Brick) {
            if (((Brick) cell).isInitialState()) {
                ((Brick) cell).setBlockState(Block.BlockState.EXPLODING_STATE_);
                ((Brick) cell).setStartExplodingTime(System.nanoTime());
            }
        }

        if (cell instanceof Portal) {
            if (((Portal) cell).isInitialState()) {
                ((Portal) cell).setBlockState(Block.BlockState.EXPLODING_STATE_);
                ((Portal) cell).setStartExplodingTime(System.nanoTime());
            }
        }
    }

    /**
     * Check xem ô nào bị nổ.
     */
    public void checkIntersectCells() {
        int x1 = (int) (this.getX() / GamePlay.map.cellLength);
        int x2 = (int) ((this.getX() + this.getWidth() - 1) / GamePlay.map.cellLength);
        int y1 = (int) (this.getY() / GamePlay.map.cellLength);
        int y2 = (int) ((this.getY() + this.getLength() - 1) / GamePlay.map.cellLength);

        for (int i = y1; i <= y2; i++)
            for (int j = x1; j <= x2; j++) {
                handleIntersectCell(GamePlay.map.cells[i][j]);
            }
    }

    /**
     * Check xem hết thời gian flame chưa.
     *
     * @return chưa hoặc rồi
     */
    public boolean checkExpired() {
        return (System.nanoTime() - startTime >= duration);
    }

    @Override
    public Image getImage() {
        switch (type) {
            case LEFT_:
                return ImagesPath.FlameLeft;
            case RIGHT_:
                return ImagesPath.FlameRight;
            case UP_:
                return ImagesPath.FlameUp;
            case DOWN_:
                return ImagesPath.FlameDown;
            case CENTER_:
                return ImagesPath.FlameMid;
            case VERTICAL_:
                return ImagesPath.FlameVertical;
            case HORIZONTAL_:
                return ImagesPath.FlameHorizontal;
        }

        return ImagesPath.Brick;
    }
}
