package bomberman.Object.NonMovingObject;

import bomberman.GlobalVariable.FilesPath;

public class Portal extends Block {
    /**
     * Constructor cho Portal.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Portal(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    @Override
    public void setFinalStateImageInfo() {
        FINAL_STATE_IMAGE = FilesPath.Portal;
    }

    @Override
    public void setGraphicData() {
        setNumberOfFrame(8);
        setNumberOfGameFramePerFrame(3);
    }
}
