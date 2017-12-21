import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Item extends Circle implements Collidable{
    public Item(double x, double y){
        super(x, y, 10);
        setFill(Color.GOLD);
        setStroke(Color.GOLDENROD);

    }

    public double getY(){
        return getCenterY();
    }

    @Override
    public Bounds getBounds() {
        return getBoundsInLocal();
    }
}
