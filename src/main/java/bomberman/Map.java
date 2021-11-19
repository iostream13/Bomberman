package bomberman;

import java.util.ArrayList;

public class Map {
    public static char[][] map = new char[1010][1010];
    public static int[][] state = new int[1010][1010];
    public static int max_y = 0;
    public static int max_x = 0;
    public static float height;
    public static float weight;
    public static int level = 1;
    public static Character player;
    public static ArrayList <Character> enemy = new ArrayList<Character>();
}
