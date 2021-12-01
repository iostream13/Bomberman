package bomberman.Object.NonMovingObject;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.FilesPath;
import bomberman.GlobalVariable.GameVariables;

import bomberman.Map.PlayGround;

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
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     * @param width    chiều rộng
     * @param length   chiều dài
     */
    public Flame(PlayGround belongTo, double x, double y, double width, double length) {
        super(belongTo, x, y, width, length);
    }

    /**
     * Constructor cho flame.
     *
     * @param belongTo tham chiếu tới PlayGround
     * @param x        tọa độ x
     * @param y        tọa độ y
     * @param width    chiều rộng
     * @param length   chiều dài
     * @param type     loại của flame
     */
    public Flame(PlayGround belongTo, double x, double y, double width, double length, FlameType type) {
        super(belongTo, x, y, width, length);

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
        int x1 = GameVariables.calculateCellIndex(this.getX());
        int x2 = GameVariables.calculateCellIndex(this.getX() + this.getWidth() - 1);
        int y1 = GameVariables.calculateCellIndex(this.getY());
        int y2 = GameVariables.calculateCellIndex(this.getY() + this.getLength() - 1);

        for (int i = y1; i <= y2; i++)
            for (int j = x1; j <= x2; j++) {
                handleIntersectCell(this.getBelongTo().getCells(i, j));
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
                return FilesPath.FlameLeft;
            case RIGHT_:
                return FilesPath.FlameRight;
            case UP_:
                return FilesPath.FlameUp;
            case DOWN_:
                return FilesPath.FlameDown;
            case CENTER_:
                return FilesPath.FlameMid;
            case VERTICAL_:
                return FilesPath.FlameVertical;
            case HORIZONTAL_:
                return FilesPath.FlameHorizontal;
        }

        return FilesPath.Brick;
    }

    @Override
    public void setGraphicData() {
        setNumberOfFrame(8);
        setNumberOfGameFramePerFrame(3);
    }
}
