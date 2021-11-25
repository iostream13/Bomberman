package bomberman.Object.MovingObject.Threats;

import bomberman.Object.MovingObject.MovingObject;
import javafx.scene.input.KeyCode;

import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends MovingObject {

  // thời gian di chuyển theo hướng hiện tại
  private long duration = 250000000; // 0.25 giây
  private long startTime = System.nanoTime();
  /**
   * Constructor cho Enemy.
   *
   * @param x tọa độ x
   * @param y tọa độ y
   * @param width chiều rộng
   * @param length chiều dài
   */
  public Enemy(double x, double y, double width, double length) {
    super(x, y, width, length);
  }

  @Override
  public void move() {
    if (System.nanoTime() - startTime >= duration) {
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
    super.move();
  }
}
