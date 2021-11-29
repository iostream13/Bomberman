package bomberman.Object.MovingObject.Threats;

import bomberman.Object.MovingObject.MovingObject;
import bomberman.PvB_GamePlay;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends MovingObject {

    /**
     * Trạng thái đường đi của object.
     */
    private int[][] state = new int[50][50];
    /**
     * Trạng thái bomb.
     */
    private int[][] stateBomb = new int[50][50];
    public boolean ok = false;
    public boolean toRight = false;
    public boolean toLeft = false;
    public boolean toUp = false;
    public boolean toDown = false;

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

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public int[][] getStateBomb() {
        return stateBomb;
    }

    public void setStateBomb(int[][] stateBomb) {
        this.stateBomb = stateBomb;
    }

    public void markBomb() {
        for (int i = 1; i < PvB_GamePlay.map.numberOfRow; i++) {
            for (int j = 1; j < PvB_GamePlay.map.numberOfColumn; j++) {
                stateBomb[i][j] = 0;
            }
        }
        for (int i = 0; i < PvB_GamePlay.bombs.size(); i++) {
            int X = (int)((PvB_GamePlay.bombs.get(i).getX() + 1) / PvB_GamePlay.map.cellLength);
            int Y = (int)((PvB_GamePlay.bombs.get(i).getY() + 1) / PvB_GamePlay.map.cellLength);
            stateBomb[Y][X] = 1;
        }
    }

    public void findBestWay(int xEnemy, int yEnemy, int xPlayer, int yPlayer) {
        boolean[][] dx = new boolean[110][110];
        Pair<Integer, Integer>[][] tr = new Pair[110][110];
        int[] c = {0, 0, -1, 1};
        int[] d = {1, -1, 0, 0};
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        Pair<Integer, Integer> p;
        p = new Pair<>(yEnemy, xEnemy);
        dx[yEnemy][xEnemy] = true;
        q.add(p);
        markBomb();
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
                /*for (int i = 0; i < PvB_GamePlay.map.numberOfRow; i++) {
                    for (int j = 0; j < PvB_GamePlay.map.numberOfColumn; j++) {
                        System.out.print(state[i][j]);
                        System.out.print(' ');
                    }
                    System.out.print("\n");
                }*/
                ok = true;
                return;
            }
            p = new Pair<>(y, x);
            for (int i = 0; i <= 3; i++) {
                int newX = x + c[i];
                int newY = y + d[i];
                if (newX < 0 || newX >= PvB_GamePlay.map.numberOfColumn || newY < 0 || newY > PvB_GamePlay.map.numberOfRow) {
                    continue;
                }
                if (!PvB_GamePlay.map.isBlockCell(newY, newX) && stateBomb[newY][newX] == 0 && !dx[newY][newX]) {
                    dx[newY][newX] = true;
                    tr[newY][newX] = p;
                    Pair <Integer, Integer> pp = new Pair<>(newY, newX);
                    q.add(pp);
                }
            }
        }
        ok = false;
    }

    @Override
    public void move() {
        if (System.nanoTime() - startTime >= duration) {

            int xPlayer = ((int)PvB_GamePlay.player.getCenterX())/(int)PvB_GamePlay.map.cellLength;
            int yPlayer = ((int)PvB_GamePlay.player.getCenterY())/(int)PvB_GamePlay.map.cellLength;
            int xEnemy = ((int)this.getCenterX())/(int)PvB_GamePlay.map.cellLength;
            int yEnemy = ((int)this.getCenterY())/(int)PvB_GamePlay.map.cellLength;
            this.findBestWay(xEnemy, yEnemy, xPlayer, yPlayer);
            System.out.println(ok);
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
                        }
                        else {
                            if (i == 1) {
                                toUp = true;
                            } else if (i == 2) {
                                toLeft = true;
                            }
                            else {
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
                System.out.println(toDown);
                System.out.println(toLeft);
                System.out.println(toRight);
                System.out.println(toUp);
            }
            else {
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
