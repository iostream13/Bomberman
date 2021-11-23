package bomberman;

import javafx.scene.image.Image;

public class Portal extends Object{
    public boolean isBlocked = true;
    public boolean exploding = false;
    public long startExplodingTime;
    public final long explodingDuration = 500000000;

    public boolean checkExplodingExpired() {
        return (exploding && System.nanoTime() - startExplodingTime >= explodingDuration);
    }
    public Portal(float x, float y, float width, float length) {
        super(x, y, width, length);
    }

    @Override
    public Image getImage() {
        if (exploding) {
            return Images.BrickExploded;
        }
        if (isBlocked) {
            return Images.Brick;
        }
        return Images.Portal;
    }
}
