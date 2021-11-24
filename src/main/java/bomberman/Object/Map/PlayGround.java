package bomberman.Object.Map;

import java.io.*;

import bomberman.GamePlay;

import bomberman.Object.MovingObject.Bomber.Bomber;
import bomberman.Object.MovingObject.Threats.Balloon;
import bomberman.Object.MovingObject.Threats.Oneal;
import bomberman.Object.NonMovingObject.*;
import bomberman.Object.GameObject;

public class PlayGround {
    /**
     * Level hiện tại.
     */
    public int level;

    /**
     * Số lượng hàng.
     */
    public int numberOfRow = 0;

    /**
     * Số lượng cột.
     */
    public int numberOfColumn = 0;

    /**
     * Độ dài cạnh của một ô.
     */
    public double cellLength = 40;

    /**
     * Các ô trên bản đồ, mỗi ô là 1 object.
     */
    public GameObject[][] cells = new GameObject[50][50];

    /**
     * Đường dẫn file map.
     */
    private static final String MAP_PATH = "src/main/java/resources/Map/map.txt";

    /**
     * Import data from file.
     */
    public PlayGround() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(MAP_PATH);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            int cnt = 1;
            int y = 0, x = 0;

            String[] item;

            String line = bufferedReader.readLine();

            while (cnt <= numberOfRow + 1) {
                if (cnt == 1) {
                    item = line.split(" ");
                    level = Integer.parseInt(item[0]);

                    numberOfRow = Integer.parseInt(item[1]);
                    numberOfColumn = Integer.parseInt(item[2]);
                } else {
                    for (int i = 0; i < numberOfColumn; i++) {
                        char tmp = line.charAt(i);
                        switch (tmp) {
                            case 'p':
                                GamePlay.player = new Bomber(cellLength * i, cellLength * y);
                                cells[y][i] = new Grass(cellLength * i, cellLength * y, cellLength, cellLength);
                                break;
                            case '#':
                                cells[y][i] = new Wall(cellLength * i, cellLength * y, cellLength, cellLength);
                                break;
                            case '*':
                                cells[y][i] = new Brick(cellLength * i, cellLength * y, cellLength, cellLength);
                                break;
                            case 'x':
                                cells[y][i] = new Portal(cellLength * i, cellLength * y, cellLength, cellLength);
                                break;
                            case 'b':
                                cells[y][i] = new Item(cellLength * i, cellLength * y, cellLength, cellLength, Item.typeOfItems.BOMB_ITEM_);
                                break;
                            case 'f':
                                cells[y][i] = new Item(cellLength * i, cellLength * y, cellLength, cellLength, Item.typeOfItems.FLAME_ITEM_);
                                break;
                            case 's':
                                cells[y][i] = new Item(cellLength * i, cellLength * y, cellLength, cellLength, Item.typeOfItems.SPEED_ITEM_);
                                break;
                            case '1':
                                GamePlay.enemies.add(new Balloon(cellLength * i, cellLength * y, cellLength, cellLength));
                                cells[y][i] = new Grass(cellLength * i, cellLength * y, cellLength, cellLength);
                                break;
                            case '2':
                                GamePlay.enemies.add(new Oneal(cellLength * i, cellLength * y, cellLength, cellLength));
                                cells[y][i] = new Grass(cellLength * i, cellLength * y, cellLength, cellLength);
                                break;
                            default:
                                cells[y][i] = new Grass(cellLength * i, cellLength * y, cellLength, cellLength);
                        }
                    }

                    y++;
                }

                cnt++;

                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        } catch (IOException ex) {
            System.out.println("IO exception");
        } finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (IOException ex) {
                System.out.println("IO exception");
            }
        }
    }

    /**
     * Kiểm tra một ô có bị chặn không.
     */
    public boolean isBlockCell(int temp_x, int temp_y) {
        //các trường hợp bị chặn
        //ô tường
        if ((cells[temp_x][temp_y] instanceof Wall)) {
            return true;
        }

        // ô gạch chưa bị nổ
        if (cells[temp_x][temp_y] instanceof Brick) {
            if (((Brick) cells[temp_x][temp_y]).isInitialState()) {
                return true;
            }
        }

        // ô item chưa bị nổ
        if ((cells[temp_x][temp_y] instanceof Item)) {
            if (((Item) cells[temp_x][temp_y]).isInitialState()) {
                return true;
            }
        }

        // ô portal chưa bị nổ
        if ((cells[temp_x][temp_y] instanceof Portal)) {
            if (((Portal) cells[temp_x][temp_y]).isInitialState()) {
                return true;
            }
        }

        return false;
    }
}
