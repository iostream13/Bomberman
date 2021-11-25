package bomberman.GlobalVariable;

import javafx.scene.image.Image;

import java.net.URISyntaxException;

public class ImagesPath {
    /**
     * Tạo đối tượng Image mới.
     *
     * @param path đường dẫn của ảnh
     * @return đối tượng Image mới
     */
    private static Image createImage(String path) {
        return new Image(String.valueOf(ImagesPath.class.getResource(path)));
    }

    public static Image Grass = createImage("/image/grass.png");
    public static Image Wall = createImage("/image/wall.png");
    public static Image Brick = createImage("/image/brick.png");
    public static Image Player = createImage("/image/dango.png");
    public static Image Bomb = createImage("/image/bomb.png");
    public static Image FlameMid = createImage("/image/flamemid.png");
    public static Image FlameLeft = createImage("/image/flameleft.png");
    public static Image FlameRight = createImage("/image/flameright.png");
    public static Image FlameUp = createImage("/image/flameup.png");
    public static Image FlameDown = createImage("/image/flamedown.png");
    public static Image BrickExploded = createImage("/image/brick_exploded.png");
    public static Image PowerUpBomb = createImage("/image/powerup_bomb.png");
    public static Image PowerUpFlame = createImage("/image/powerup_flame.png");
    public static Image PowerUpSpeed = createImage("/image/powerup_speed.png");
    public static Image FlameVertical = createImage("/image/explosion_vertical.png");
    public static Image Portal = createImage("/image/Portal.png");
    public static Image FlameHorizontal = createImage("/image/explosion_horizontal.png");
    public static Image Oneal = createImage("/image/oneal.png");
    public static Image Balloom = createImage("/image/balloom.png");
    public static Image LevelUp = createImage("/image/level_up.png");
    public static Image YouWon = createImage("/image/you_won.png");
    public static Image YouLose = createImage("/image/you_lose.png");

    public ImagesPath() throws URISyntaxException {
    }
}
