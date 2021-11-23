package bomberman.GlobalVariable;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class RenderVariable {
    public static Group root = new Group();

    public static Canvas canvas = new Canvas(1240, 520);

    public static GraphicsContext gc = canvas.getGraphicsContext2D();

    public static Scene scene = new Scene(root);
}
