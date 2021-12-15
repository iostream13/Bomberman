package bomberman.GlobalVariable;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URISyntaxException;
import javax.sound.sampled.*;

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

    public static Clip createClip(String filePath) {
        filePath = "src/main/java/resources/sound/" + filePath;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        return null;
    }

    // IMAGE FILE PATH
    public static final Image BackGroundGame = createImage("/image/backgroundgame.png");

    public static final Image Grass = createImage("/image/grass.png");
    public static final Image Wall = createImage("/image/wall.png");
    public static final Image Bomb = createImage("/image/bomb.png");
    public static final Image Portal = createImage("/image/Portal.png");
    public static final Image Brick = createImage("/image/brick.png");
    public static final Image BrickExploded = createImage("/image/brick_exploded.png");

    public static final Image Flame = createImage("/image/flame.png");

    public static final Image PowerUpBomb = createImage("/image/power_up_bomb.png");
    public static final Image PowerUpFlame = createImage("/image/power_up_flame.png");
    public static final Image PowerUpSpeed = createImage("/image/power_up_speed.png");

    public static final Image LevelUp = createImage("/image/level_up.png");
    public static final Image YouWon = createImage("/image/you_won.png");
    public static final Image YouLose = createImage("/image/you_lose.png");
    public static final Image YouDraw= createImage("/image/you_draw.png");

    public static final Image Oneal = createImage("/image/oneal.png");

    public static final Image Balloom = createImage("/image/balloom.png");

    public static final Image Teleport = createImage("/image/teleport.png");

    public static final Image Bomber = createImage("/image/bomber.png");

    // MAP FILE PATH
    public static final String PVB_MAP_PATH = "src/main/java/resources/Map/map.txt";
    public static final String PVP_MAP_PATH = "src/main/java/resources/Map/PvPmap.txt";

    // AUDIO CLIP FILE PATH
    public static Clip PlayGroundAudio = createClip("PlayGround.wav");

    public static Clip BomberDieAudio = createClip("Bomberdie.wav");
    public static Clip BalloomDieAudio = createClip("balloomDie.wav");
    public static Clip OnealDieAudio = createClip("OnealDie.wav");

    public static Clip ExplosionAudio = createClip("Explosion.wav");
    public static Clip PlaceBombAudio = createClip("PlaceBomb.wav");
    public static Clip ItemAppearsAudio = createClip("ItemAppears.wav");
    public static Clip PowerUpAudio = createClip("PowerUp.wav");

    public static Clip LevelUpAudio = createClip("LevelUp.wav");
    public static Clip YouWonAudio = createClip("YouWon.wav");
    public static Clip YouLoseAudio = createClip("YouLose.wav");

    public FilesPath() throws URISyntaxException {
    }

    // TODO: optimize this
    public static String encodeImageName(Image tmp) {
        if (Bomber.equals(tmp)) {
            return "Bomber";
        } else if (Grass.equals(tmp)) {
            return "Grass";
        } else if (Wall.equals(tmp)) {
            return "Wall";
        } else if (Bomb.equals(tmp)) {
            return "Bomb";
        } else if (Portal.equals(tmp)) {
            return "Portal";
        } else if (Brick.equals(tmp)) {
            return "Brick";
        } else if (BrickExploded.equals(tmp)) {
            return "BrickExploded";
        } else if (Flame.equals(tmp)) {
            return "Flame";
        } else if (PowerUpBomb.equals(tmp)) {
            return "PowerUpBomb";
        } else if (PowerUpFlame.equals(tmp)) {
            return "PowerUpFlame";
        } else if (PowerUpSpeed.equals(tmp)) {
            return "PowerUpSpeed";
        } else if (LevelUp.equals(tmp)) {
            return "LevelUp";
        } else if (YouWon.equals(tmp)) {
            return "YouWon";
        } else if (YouLose.equals(tmp)) {
            return "YouLose";
        } else if (YouDraw.equals(tmp)) {
            return "YouDraw";
        } else if (BackGroundGame.equals(tmp)) {
            return "BackGroundGame";
        }

        return "UNKNOWN";
    }

    public static Image decodeImageName(String name) {
        switch (name) {
            case "Bomber": return Bomber;
            case "Grass": return Grass;
            case "Wall": return Wall;
            case "Bomb": return Bomb;
            case "Portal": return Portal;
            case "Brick": return Brick;
            case "BrickExploded": return BrickExploded;

            case "Flame": return Flame;

            case "PowerUpBomb": return PowerUpBomb;
            case "PowerUpFlame": return PowerUpFlame;
            case "PowerUpSpeed": return PowerUpSpeed;

            case "YouWon": return YouWon;
            case "YouLose": return YouLose;
            case "YouDraw": return YouDraw;

            case "BackGroundGame": return BackGroundGame;
        }

        return Grass;
    }
}
