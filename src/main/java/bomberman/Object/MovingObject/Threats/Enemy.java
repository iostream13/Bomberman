package bomberman.Object.MovingObject.Threats;

import bomberman.Object.MovingObject.MovingObject;
import bomberman.PvB_GamePlay;
import javafx.util.Pair;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends MovingObject {

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
    }

    @Override
    public void move() {
        if (System.nanoTime() - startTime >= duration) {
            int xPlayer = ((int)PvB_GamePlay.player.getX())/(int)PvB_GamePlay.map.cellLength;
            int yPlayer = ((int)PvB_GamePlay.player.getY())/(int)PvB_GamePlay.map.cellLength;
            int xEnemy = ((int)this.getX())/(int)PvB_GamePlay.map.cellLength;
            int yEnemy = ((int)this.getY())/(int)PvB_GamePlay.map.cellLength;
            this.findBestWay(xEnemy, yEnemy, xPlayer, yPlayer);
            System.out.println(ok);
            if (this.ok == true) {
                boolean toRight = false;
                boolean toLeft = false;
                boolean toUp = false;
                boolean toDown = false;
                int[][] state = this.getState();
                for (int i = 0; i < PvB_GamePlay.map.numberOfRow; i++) {
                    for (int j = 0; j < PvB_GamePlay.map.numberOfColumn; j++) {
                        System.out.print(state[i][j]);
                        System.out.print(' ');
                    }
                    System.out.print("\n");
                }
                System.out.print(xEnemy);
                System.out.print(' ');
                System.out.println(yEnemy);
                System.out.println(state[xEnemy][yEnemy]);
                System.out.print("\n");
                int[] c = {0, 0, -1, 1};
                int[] d = {1, -1, 0, 0};
                for (int i = 0; i <= 3; i++) {
                    int newX = xEnemy + c[i];
                    int newY = yEnemy + d[i];
                    if (newX < 0 || newX >= PvB_GamePlay.map.numberOfColumn || newY < 0 || newY > PvB_GamePlay.map.numberOfRow) {
                        continue;
                    }
                    if (state[newY][newX] == 1) {
                        if (i == 0) {
                            toDown = true;
                            toUp = false;
                        }
                        else {
                            if (i == 1) {
                                toUp = true;
                                toDown = false;
                            }
                        }
                        if (i == 2) {
                            toLeft = true;
                            toRight = false;
                        }
                        else {
                            if (i == 3) {
                                toRight = true;
                                toLeft = false;
                            }
                        }
                    }
                }
                setObjectDirection(MovingObject.ObjectDirection.RIGHT_, toRight);
                setObjectDirection(MovingObject.ObjectDirection.LEFT_, toLeft);
                setObjectDirection(MovingObject.ObjectDirection.UP_, toUp);
                setObjectDirection(MovingObject.ObjectDirection.DOWN_, toDown);
            }
            else {
                System.out.println("ddddddd");
                boolean toRight, toLeft, toUp, toDown;
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
