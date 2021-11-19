package bomberman;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class BombController {
    public Image Grass = new Image(getClass().getResource("/image/grass.png").toURI().toString());
    public Image Wall = new Image(getClass().getResource("/image/wall.png").toURI().toString());
    public Image Brick = new Image(getClass().getResource("/image/brick.png").toURI().toString());
    public Image Player = new Image(getClass().getResource("/image/dango.png").toURI().toString());


    public BombController() throws URISyntaxException {

    }

    /**
     * render map.
     */
    public void render() {
        float x_ = 0;
        float y_ = 0;
        for (int i = 0; i < Map.max_y; i++) {
            x_ = 0;
            for (int j = 0; j < Map.max_x; j++) {
                if (Map.map[i][j] == '*') {
                    BombermanApplication.gc.drawImage(Brick, x_, y_, Map.weight, Map.height);
                }
                else {
                    if (Map.map[i][j] == '#') {
                        BombermanApplication.gc.drawImage(Wall, x_, y_, Map.weight, Map.height);
                    }
                    else {
                        BombermanApplication.gc.drawImage(Grass, x_, y_, Map.weight, Map.height);
                    }
                }
                x_ += Map.weight;
            }
            y_ += Map.height;
        }
        BombermanApplication.gc.drawImage(Player, Map.player.getX(), Map.player.getY(), Map.player.rect_w, Map.player.rect_h);
        //BombermanApplication.gc.drawImage(Player, 0, 0, Map.weight, Map.height);
    }

    /**
     * run game.
     */
    public void play() {
        //BombermanApplication.scene.setOnKeyPressed(Management::inputKeyPress);
        //BombermanApplication.scene.setOnKeyReleased(Management::inputKeyRelease);
        Management.doPlayer();
        this.render();
    }
}