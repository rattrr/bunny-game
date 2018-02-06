package map;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Background {
    private StackPane pane = new StackPane();
    private Rectangle background;

    public Background(double width, double height){
        background = new Rectangle(width, height);
        setGradient(Color.LIGHTPINK, Color.WHITE, Color.LIGHTYELLOW);
        pane.getChildren().add(background);

    }

    public StackPane getBackground(){
        return pane;
    }

    void setGradient(Color start, Color middle, Color end){
        Stop[] stops = new Stop[] { new Stop(0, start), new Stop(0.5, middle), new Stop(1, end)};
        background.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops));
    }

}
