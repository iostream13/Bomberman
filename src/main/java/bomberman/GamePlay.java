package bomberman;

import java.util.ArrayList;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import bomberman.Object.*;
import bomberman.Object.Map.PlayGround;
import bomberman.Object.MovingObject.Bomber.Bomber;
import bomberman.Object.MovingObject.Threats.Enemy;
import bomberman.Object.MovingObject.MovingObject;
import bomberman.Object.NonMovingObject.*;

public class GamePlay {
    /**
     * Trạng thái game (đang chơi, thắng, thua).
     */
    public enum gameStatusType {
        PLAYING_,
        WON_,
        LOSE_
    }

    /**
     * Trạng thái game hiện tại.
     */
    public static gameStatusType gameStatus;

    /**
     * Player.
     */
    public static Bomber player = null;

    /**
     * Threats.
     */
    public static ArrayList<Enemy> enemies = new ArrayList<>();

    /**
     * List bomb.
     */
    public static ArrayList<Bomb> bombs = new ArrayList<>();

    /**
     * List flame.
     */
    public static ArrayList<Flame> flames = new ArrayList<>();

    /**
     * Map.
     */
    public static PlayGround map = new PlayGround();

    /**
     * Xử lí thao tác ấn phím.
     *
     * @param e Key Event
     */
    public static void inputKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
            player.setObjectDirection(MovingObject.ObjectDirection.RIGHT_, true);
        } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
            player.setObjectDirection(MovingObject.ObjectDirection.LEFT_, true);
        } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
            player.setObjectDirection(MovingObject.ObjectDirection.UP_, true);
        } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
            player.setObjectDirection(MovingObject.ObjectDirection.DOWN_, true);
        } else if (e.getCode() == KeyCode.SPACE) {
            if (player.canCreateBomb()) {
                player.createBomb();
            }
        }
    }

    /**
     * Xử lí thao tác nhả phím.
     *
     * @param e Key Event
     */
    public static void inputKeyRelease(KeyEvent e) {
        if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
            player.setObjectDirection(MovingObject.ObjectDirection.RIGHT_, false);
        } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
            player.setObjectDirection(MovingObject.ObjectDirection.LEFT_, false);
        } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
            player.setObjectDirection(MovingObject.ObjectDirection.UP_, false);
        } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
            player.setObjectDirection(MovingObject.ObjectDirection.DOWN_, false);
        }
    }


    /**
     * Render screen.
     */
    public static void render() {
        for (int i = 0; i < map.numberOfRow; i++) {
            for (int j = 0; j < map.numberOfColumn; j++) {
                map.cells[i][j].draw();
            }
        }
        for (Bomb x : bombs) {
            x.draw();
        }

        if (player != null) {
            player.draw();
        }

        for (int i = flames.size() - 1; i >= 0; i--) {
            flames.get(i).draw();
        }
    }

    /**
     * Xử lí thua game.
     */
    public static void gameOver() {
        gameStatus = gameStatusType.LOSE_;
    }

    /**
     * Chạy game.
     */
    public static void play() {
        //cập nhật trạng thái của bản đồ
        for (int i = 0; i < map.numberOfRow; i++) {
            for (int j = 0; j < map.numberOfColumn; j++) {
                GameObject now = map.cells[i][j];

                //hủy những ô brick đã hết thời gian nổ
                if (now instanceof Brick) {
                    if (((Brick) now).checkExplodingExpired()) {
                        ((Brick) now).setBlockState(Block.BlockState.FINAL_STATE_);
                    }
                }

                //hủy những ô item đã hết thời gian nổ
                if (now instanceof Item) {
                    if (((Item) now).checkExplodingExpired()) {
                        ((Item) now).setBlockState(Block.BlockState.FINAL_STATE_);
                    }
                }

                //hủy những ô portal đã hết thời gian nổ
                if (now instanceof Portal) {
                    if (((Portal) now).checkExplodingExpired()) {
                        ((Portal) now).setBlockState(Block.BlockState.FINAL_STATE_);
                    }
                }
            }
        }

        //kiểm tra xem bom đã đến lúc nổ chưa, nếu đến thì cho nổ, tạo flame và xóa bom
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).checkExplode()) {
                bombs.get(i).createFlame();
                bombs.get(i).getOwner().currentBomb--;
                bombs.remove(i);
                i--;
            }
        }

        for (int i = 0; i < flames.size(); i++) {
            //kiểm tra flame đã hết thời gian chưa, nếu có thì xóa
            if (flames.get(i).checkExpired()) {
                flames.remove(i);
                i--;
            } else {
                // nếu flame chạm nhân vật
                if (flames.get(i).checkIntersect(player)) {
                    gameOver();
                }
                // nếu flame chạm bom, kích nổ bom đó luôn
                for (int j = 0; j < bombs.size(); j++)
                    if (flames.get(i).checkIntersect(bombs.get(j))) {
                        bombs.get(j).createFlame();
                        bombs.get(j).getOwner().currentBomb--;
                        bombs.remove(j);
                        j--;
                    }
            }
        }

        // Player luôn di chuyển (đứng im tại chỗ tốc độ bằng 0)
        player.move();

        player.checkEatItems();

        render();
    }
}
