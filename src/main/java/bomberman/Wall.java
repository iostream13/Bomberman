package bomberman;

import javafx.scene.image.Image;

public class Wall extends Object{
    public Wall(float x, float y, float width, float length) {
        super(x, y, width, length);
    }

    @Override
    public Image getImage() {
        return Images.Wall;
    }
}
