import javafx.animation.FillTransition;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Goal extends Rectangle implements Collidable {

    public Goal(double x, double y, double width, double height){
        super(x, y, width, height);
        setFill(Color.LIGHTCORAL);
    }

    @Override
    public Bounds getBounds() {
        return getBoundsInLocal();
    }

    public void changeColor(Color color){
        FillTransition ft = new FillTransition(Duration.millis(3000), this, Color.LIGHTCORAL, color);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}
