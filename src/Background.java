import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Background {
    private Group group = new Group();

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

    public Group getBackground(){
        return group;
    }


}
