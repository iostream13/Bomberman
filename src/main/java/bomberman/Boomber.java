package bomberman;

import javafx.scene.image.Image;

public class Boomber extends MovingObject {
  public int maxBomb = 1;
  public int currentBomb = 0;
  // số ô mà flame do nhân vật tạo ra, ăn buff thì tăng 1
  public int flameLength = 1;

  public Boomber(float x, float y) {
    super(x, y, 30, 30);
  }

  public Boomber(float x, float y, float width, float length) {
    super(x, y, width, length);
  }

  // kiểm tra còn thừa bom để đặt không
  public boolean canCreateBomb() {
    return currentBomb < maxBomb;
  }

  // đặt 1 bom
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

  // kiểm tra trên phạm vi đang đứng có item không, nếu có thì thực hiện ăn
  public void checkEatItems() {
    int x1 = (int) (this.getX() / GamePlay.map.cellLength);
    int x2 = (int) ((this.getX() + this.getWidth() - 1) / GamePlay.map.cellLength);
    int y1 = (int) (this.getY() / GamePlay.map.cellLength);
    int y2 = (int) ((this.getY() + this.getLength() - 1) / GamePlay.map.cellLength);

    for (int i = y1; i <= y2; i++)
      for (int j = x1; j <= x2; j++) {
        Object now = GamePlay.map.cells[i][j];
        if (!(now instanceof Item)) continue;
        if (!(((Item) now).showingItem)) continue;
        switch (((Item) now).type) {
          case BombItem:
            maxBomb++;
            break;
          case SpeedItem:
            setSpeed(getSpeed() + 20);
            break;
          case FlameItem:
            flameLength++;
            break;
        }
        ((Item) now).showingItem = false;
        ((Item) now).ate = true;
      }
  }

  @Override
  public Image getImage() {
    return Images.Player;
  }
}
