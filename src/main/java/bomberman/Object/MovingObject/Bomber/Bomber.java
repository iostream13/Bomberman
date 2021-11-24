package bomberman.Object.MovingObject.Bomber;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.ImagesPath;

import bomberman.GamePlay;

import bomberman.Object.GameObject;
import bomberman.Object.MovingObject.MovingObject;
import bomberman.Object.NonMovingObject.Bomb;
import bomberman.Object.NonMovingObject.Item;

public class Bomber extends MovingObject {
    /**
     * Số bomb có thể đặt được.
     */
    public int maxBomb = 1;

    /**
     * Số lượng bomb đang đặt.
     */
    public int currentBomb = 0;

    /**
     * Số ô mà flame do nhân vật tạo ra, ăn buff thì tăng 1.
     */
    public int flameLength = 1;

    /**
     * Constructor cho Bomber.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     */
    public Bomber(double x, double y) {
        super(x, y, 35, 35); // Kích thước mặc định
    }

    /**
     * Constructor cho Bomber.
     *
     * @param x      tạo độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Bomber(double x, double y, double width, double length) {
        super(x, y, width, length);
    }

    /**
     * Check xem còn bomb để đặt không.
     *
     * @return có hoặc không
     */
    public boolean canCreateBomb() {
        return currentBomb < maxBomb;
    }

    /**
     * Tạo bomb.
     */
    public void createBomb() {
        int x =
                (int)
                        ((int) ((this.getX() + this.getWidth() / 2) / GamePlay.map.cellLength)
                                * GamePlay.map.cellLength);
        int y =
                (int)
                        ((int) ((this.getY() + this.getLength() / 2) / GamePlay.map.cellLength)
                                * GamePlay.map.cellLength);

        currentBomb++;

        GamePlay.bombs.add(new Bomb(x, y, GamePlay.map.cellLength, GamePlay.map.cellLength, this));
    }

    /**
     * Kiểm tra trên phạm vi đang đứng có item không, nếu có thì thực hiện ăn.
     */
    public void checkEatItems() {
        int x1 = (int) (this.getX() / GamePlay.map.cellLength);
        int x2 = (int) ((this.getX() + this.getWidth() - 1) / GamePlay.map.cellLength);
        int y1 = (int) (this.getY() / GamePlay.map.cellLength);
        int y2 = (int) ((this.getY() + this.getLength() - 1) / GamePlay.map.cellLength);

        for (int i = y1; i <= y2; i++)
            for (int j = x1; j <= x2; j++) {
                GameObject now = GamePlay.map.cells[i][j];

                if (!(now instanceof Item)) {
                    continue;
                }

                if (!((Item) now).isFinalState() || ((Item) now).getAteStatus()) {
                    continue;
                }

                switch (((Item) now).getType()) {
                    case BOMB_ITEM_:
                        maxBomb++;
                        break;
                    case SPEED_ITEM_:
                        setSpeed(getSpeed() + 1);
                        break;
                    case FLAME_ITEM_:
                        flameLength++;
                        break;
                }

                ((Item) now).setAteStatus(true);
            }
    }

    @Override
    public Image getImage() {
        return ImagesPath.Player;
    }
}
