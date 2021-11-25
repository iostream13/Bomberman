package bomberman.Object.MovingObject.Threats;

import bomberman.GlobalVariable.ImagesPath;
import javafx.scene.image.Image;

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
    public Image getImage() {
        return ImagesPath.Oneal;
    }
}
