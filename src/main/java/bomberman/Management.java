package bomberman;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;

public class Management {
    public static float PLAYER_SPEED = 3;
    public static float x_val_ = 0;
    public static float y_val_ = 0;
    public static boolean on_ground = false;
    private static final String url = "C:\\Users\\Hien Jeony\\Desktop\\OOP\\Bomberman\\data\\map.txt";
    public static void importFromFile() {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(Management.url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            int cnt = 1, level = 0;
            int y = 0, x = 0;
            String[] item = new String[100010];
            String line = bufferedReader.readLine();
            //System.out.println(line);

            while (cnt <= Map.max_y + 1) {
                if (cnt == 1) {
                    item = line.split(" ");
                    level = Integer.parseInt(item[0]);
                    Map.max_y = Integer.parseInt(item[1]);
                    Map.max_x = Integer.parseInt(item[2]);
                    Map.height = (float)(600.0/(float)Map.max_y);
                    Map.weight = (float)(1200.0/(float)Map.max_x);
                } else {
                    x = Map.max_x;
                    for (int i = 0; i < x; i++) {
                        Map.map[y][i] = line.charAt(i);
                        if (Map.map[y][i] == 'p') {
                            Map.player = new Character((float) i * Map.weight, (float) y * Map.height);
                            System.out.println(i);
                            System.out.println(y-1);
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

    public static void checkMap() {
        int x1 = 0;
        int x2 = 0;
        int y1 = 0;
        int y2 = 0;
        x1 = ((int)Map.player.getX() + (int)x_val_) / (int)Map.weight;
        x2 = ((int)Map.player.getX() + (int)x_val_ + (int)Map.weight - 1) / (int)Map.weight;
        y1 = (int)Map.player.getY() / (int)Map.height;
        y2 = ((int)Map.player.getY() + (int)Map.height - 1) / (int)Map.height;
        if (x1 >= 0 && x2 < Map.max_x && y1 >= 0 && y2 < Map.max_y) {
            if (x_val_ > 0) {
                if (Map.map[y1][x2] == '#' || Map.map[y2][x2] == '#' || (Map.map[y1][x2] == '*' && Map.state[y1][x2] == 0) || (Map.map[y2][x2] == '*' && Map.state[y2][x2] == 0)) {
                    Map.player.setX(x2 * Map.weight - Map.weight);
                    x_val_ = 0;
                }
            }
            else if (x_val_ < 0) {
                if (Map.map[y1][x2] == '#' || Map.map[y2][x2] == '#' || (Map.map[y1][x2] == '*' && Map.state[y1][x2] == 0) || (Map.map[y2][x2] == '*' && Map.state[y2][x2] == 0)) {
                    Map.player.setX((x1 + 1) * Map.weight);
                    x_val_ = 0;
                }
            }
        }
        x1 = (int)Map.player.getX() / (int)Map.weight;
        x2 = ((int)Map.player.getX() + (int)Map.weight - 1) / (int)Map.weight;
        y1 = ((int)Map.player.getY() + (int)y_val_) / (int)Map.height;
        y2 = ((int)Map.player.getX() + (int)y_val_ + (int)Map.height - 1) / (int)Map.height;

        if (x1 >= 0 && x2 < Map.max_x && y1 >= 0 && y2 < Map.max_y) {
            if (y_val_ > 0) {
                if (Map.map[y2][x1] == '#' || Map.map[y2][x2] == '#' || (Map.map[y2][x1] == '*' && Map.state[y2][x1] == 0) || (Map.map[y2][x2] == '*' && Map.state[y2][x2] == 0)) {
                    Map.player.setY(y2 * Map.height - Map.height);
                    y_val_ = 0;
                    on_ground = true;
                }
            }
            else if (y_val_ < 0) {
                if (Map.map[y2][x1] == '#' || Map.map[y2][x2] == '#' || (Map.map[y2][x1] == '*' && Map.state[y2][x1] == 0) || (Map.map[y2][x2] == '*' && Map.state[y2][x2] == 0)) {
                    Map.player.setY((y1 + 1) * Map.height);
                    y_val_ = 0;
                }
            }
        }
        Map.player.setX(Map.player.getX() + x_val_);
        Map.player.setY(Map.player.getY() + y_val_);
        if (Map.player.getX() < 0)
            Map.player.setX(0);
        else if (Map.player.getX() + Map.weight >= 1200) {
            Map.player.setX(1200 - Map.weight - 1);
        }
        if (Map.player.getY() < 0)
            Map.player.setY(0);
        else if (Map.player.getY() + Map.height >= 600) {
            Map.player.setY(600 - Map.height - 1);
        }
    }

    public static void inputKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.KP_RIGHT) {
            Map.player.setRight(1);
            Map.player.setLeft(0);
        } else if (e.getCode() == KeyCode.KP_LEFT) {
            Map.player.setRight(0);
            Map.player.setLeft(1);
        } else if (e.getCode() == KeyCode.KP_UP) {
            Map.player.setDown(0);
            Map.player.setUp(1);
        } else if (e.getCode() == KeyCode.KP_DOWN) {
            Map.player.setDown(1);
            Map.player.setUp(0);
        }
    }
    public static void inputKeyRelease(KeyEvent e) {
        if (e.getCode() == KeyCode.KP_RIGHT) {
            Map.player.setRight(0);
        } else if (e.getCode() == KeyCode.KP_LEFT) {
            Map.player.setLeft(0);
        } else if (e.getCode() == KeyCode.KP_UP) {
            Map.player.setUp(0);
        } else if (e.getCode() == KeyCode.KP_DOWN) {
            Map.player.setDown(0);
        }
    }

    public static void doPlayer() {
        x_val_ = 0;
        y_val_ = 0;
        if (Map.player.getLeft() == 1) {
            x_val_ -= PLAYER_SPEED;
        }

        else if (Map.player.getRight() == 1) {
            x_val_ += PLAYER_SPEED;
        }
        if (Map.player.getUp() == 1) {
            y_val_ -= PLAYER_SPEED;
        }
        else if (Map.player.getDown() == 1) {
            y_val_ += PLAYER_SPEED;
        }
        System.out.println(x_val_);
        System.out.println("DDDD");
        Management.checkMap();
    }


}
