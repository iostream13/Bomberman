package bomberman.Object.NonMovingObject;

import bomberman.GlobalVariable.FilesPath;

public class Brick extends Block {
    /**
     * Constructor cho Brick.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Brick(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    @Override
    public void setFinalStateImageInfo() {
        FINAL_STATE_IMAGE = FilesPath.Grass;
    }

    @Override
    public void setGraphicData() {
        setNumberOfFrame(8);
        setNumberOfGameFramePerFrame(3);
    }
}
