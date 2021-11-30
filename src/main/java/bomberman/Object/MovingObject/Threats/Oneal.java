package bomberman.Object.MovingObject.Threats;

import bomberman.GlobalVariable.FilesPath;

public class Oneal extends Enemy {
    /**
     * Constructor cho Oneal.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Oneal(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    /**
     * Constructor cho Oneal.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     */
    public Oneal(double x, double y) {
        super(x, y);
    }

    @Override
    public void setGraphicData() {
        setNumberOfFrame(8);
        setNumberOfGameFramePerFrame(3);

        setImageData(FilesPath.OnealUp, FilesPath.OnealDown, FilesPath.OnealLeft, FilesPath.OnealRight);
    }
}
