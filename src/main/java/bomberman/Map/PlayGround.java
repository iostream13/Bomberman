package bomberman.Map;

import java.io.*;
import java.util.ArrayList;

import bomberman.GlobalVariable.FilesPath;
import bomberman.GlobalVariable.GameVariables;

import bomberman.Object.MovingObject.Bomber.Bomber;
import bomberman.Object.MovingObject.Threats.Enemy;
import bomberman.Object.MovingObject.Threats.Balloom;
import bomberman.Object.MovingObject.Threats.Oneal;
import bomberman.Object.NonMovingObject.*;
import bomberman.Object.GameObject;

public class PlayGround {

    /**
     * số level.
     */
    public int maxLevel;

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
    public double cellLength = GameVariables.cellLength;

    /**
     * Các ô trên bản đồ, mỗi ô là 1 object.
     */
    public GameObject[][] cells = new GameObject[50][50];

    /**
     * List player.
     */
    public ArrayList<Bomber> players = new ArrayList<>();

    /**
     * Threats.
     */
    public ArrayList<Enemy> enemies = new ArrayList<>();

    /**
     * List bomb.
     */
    public ArrayList<Bomb> bombs = new ArrayList<>();

    /**
     * Trạng thái ô đó có bomb hay không.
     * True là có bomb, false là không có bomb.
     */
    public boolean[][] stateBomb = new boolean[50][50];

    /**
     * List flame.
     */
    public ArrayList<Flame> flames = new ArrayList<>();

    /**
     * Lưu tất cả các bản đồ của các level.
     */
    private char[][][] allLevelMaps = new char[5][50][50];

    /**
     * kích thước bản đồ theo chiều ngang.
     */
    public double mapLength;

    /**
     * kích thước bản đồ theo chiều dọc.
     */
    public double mapWidth;

    /**
     * Nhập dữ liệu của map từ file.
     */
    private void readMapsFromFile() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            fileInputStream = new FileInputStream(FilesPath.MAP_PATH);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String[] item;

            String line = bufferedReader.readLine();
            item = line.split(" ");
            maxLevel = Integer.parseInt(item[0]);
            numberOfRow = Integer.parseInt(item[1]);
            numberOfColumn = Integer.parseInt(item[2]);

            mapLength = cellLength * numberOfColumn;
            mapWidth = cellLength * numberOfRow;

            for (int levelNow = 0; levelNow < maxLevel; levelNow++) {
                for (int i = 0; i < numberOfRow; i++) {
                    line = bufferedReader.readLine();
                    for (int j = 0; j < numberOfColumn; j++) {
                        allLevelMaps[levelNow][i][j] = line.charAt(j);
                    }
                }
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
     * Tạo tình trạng map của level hiện tại.
     */
    public void createMapAtLevel() {
        for (int y = 0; y < numberOfRow; y++) {
            for (int i = 0; i < numberOfColumn; i++) {
                char tmp = allLevelMaps[level][y][i];
                switch (tmp) {
                    case 'p':
                        players.add(new Bomber(cellLength * i, cellLength * y));
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
                        enemies.add(new Balloom(cellLength * i, cellLength * y));
                        cells[y][i] = new Grass(cellLength * i, cellLength * y, cellLength, cellLength);
                        break;
                    case '2':
                        enemies.add(new Oneal(cellLength * i, cellLength * y));
                        cells[y][i] = new Grass(cellLength * i, cellLength * y, cellLength, cellLength);
                        break;
                    default:
                        cells[y][i] = new Grass(cellLength * i, cellLength * y, cellLength, cellLength);
                }
            }
        }

        for (int i = 0; i < numberOfRow; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                stateBomb[i][j] = false;
            }
        }
    }

    /**
     * Khởi tạo 1 PlayGround(Map).
     */
    public PlayGround() {
        readMapsFromFile();

        createMapAtLevel();
    }

    /**
     * Kiểm tra một ô có bị chặn không.
     *
     * @param temp_x chỉ số x
     * @param temp_y chỉ số y
     * @return có bị block hoặc không
     */
    public boolean isBlockCell(int temp_x, int temp_y) {
        //các trường hợp bị chặn
        //ô tường
        if ((cells[temp_x][temp_y] instanceof Wall)) {
            return true;
        }

        // ô gạch chưa bị nổ
        if (cells[temp_x][temp_y] instanceof Brick) {
            if (!((Brick) cells[temp_x][temp_y]).isFinalState()) {
                return true;
            }
        }

        // ô item chưa bị nổ
        if ((cells[temp_x][temp_y] instanceof Item)) {
            if (!((Item) cells[temp_x][temp_y]).isFinalState()) {
                return true;
            }
        }

        // ô portal chưa bị nổ
        if ((cells[temp_x][temp_y] instanceof Portal)) {
            if (!((Portal) cells[temp_x][temp_y]).isFinalState()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Render map ra screen.
     */
    public void render() {
        for (int i = 0; i < numberOfRow; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                cells[i][j].draw();
            }
        }

        for (Bomb x : bombs) {
            x.draw();
        }

        for (Bomber player : players) {
            if (player != null) {
                player.draw();
            }
        }

        for (Enemy enemy : enemies) {
            enemy.draw();
        }

        for (Flame flame : flames) {
            flame.draw();
        }
    }
}
