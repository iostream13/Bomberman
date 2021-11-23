package bomberman.Object.NonMovingObject;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.ImagesPath;

import bomberman.Object.GameObject;

public class Grass extends GameObject {
    /**
     * Constructor cho Grass.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Grass(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    @Override
    public Image getImage() {
        return ImagesPath.Grass;
    }
}
