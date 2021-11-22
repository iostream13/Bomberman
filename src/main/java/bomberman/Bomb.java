package bomberman;

import javafx.scene.image.Image;

public class Bomb extends Object {
  public Boomber owner;
  public boolean isBlocked = false;
  public long startTime;
  public final long duration = 2000000000;

  public Bomb(float x, float y, float width, float length) {
    super(x, y, width, length);
  }

  public Bomb(float x, float y, float width, float length, Boomber owner) {
    super(x, y, width, length);
    this.owner = owner;
    this.startTime = System.nanoTime();
  }

  @Override
  public Image getImage() {
    return Images.Bomb;
  }

  // kiểm tra đã đến thời gian nổ chưa
  public boolean checkExplode() {
    return (System.nanoTime() - startTime >= duration);
  }

  //sinh ra các flame
  public void createFlame() {
    int x = (int) ((int) this.getX() / GamePlay.map.cellLength);
    int y = (int) ((int) this.getY() / GamePlay.map.cellLength);
    int len = owner.flameLength;
    int side = (int) GamePlay.map.cellLength;

    // sinh flame ra bên trái bom
    for (int i = x-1; i >= 0 && i >= x - len; i--) {
      // gặp cô cản, ngừng sinh flame
      if (GamePlay.map.isBlockCell(y, i)) {
        Flame.handleIntersectCell(GamePlay.map.cells[y][i]);
        break;
      }
      if (i == x - len) {
        GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.flameTypes.left));
      } else {
        GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.flameTypes.horizontal));
      }
    }

    // sinh flame ra bên phải bom
    for (int i = x+1; i <= GamePlay.map.numberOfColumn && i <= x + len; i++) {
      // gặp cô cản, ngừng sinh flame
      if (GamePlay.map.isBlockCell(y, i)) {
        Flame.handleIntersectCell(GamePlay.map.cells[y][i]);
        break;
      }
      if (i == x + len) {
        GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.flameTypes.right));
      } else {
        GamePlay.flames.add(new Flame(i * side, y * side, side, side, Flame.flameTypes.horizontal));
      }
    }

    // sinh flame ra bên trên bom
    for (int i = y-1; i >= 0 && i >= y - len; i--) {
      // gặp cô cản, ngừng sinh flame
      if (GamePlay.map.isBlockCell(i, x)) {
        Flame.handleIntersectCell(GamePlay.map.cells[i][x]);
        break;
      }
      if (i == y - len) {
        GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.flameTypes.up));
      } else {
        GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.flameTypes.vertical));
      }
    }

    // sinh flame ra bên dưới bom
    for (int i = y+1; i <= GamePlay.map.numberOfRow && i <= y + len; i++) {
      // gặp cô cản, ngừng sinh flame
      if (GamePlay.map.isBlockCell(i, x)) {
        Flame.handleIntersectCell(GamePlay.map.cells[i][x]);
        break;
      }
      if (i == y + len) {
        GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.flameTypes.down));
      } else {
        GamePlay.flames.add(new Flame(x * side, i * side, side, side, Flame.flameTypes.vertical));
      }
    }

    //sinh flame ở chính giữa
    GamePlay.flames.add(new Flame(x * side, y * side, side, side, Flame.flameTypes.middle));
  }

}
