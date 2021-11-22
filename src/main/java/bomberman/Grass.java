package bomberman;

import javafx.scene.image.Image;

public class Grass extends Object{
    public Grass(float x, float y, float width, float length) {
        super(x, y, width, length);
    }

    @Override
    public Image getImage() {
        return Images.Grass;
    }
}
