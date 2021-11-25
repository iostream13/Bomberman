package bomberman.Object.NonMovingObject;

import bomberman.Object.MovingObject.MovingObject;
import bomberman.Object.MovingObject.Threats.Enemy;
import javafx.scene.image.Image;

import bomberman.GlobalVariable.ImagesPath;

import bomberman.PvB_GamePlay;

import bomberman.Object.MovingObject.Bomber.Bomber;
import bomberman.Object.GameObject;

import java.util.ArrayList;

public class Bomb extends GameObject {
    /**
     * Owner of the bomb.
     */
    private Bomber owner;

    /**
     * Explosion start time.
     */
    private long startTime;

    private ArrayList<MovingObject> unblockObject = new ArrayList<>();

    /**
     * Explosion duration.
     */
    private final long EXISTENCE_DURATION = 2000000000; // 2 giây

    public Bomber getOwner() {
        return owner;
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

        for (Bomber X : PvB_GamePlay.players) {
            if (X.checkIntersect(this)) {
                unblockObject.add(X);
            }
        }

        for (Enemy X : PvB_GamePlay.enemies) {
            if (X.checkIntersect(this)) {
                unblockObject.add(X);
            }
        }
    }

    public void updateUnblockList() {
        for (int i = 0; i < PvB_GamePlay.players.size(); i++) {
            MovingObject X = PvB_GamePlay.players.get(i);
            if (unblockObject.contains(X) && !X.checkIntersect(this)) {
                unblockObject.remove(X);
                i--;
            }
        }

        for (int i = 0; i < PvB_GamePlay.enemies.size(); i++) {
            MovingObject X = PvB_GamePlay.enemies.get(i);
            if (unblockObject.contains(X) && !X.checkIntersect(this)) {
                unblockObject.remove(X);
                i--;
            }
        }
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

        for (Bomber X : PvB_GamePlay.players) {
            if (X.checkIntersect(this)) {
                unblockObject.add(X);
            }
        }

        for (Enemy X : PvB_GamePlay.enemies) {
            if (X.checkIntersect(this)) {
                unblockObject.add(X);
            }
        }
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
    public boolean checkBlockStatusWithObject(MovingObject tempObject) {
        return !unblockObject.contains(tempObject);
    }

    /**
     * Sinh ra các flame.
     */
    public void createFlame() {
        int x = (int) ((int) this.getX() / PvB_GamePlay.map.cellLength);
        int y = (int) ((int) this.getY() / PvB_GamePlay.map.cellLength);

        int len = owner.flameLength;
        int side = (int) PvB_GamePlay.map.cellLength;

        // sinh flame ra bên trái bom
        for (int i = x - 1; i >= 0 && i >= x - len; i--) {
            // gặp cô cản, ngừng sinh flame
            if (PvB_GamePlay.map.isBlockCell(y, i)) {
                Flame.handleIntersectCell(PvB_GamePlay.map.cells[y][i]);

                break;
            }

            if (i == x - len) {
                PvB_GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.LEFT_));
            } else {
                PvB_GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.HORIZONTAL_));
            }
        }

        // sinh flame ra bên phải bom
        for (int i = x + 1; i <= PvB_GamePlay.map.numberOfColumn && i <= x + len; i++) {
            // gặp cô cản, ngừng sinh flame
            if (PvB_GamePlay.map.isBlockCell(y, i)) {
                Flame.handleIntersectCell(PvB_GamePlay.map.cells[y][i]);

                break;
            }

            if (i == x + len) {
                PvB_GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.RIGHT_));
            } else {
                PvB_GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.FlameType.HORIZONTAL_));
            }
        }

        // sinh flame ra bên trên bom
        for (int i = y - 1; i >= 0 && i >= y - len; i--) {
            // gặp cô cản, ngừng sinh flame
            if (PvB_GamePlay.map.isBlockCell(i, x)) {
                Flame.handleIntersectCell(PvB_GamePlay.map.cells[i][x]);

                break;
            }

            if (i == y - len) {
                PvB_GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.UP_));
            } else {
                PvB_GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.VERTICAL_));
            }
        }

        // sinh flame ra bên dưới bom
        for (int i = y + 1; i <= PvB_GamePlay.map.numberOfRow && i <= y + len; i++) {
            // gặp cô cản, ngừng sinh flame
            if (PvB_GamePlay.map.isBlockCell(i, x)) {
                Flame.handleIntersectCell(PvB_GamePlay.map.cells[i][x]);

                break;
            }

            if (i == y + len) {
                PvB_GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.DOWN_));
            } else {
                PvB_GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.FlameType.VERTICAL_));
            }
        }

        //sinh flame ở chính giữa
        PvB_GamePlay.flames.add(new Flame(x * side, y * side, side, side, Flame.FlameType.CENTER_));
    }
}
