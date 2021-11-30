package bomberman.Object.MovingObject.Threats;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import bomberman.GlobalVariable.GameVariables;

import bomberman.Object.MovingObject.MovingObject;
import bomberman.PvB_GamePlay;

public abstract class Enemy extends MovingObject {

    /**
     * Trạng thái đường đi của object.
     */
    private int[][] state = new int[50][50];

    /**
     * Trạng thái di chuyển của enemy.
     */
    private boolean ok = false;
    private boolean toRight = false;
    private boolean toLeft = false;
    private boolean toUp = false;
    private boolean toDown = false;

    /**
     * Thời gian di chuyển theo hướng hiện tại
     */
    private long duration = 250000000; // 0.25 giây

    private long startTime = System.nanoTime();

    /**
     * Constructor cho Enemy.
     *
     * @param x      tọa độ x
     * @param y      tọa độ y
     * @param width  chiều rộng
     * @param length chiều dài
     */
    public Enemy(double x, double y, double width, double length) {
        super(x, y, width, length);

        setSpeed(2);
    }

    /**
     * Constructor cho Enemy.
     *
     * @param x tọa độ x
     * @param y tọa độ y
     */
    public Enemy(double x, double y) {
        super(x, y, 35, 35); // Kích thước mặc định

        setSpeed(2);
    }

    /**
     * Tìm đường đi tốt nhất cho enemy chạy tới player.
     *
     * @param xPlayer chỉ số ô đang đứng x của player
     * @param yPlayer chỉ số ô đang đứng y của player
     */
    public void findBestWay(int xPlayer, int yPlayer) {
        int xEnemy = GameVariables.calculateCellIndex(this.getCenterX());
        int yEnemy = GameVariables.calculateCellIndex(this.getCenterY());

        boolean[][] dx = new boolean[110][110];

        Pair<Integer, Integer>[][] tr = new Pair[110][110];

        int[] c = {0, 0, -1, 1};
        int[] d = {1, -1, 0, 0};

        Queue<Pair<Integer, Integer>> q = new LinkedList<>();

        Pair<Integer, Integer> p;

        p = new Pair<>(yEnemy, xEnemy);

        dx[yEnemy][xEnemy] = true;

        q.add(p);

        while (q.size() > 0) {
            p = q.element();

            q.remove();

            int y = p.getKey();
            int x = p.getValue();

            if (x == xPlayer && y == yPlayer) {
                for (int i = 1; i < PvB_GamePlay.map.numberOfRow; i++) {
                    for (int j = 1; j < PvB_GamePlay.map.numberOfColumn; j++) {
                        state[i][j] = 0;
                    }
                }

                int tmpX = xPlayer;
                int tmpY = yPlayer;

                while (tmpX != xEnemy || tmpY != yEnemy) {
                    state[tmpY][tmpX] = 1;

                    p = tr[tmpY][tmpX];

                    tmpY = p.getKey();
                    tmpX = p.getValue();
                }

                state[tmpY][tmpX] = 1;

                ok = true;

                return;
            }

            p = new Pair<>(y, x);

            for (int i = 0; i <= 3; i++) {
                int newX = x + c[i];
                int newY = y + d[i];

                if (newX < 0 || newX >= PvB_GamePlay.map.numberOfColumn || newY < 0 || newY >= PvB_GamePlay.map.numberOfRow) {
                    continue;
                }

                if (!PvB_GamePlay.map.isBlockCell(newY, newX) && !PvB_GamePlay.map.stateBomb[newY][newX] && !dx[newY][newX]) {
                    dx[newY][newX] = true;
                    tr[newY][newX] = p;

                    Pair<Integer, Integer> pp = new Pair<>(newY, newX);

                    q.add(pp);
                }
            }
        }

        ok = false;
    }

    @Override
    public void move() {
        if (System.nanoTime() - startTime >= duration) {
            int xPlayer = GameVariables.calculateCellIndex(PvB_GamePlay.player.getCenterX());
            int yPlayer = GameVariables.calculateCellIndex(PvB_GamePlay.player.getCenterY());
            int xEnemy = GameVariables.calculateCellIndex(this.getCenterX());
            int yEnemy = GameVariables.calculateCellIndex(this.getCenterY());

            this.findBestWay(xPlayer, yPlayer);

            if (this.ok) {
                toDown = false;
                toUp = false;
                toLeft = false;
                toRight = false;

                int[] c = {0, 0, -1, 1};
                int[] d = {1, -1, 0, 0};

                for (int i = 0; i <= 3; i++) {
                    int newX = xEnemy + c[i];
                    int newY = yEnemy + d[i];

                    if (newX < 1 || newX >= PvB_GamePlay.map.numberOfColumn || newY < 1 || newY > PvB_GamePlay.map.numberOfRow) {
                        continue;
                    }

                    if (state[newY][newX] == 1) {
                        if (i == 0) {
                            toDown = true;
                        } else {
                            if (i == 1) {
                                toUp = true;
                            } else if (i == 2) {
                                toLeft = true;
                            } else {
                                toRight = true;

                            }
                        }
                    }
                }

                state[yEnemy][xEnemy] = 0;

                setObjectDirection(MovingObject.ObjectDirection.RIGHT_, toRight);
                setObjectDirection(MovingObject.ObjectDirection.LEFT_, toLeft);
                setObjectDirection(MovingObject.ObjectDirection.UP_, toUp);
                setObjectDirection(MovingObject.ObjectDirection.DOWN_, toDown);
            } else {
                boolean toUp, toRight, toDown, toLeft;

                toRight = ThreadLocalRandom.current().nextBoolean();

                if (toRight) {
                    toLeft = false;
                } else {
                    toLeft = ThreadLocalRandom.current().nextBoolean();
                }

                toUp = ThreadLocalRandom.current().nextBoolean();

                if (!toUp && !toRight && !toLeft) {
                    toDown = true;
                } else if (toUp) {
                    toDown = false;
                } else {
                    toDown = ThreadLocalRandom.current().nextBoolean();
                }

                setObjectDirection(MovingObject.ObjectDirection.RIGHT_, toRight);
                setObjectDirection(MovingObject.ObjectDirection.LEFT_, toLeft);
                setObjectDirection(MovingObject.ObjectDirection.UP_, toUp);
                setObjectDirection(MovingObject.ObjectDirection.DOWN_, toDown);

                startTime = System.nanoTime();
            }
        }

        super.move();
    }
}
