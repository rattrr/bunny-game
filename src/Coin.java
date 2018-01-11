import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends Circle implements Collidable{
    public Coin(double x, double y){
        super(x, y, 10);
        setFill(Color.LIGHTYELLOW);
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
