import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shadow implements Collidable {
    private Rectangle shape;
    public Shadow(double x, double y, double width, double height){
        shape = new Rectangle(x, y, width, height);
        shape.setStroke(Color.DEEPPINK);
        shape.setFill(Color.TRANSPARENT);
    }
    public Bounds getBounds(){
        return shape.getBoundsInLocal();
    }

    public Rectangle getShape(){
        return shape;
    }
}
