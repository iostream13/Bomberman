package bomberman.Object.MovingObject.Threats;

import bomberman.GlobalVariable.ImagesPath;
import javafx.scene.image.Image;

public class Balloom extends Enemy {
    /**
     * Constructor cho Balloom.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Balloom(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    /**
     * Constructor cho Balloom.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     */
    public Balloom(double x, double y) {
        super(x, y);
    }

    @Override
    public Image getImage() {
        return ImagesPath.Balloom;
    }
}
