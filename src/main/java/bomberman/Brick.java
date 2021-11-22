package bomberman;

import javafx.scene.image.Image;

public class Brick extends Object{
    public boolean exploded = false;
    public boolean exploding = false;
    public long startExplodingTime;
    public final long explodingDuration = 500000000;
    public Brick(float x, float y, float width, float length) {
        super(x, y, width, length);
    }

    public boolean checkExplodingExpired() {
        return (exploding && System.nanoTime() - startExplodingTime >= explodingDuration);
    }

    @Override
    public Image getImage() {
        if (exploding) return Images.BrickExploded;
        if (exploded) return Images.Grass;
        return Images.Brick;
    }
}
