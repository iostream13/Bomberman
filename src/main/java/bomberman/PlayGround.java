package bomberman;

import java.io.*;

public class PlayGround {
    public int level;
    public int numberOfRow = 0;
    public int numberOfColumn = 0;
    //độ dài cạnh của một ô
    public float cellLength = 40;
    //các ô trên bản đồ, mỗi ô là 1 object
    public Object[][] cells = new Object[50][50];
    private static final String linkToMap = "/Map/map.txt";

    /**
     * import data from file.
     */
    PlayGround() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream("src/main/java/resources/Map/map.txt");
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
                                GamePlay.player = new Boomber(cellLength * i, cellLength * y);
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
                                cells[y][i] = new Item(cellLength * i, cellLength * y, cellLength, cellLength, Item.typeOfItems.BombItem);
                                break;
                            case 'f':
                                cells[y][i] = new Item(cellLength * i, cellLength * y, cellLength, cellLength, Item.typeOfItems.FlameItem);
                                break;
                            case 's':
                                cells[y][i] = new Item(cellLength * i, cellLength * y, cellLength, cellLength, Item.typeOfItems.SpeedItem);
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
        }catch (FileNotFoundException ex) {
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

    // kiểm tra một ô có bị chặn không
    public boolean isBlockCell(int x, int y) {
        //các trường hợp bị chặn
        //ô tường
        if ((cells[x][y] instanceof Wall)) {
            return true;
        }

        // ô gạch chưa bị nổ
        if (cells[x][y] instanceof Brick) {
            if (!((Brick) cells[x][y]).exploded) {
                return true;
            }
        }

        // ô item chưa bị nổ
        if ((cells[x][y] instanceof Item)) {
            if (((Item) cells[x][y]).isHidden) return true;
        }

        // ô portal chưa bị nổ
        if ((cells[x][y] instanceof Portal)) {
            if (((Portal) cells[x][y]).isBlocked) return true;
        }

        return false;
    }
}
