package bomberman;

import javafx.scene.image.Image;

import java.net.URISyntaxException;

public class Images {
    public static Image Grass = new Image(String.valueOf(Images.class.getResource("/image/grass.png")));
    public static Image Wall = new Image(String.valueOf(Images.class.getResource("/image/wall.png")));
    public static Image Brick = new Image(String.valueOf(Images.class.getResource("/image/brick.png")));
    public static Image Player = new Image(String.valueOf(Images.class.getResource("/image/dango.png")));
    public static Image Bomb = new Image(String.valueOf(Images.class.getResource("/image/bomb.png")));
    public static Image FlameMid = new Image(String.valueOf(Images.class.getResource("/image/flamemid.png")));
    public static Image FlameLeft = new Image(String.valueOf(Images.class.getResource("/image/flameleft.png")));
    public static Image FlameRight = new Image(String.valueOf(Images.class.getResource("/image/flameright.png")));
    public static Image FlameUp = new Image(String.valueOf(Images.class.getResource("/image/flameup.png")));
    public static Image FlameDown = new Image(String.valueOf(Images.class.getResource("/image/flamedown.png")));
    public static Image BrickExploded = new Image(String.valueOf(Images.class.getResource("/image/brick_exploded.png")));
    public static Image PowerupBomb = new Image(String.valueOf(Images.class.getResource("/image/powerup_bomb.png")));
    public static Image PowerupFlame = new Image(String.valueOf(Images.class.getResource("/image/powerup_flame.png")));
    public static Image PowerupSpeed = new Image(String.valueOf(Images.class.getResource("/image/powerup_speed.png")));
    public static Image FlameVertical = new Image(String.valueOf(Images.class.getResource("/image/explosion_vertical.png")));
    public static Image Portal = new Image(String.valueOf(Images.class.getResource("/image/Portal.png")));
    public static Image FlameHorizontal = new Image(String.valueOf(Images.class.getResource("/image/explosion_horizontal.png")));
    public Images() throws URISyntaxException {

    }
}
