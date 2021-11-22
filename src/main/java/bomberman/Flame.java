package bomberman;

import javafx.scene.image.Image;

public class Flame extends Object{

    public static enum flameTypes {
        left,
        right,
        up,
        down,
        middle,
        horizontal,
        vertical,
    }

    flameTypes type;
    public long startTime;
    public final long duration = 500000000;

    public Flame(float x, float y, float width, float length) {
        super(x, y, width, length);
    }

    public Flame(float x, float y, float width, float length, flameTypes type) {
        super(x, y, width, length);
        this.type = type;
        startTime = System.nanoTime();
    }

    // xử lý flame nổ vào các ô
    public static void handleIntersectCell(Object cell) {
        if (cell instanceof Item) {
            if (((Item) cell).isHidden) {
                ((Item) cell).isHidden = false;
                ((Item) cell).exploding = true;
                ((Item) cell).startExplodingTime = System.nanoTime();
            }
        }

        if (cell instanceof Brick) {
            if (((Brick) cell).exploded == false) {
                ((Brick) cell).exploding = true;
                ((Brick) cell).startExplodingTime = System.nanoTime();
            }
        }

        if (cell instanceof Portal) {
            if (((Portal) cell).isBlocked) {
                ((Portal) cell).exploding = true;
                ((Portal) cell).startExplodingTime = System.nanoTime();
            }
        }
    }
    public void checkIntersectCells() {
        int x1 = (int) (this.getX() / GamePlay.map.cellLength);
        int x2 = (int) ((this.getX() + this.getWidth() - 1) / GamePlay.map.cellLength);
        int y1 = (int) (this.getY() / GamePlay.map.cellLength);
        int y2 = (int) ((this.getY() + this.getLength() - 1) / GamePlay.map.cellLength);

        for (int i=y1;i<=y2;i++) for (int j=x1;j<=x2;j++) handleIntersectCell(GamePlay.map.cells[i][j]);
    }

    boolean checkExpired() {
        return (System.nanoTime() - startTime >= duration);
    }

    @Override
    public Image getImage() {
        switch (type) {
            case left: return Images.FlameLeft;
            case right: return Images.FlameRight;
            case up: return Images.FlameUp;
            case down: return Images.FlameDown;
            case middle: return Images.FlameMid;
            case vertical: return Images.FlameVertical;
            case horizontal: return Images.FlameHorizontal;
        }
        return Images.Brick;
    }
}
