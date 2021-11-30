package bomberman;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import bomberman.GlobalVariable.FilesPath;
import bomberman.GlobalVariable.RenderVariable;

import bomberman.Object.*;
import bomberman.Map.PlayGround;
import bomberman.Object.MovingObject.Bomber.Bomber;
import bomberman.Object.MovingObject.Threats.Enemy;
import bomberman.Object.MovingObject.MovingObject;
import bomberman.Object.NonMovingObject.*;

public class PvB_GamePlay {
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
    private static gameStatusType gameStatus;

    public static void setGameStatus(gameStatusType inputGameStatus) {
        gameStatus = inputGameStatus;
    }

    public static gameStatusType getGameStatus() {
        return gameStatus;
    }

    /**
     * Map.
     */
    public static PlayGround map;

    /**
     * Player của màn chơi này.
     */
    public static Bomber player;

    /**
     * Biến để kiểm soát game chạy hay dừng.
     */
    private static boolean needToWait;

    /**
     * Khởi tạo màn chơi PvB.
     * (Khởi tạo các biến phục vụ cho màn chơi)
     */
    public static void createGame() {
        map = new PlayGround();

        player = map.players.get(0);

        needToWait = false;
    }

    /**
     * Render screen.
     */
    public static void render() {
        map.render();
    }

    /**
     * Lên level.
     */
    public static void nextLevel() {
        map.level++;

        if (map.maxLevel < map.level + 1) {
            gameWon();

            return;
        }

        RenderVariable.gc.drawImage(FilesPath.LevelUp, map.mapLength / 2 - 200, map.mapWidth / 2 - 200, 400, 400);

        needToWait = true;

        player = null;

        map.players.clear();
        map.enemies.clear();
        map.bombs.clear();
        map.flames.clear();

        map.createMapAtLevel();

        player = map.players.get(0);
    }

    /**
     * Xử lí thua game.
     */
    public static void gameOver() {
        RenderVariable.gc.drawImage(FilesPath.YouLose, map.mapLength / 2 - 200, map.mapWidth / 2 - 200, 400, 400);

        needToWait = true;

        gameStatus = gameStatusType.LOSE_;
    }

    /**
     * Xử lý thắng game.
     */
    public static void gameWon() {
        RenderVariable.gc.drawImage(FilesPath.YouWon, map.mapLength / 2 - 200, map.mapWidth / 2 - 200, 400, 400);

        needToWait = true;

        gameStatus = gameStatusType.WON_;
    }

    /**
     * Chạy game.
     */
    public static void play() {
        if (needToWait) {
            long startTime = System.nanoTime();

            do {

            } while (System.nanoTime() - startTime <= 2000000000);

            needToWait = false;
        }

        for (Flame flame : map.flames) {
            // nếu flame chạm nhân vật
            if (flame.checkIntersect(player)) {
                gameOver();

                return;
            }

            // nếu flame chạm quái
            for (int j = 0; j < map.enemies.size(); j++) {
                if (flame.checkIntersect(map.enemies.get(j))) {
                    map.enemies.remove(j);

                    j--;
                }
            }
        }

        for (Enemy enemy : map.enemies) {
            // quái chạm nhân vật
            if (enemy.checkIntersect(player)) {
                gameOver();

                return;
            }
        }

        if (player.checkOnPortal() && map.enemies.isEmpty()) {
            nextLevel();

            return;
        }

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
        for (int i = 0; i < map.bombs.size(); i++) {
            if (map.bombs.get(i).checkExplode()) {
                map.bombs.get(i).detonateBomb();

                map.bombs.get(i).getOwner().changeCurrentBomb(-1);

                map.bombs.remove(i);

                i--;
            } else {
                map.bombs.get(i).updateUnblockList();
            }
        }

        for (int i = 0; i < map.flames.size(); i++) {
            //kiểm tra flame đã hết thời gian chưa, nếu có thì xóa
            if (map.flames.get(i).checkExpired()) {
                map.flames.remove(i);

                i--;
            } else {
                // nếu flame chạm bom, kích nổ bom đó luôn
                for (int j = 0; j < map.bombs.size(); j++)
                    if (map.flames.get(i).checkIntersect(map.bombs.get(j))) {
                        map.bombs.get(j).detonateBomb();

                        map.bombs.get(j).getOwner().changeCurrentBomb(-1);

                        map.bombs.remove(j);

                        j--;
                    }
            }
        }

        for (Enemy enemy : map.enemies) {
            //Quái di chuyển
            enemy.move();
        }

        // Player luôn di chuyển (đứng im tại chỗ tốc độ bằng 0)
        player.move();

        player.checkEatItems();

        render();
    }

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
}
