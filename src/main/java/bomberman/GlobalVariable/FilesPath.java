package bomberman.GlobalVariable;

import javafx.scene.image.Image;

import java.net.URISyntaxException;

public class FilesPath {
    /**
     * Tạo đối tượng Image mới.
     *
     * @param path đường dẫn của ảnh
     * @return đối tượng Image mới
     */
    private static Image createImage(String path) {
        return new Image(String.valueOf(FilesPath.class.getResource(path)));
    }

    // IMAGE FILE PATH
    public static final Image Grass = createImage("/image/grass.png");
    public static final Image Wall = createImage("/image/wall.png");
    public static final Image Bomb = createImage("/image/bomb.png");
    public static final Image Portal = createImage("/image/Portal.png");
    public static final Image Brick = createImage("/image/brick.png");
    public static final Image BrickExploded = createImage("/image/brick_exploded.png");

    public static final Image FlameMid = createImage("/image/flame_mid.png");
    public static final Image FlameLeft = createImage("/image/flame_left.png");
    public static final Image FlameRight = createImage("/image/flame_right.png");
    public static final Image FlameUp = createImage("/image/flame_up.png");
    public static final Image FlameDown = createImage("/image/flame_down.png");

    public static final Image FlameVertical = createImage("/image/explosion_vertical.png");
    public static final Image FlameHorizontal = createImage("/image/explosion_horizontal.png");

    public static final Image PowerUpBomb = createImage("/image/power_up_bomb.png");
    public static final Image PowerUpFlame = createImage("/image/power_up_flame.png");
    public static final Image PowerUpSpeed = createImage("/image/power_up_speed.png");

    public static final Image LevelUp = createImage("/image/level_up.png");
    public static final Image YouWon = createImage("/image/you_won.png");
    public static final Image YouLose = createImage("/image/you_lose.png");

    public static final Image OnealUp = createImage("/image/oneal_up.png");
    public static final Image OnealDown = createImage("/image/oneal_down.png");
    public static final Image OnealLeft = createImage("/image/oneal_left.png");
    public static final Image OnealRight = createImage("/image/oneal_right.png");

    public static final Image BalloomUp = createImage("/image/balloom_up.png");
    public static final Image BalloomDown = createImage("/image/balloom_down.png");
    public static final Image BalloomLeft = createImage("/image/balloom_left.png");
    public static final Image BalloomRight = createImage("/image/balloom_right.png");

    public static final Image BomberUp = createImage("/image/dango_up.png");
    public static final Image BomberDown = createImage("/image/dango_down.png");
    public static final Image BomberLeft = createImage("/image/dango_left.png");
    public static final Image BomberRight = createImage("/image/dango_right.png");

    // MAP FILE PATH
    public static final String MAP_PATH = "src/main/java/resources/Map/map.txt";

    public FilesPath() throws URISyntaxException {
    }
}
