package bomberman;

import javafx.scene.image.Image;

public class Item extends Object{
    public static enum typeOfItems {
        BombItem,
        FlameItem,
        SpeedItem,
    }

    public boolean isHidden = true;
    public boolean exploding = false;
    public boolean showingItem = false;
    public boolean ate = false;
    public long startExplodingTime;
    public final long explodingDuration = 500000000;
    typeOfItems type;

    public boolean checkExplodingExpired() {
        return (exploding && System.nanoTime() - startExplodingTime >= explodingDuration);
    }

    public Item(float x, float y, float width, float length) {
        super(x, y, width, length);
    }

    public Item(float x, float y, float width, float length, typeOfItems type) {
        super(x, y, width, length);
        this.type = type;
    }

    @Override
    public Image getImage() {
        if (isHidden) return Images.Brick;
        if (exploding) return Images.BrickExploded;
        if (showingItem) {
            if (type == typeOfItems.BombItem) return Images.PowerupBomb;
            else if (type == typeOfItems.FlameItem) return Images.PowerupFlame;
            else return Images.PowerupSpeed;
        }
        return Images.Grass;
    }
}
