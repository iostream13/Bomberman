package bomberman.Object.NonMovingObject;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.FilesPath;

import bomberman.Object.GameObject;

public class Wall extends GameObject {
    /**
     * Constructor cho Wall.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Wall(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    @Override
    public Image getImage() {
        return FilesPath.Wall;
    }

    @Override
    public void setGraphicData() {
        setNumberOfFrame(8);
        setNumberOfGameFramePerFrame(3);
    }
}
