package bomberman;

public abstract class MovingObject extends Object{
    public MovingObject(float x, float y, float width, float length) {
        super(x, y, width, length);
    }

    public static enum orientations {
        left,
        right,
        up,
        down,
    }

    orientations currentOrientation;
    private float speed = 20;
    private float x_distance;
    private float y_distance;

    public orientations getCurrentOrientation() {
        return currentOrientation;
    }

    public void setCurrentOrientation(orientations currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    //tính khoảng cách tối đa có thể di chuyển
    public void calculateDistance() {
        x_distance = 0;
        y_distance = 0;

        switch (currentOrientation) {
            case left: x_distance -= speed; break;
            case right: x_distance += speed; break;
            case up: y_distance -= speed; break;
            case down: y_distance += speed; break;
        }
    }

    //kiểm tra xem có thể đứng ở vị trí  hiện tại không
    boolean checkCanMove() {
        int x1 = (int) (this.getX() / GamePlay.map.cellLength);
        int x2 = (int) ((this.getX() + this.getWidth() - 1) / GamePlay.map.cellLength);
        int y1 = (int) (this.getY() / GamePlay.map.cellLength);
        int y2 = (int) ((this.getY() + this.getLength() - 1) / GamePlay.map.cellLength);

        //đứng ở ô không cho phép
        if (GamePlay.map.isBlockCell(y1,x1)) return false;
        if (GamePlay.map.isBlockCell(y1,x2)) return false;
        if (GamePlay.map.isBlockCell(y2,x1)) return false;
        if (GamePlay.map.isBlockCell(y2,x2)) return false;

        //gặp bomb
        for (Bomb x: GamePlay.bombs) {
            if (x.checkIntersect(this) && x.isBlocked) return false;
        }

        return true;
    }

    //hàm di chuyển, cần code lại, chỗ này đang for trâu và còn for ngược nữa :v
    public void move() {
        calculateDistance();
        float preX = this.getX();
        float preY = this.getY();
        this.setX(this.getX() + x_distance);
        this.setY(this.getY() + y_distance);

        while (!checkCanMove() && (x_distance + y_distance != 0)) {
            if (x_distance < 0) x_distance++;
            if (x_distance > 0) x_distance--;
            if (y_distance < 0) y_distance++;
            if (y_distance > 0) y_distance--;
            setX(preX + x_distance);
            setY(preY + y_distance);
        }

        //nếu người di chuyển ra khỏi bom mình đặt, đổi quả bom sang trạng thái block object
        for (Bomb x: GamePlay.bombs) {
            if (!x.checkIntersect(x.owner)) x.isBlocked = true;
        }
    }
}
