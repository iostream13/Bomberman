package bomberman;

public class Character {
    private float x;
    private float y;
    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    public final int rect_w = 30;
    public final int rect_h = 30;
    public Character (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
