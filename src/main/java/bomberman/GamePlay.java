package bomberman;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class GamePlay {
  public static enum gameStatusType {
    happening,
    won,
    lose,
  }
  public static gameStatusType gameStatus;
  public static Boomber player = null;
  public static ArrayList<Enemy> enemies = new ArrayList<>();
  public static ArrayList<Bomb> bombs = new ArrayList<>();
  public static ArrayList<Flame> flames = new ArrayList<>();
  public static PlayGround map = new PlayGround();

  public static void inputKeyPress(KeyEvent e) {
    String s = e.getCode().toString();
    if (s.contains("RIGHT")) {
      player.setCurrentOrientation(MovingObject.orientations.right);
      player.move();
    } else if (s.contains("LEFT")) {
      player.setCurrentOrientation(MovingObject.orientations.left);
      player.move();
    } else if (s.contains("UP")) {
      player.setCurrentOrientation(MovingObject.orientations.up);
      player.move();
    } else if (s.contains("DOWN")) {
      player.setCurrentOrientation(MovingObject.orientations.down);
      player.move();
    } else if (s.contains("SPACE")) {
      if (player.canCreateBomb()) {
        player.createBomb();
      }
    }
  }

  public static void render() {
    for (int i = 0; i < map.numberOfRow; i++) {
      for (int j = 0; j < map.numberOfColumn; j++) {
        map.cells[i][j].draw();
      }
    }
    for (Bomb x : bombs) {
      x.draw();
    }

    if (player != null) player.draw();
    for (int i=flames.size()-1;i>=0;i--)  flames.get(i).draw();
  }

  public static void gameOver(){
    gameStatus = gameStatusType.lose;
  }

  /** run game. */
  public static void play() {

    //cập nhật trạng thái của bản đồ
    for (int i = 0; i < map.numberOfRow; i++) {
      for (int j = 0; j < map.numberOfColumn; j++) {
        Object now = map.cells[i][j];

        //hủy những ô brick đã hết thời gian nổ
        if (now instanceof Brick) {
          if (((Brick) now).checkExplodingExpired()) {
            ((Brick) now).exploding = false;
            ((Brick) now).exploded = true;
          }
        }

        //hủy những ô item đã hết thời gian nổ
        if (now instanceof Item) {
          if (((Item) now).checkExplodingExpired()) {
            ((Item) now).exploding = false;
            ((Item) now).showingItem = true;
          }
        }

        //hủy những ô portal đã hết thời gian nổ
        if (now instanceof Portal) {
          if (((Portal) now).checkExplodingExpired()) {
            ((Portal) now).exploding = false;
            ((Portal) now).isBlocked = false;
          }
        }
      }
    }

    //kiểm tra xem bom đã đến lúc nổ chưa, nếu đến thì cho nổ, tạo flame và xóa bom
    for (int i=0;i<bombs.size();i++)
      if (bombs.get(i).checkExplode()) {
        bombs.get(i).createFlame();
        bombs.get(i).owner.currentBomb--;
        bombs.remove(i);
        i--;
      }

    for (int i=0;i<flames.size();i++) {
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
        for (int j=0;j<bombs.size();j++)
          if (flames.get(i).checkIntersect(bombs.get(j))) {
            bombs.get(j).createFlame();
            bombs.get(j).owner.currentBomb--;
            bombs.remove(j);
            j--;
          }
      }
    }

    player.checkEatItems();
    render();
  }
}
