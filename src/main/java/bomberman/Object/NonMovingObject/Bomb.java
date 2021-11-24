package bomberman.Object.NonMovingObject;

import javafx.scene.image.Image;

import bomberman.GlobalVariable.ImagesPath;

import bomberman.GamePlay;

import bomberman.Object.MovingObject.Bomber.Bomber;
import bomberman.Object.GameObject;

public class Bomb extends GameObject {
    /**
     * Owner of the bomb.
     */
    private Bomber owner;

    /**
     * Bomb hiện tại có block bomber hay không.
     * (Khi bomber đi ra khỏi quả bomb vừa tạo bomb sẽ trở thành 1 block)
     */
    private boolean blockStatus;

    /**
     * Explosion start time.
     */
    private long startTime;

    /**
     * Explosion duration.
     */
    private final long EXISTENCE_DURATION = 2000000000; // 2 giây

    public Bomber getOwner() {
        return owner;
    }

    public boolean getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(boolean blockStatus) {
        this.blockStatus = blockStatus;
    }

    /**
     * Constructor cho Bomb.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Bomb(double x, double y, double width, double length) {
        super(x, y, width, length);

        blockStatus = false;
    }

    /**
     * Constructor cho Bomb.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     * @param owner  chủ thể của quả bom
     */
    public Bomb(double x, double y, double width, double length, Bomber owner) {
        super(x, y, width, length);

        this.owner = owner;
        this.startTime = System.nanoTime();

        blockStatus = false;
    }

    @Override
    public Image getImage() {
        return ImagesPath.Bomb;
    }

    /**
     * Kiểm tra đã đến thời gian nổ chưa.
     */
    public boolean checkExplode() {
        return (System.nanoTime() - startTime >= EXISTENCE_DURATION);
    }

    /**
     * Check xem bomb có block object này không.
     * (chỉ không block khi owner mới đặt bomb, khi owner đi ra khỏi bomb sẽ bị block)
     *
     * @param tempObject object cần check
     * @return có block hoặc không
     */
    public boolean checkBlockStatusWithObject(GameObject tempObject) {
        if (tempObject != owner) {
            return true;
        }

        return blockStatus;
    }

    /**
     * Sinh ra các flame.
     */
    public void createFlame() {
        int x = (int) ((int) this.getX() / GamePlay.map.cellLength);
        int y = (int) ((int) this.getY() / GamePlay.map.cellLength);

        int len = owner.flameLength;
        int side = (int) GamePlay.map.cellLength;

        // sinh flame ra bên trái bom
        for (int i = x - 1; i >= 0 && i >= x - len; i--) {
            // gặp cô cản, ngừng sinh flame
            if (GamePlay.map.isBlockCell(y, i)) {
                Flame.handleIntersectCell(GamePlay.map.cells[y][i]);

                break;
            }

            if (i == x - len) {
                GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.LEFT_));
            } else {
                GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.HORIZONTAL_));
            }
        }

        // sinh flame ra bên phải bom
        for (int i = x + 1; i <= GamePlay.map.numberOfColumn && i <= x + len; i++) {
            // gặp cô cản, ngừng sinh flame
            if (GamePlay.map.isBlockCell(y, i)) {
                Flame.handleIntersectCell(GamePlay.map.cells[y][i]);

                break;
            }

            if (i == x + len) {
                GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.RIGHT_));
            } else {
                GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.HORIZONTAL_));
            }
        }

        // sinh flame ra bên trên bom
        for (int i = y - 1; i >= 0 && i >= y - len; i--) {
            // gặp cô cản, ngừng sinh flame
            if (GamePlay.map.isBlockCell(i, x)) {
                Flame.handleIntersectCell(GamePlay.map.cells[i][x]);

                break;
            }

            if (i == y - len) {
                GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.UP_));
            } else {
                GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.VERTICAL_));
            }
        }

        // sinh flame ra bên dưới bom
        for (int i = y + 1; i <= GamePlay.map.numberOfRow && i <= y + len; i++) {
            // gặp cô cản, ngừng sinh flame
            if (GamePlay.map.isBlockCell(i, x)) {
                Flame.handleIntersectCell(GamePlay.map.cells[i][x]);

                break;
            }

            if (i == y + len) {
                GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.DOWN_));
            } else {
                GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.VERTICAL_));
            }
        }

        //sinh flame ở chính giữa
        GamePlay.flames.add(new Flame(x * side, y * side, side, side, Flame.FlameType.CENTER_));
    }
}
