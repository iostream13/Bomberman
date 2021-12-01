package bomberman.GlobalVariable;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class RenderVariable {
    //************************ CÁC THÔNG SỐ RENDER ************************************************

    public static double SCREEN_WIDTH = 520;
    public static double SCREEN_LENGTH = 1240;

    /**
     * Độ lớn của một ô ảnh trong resource (đơn vị pixel).
     */
    public static final double imageSize = 16;

    // *********************** BIẾN ĐƯỢC SỬ DỤNG GLOBAL ĐỂ RENDER *********************************
    public static Group root = new Group();

    public static Canvas canvas = new Canvas(1240, 520);

    public static GraphicsContext gc = canvas.getGraphicsContext2D();

    public static Scene scene = new Scene(root);
}
