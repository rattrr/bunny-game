import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Background {
    private StackPane group = new StackPane();

    public Background(double width, double height){
        Rectangle background = new Rectangle(width, height);
        Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTPINK), new Stop(0.5, Color.WHITE), new Stop(1, Color.LIGHTYELLOW)};
        background.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops));
        group.getChildren().add(background);
        Circle c = new Circle(100, 100, 50, Color.WHITE);
        Circle c2 = new Circle(140, 140, 50, Color.WHITE);
        c.setOpacity(0.20);
        c2.setOpacity(0.40);
        group.getChildren().addAll(c, c2);
    }

    public StackPane getBackground(){
        return group;
    }

}
