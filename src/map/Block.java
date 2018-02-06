package map;

import javafx.animation.FillTransition;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Block extends Rectangle implements Collidable {

    public Block(double posX, double posY){
        super(posX, posY, 50, 50);
        setFill(Color.PINK);
    }

    @Override
    public Bounds getBounds() {
        return getBoundsInLocal();
    }

    void changeColor(Color color){
        FillTransition ft = new FillTransition(Duration.millis(3000), this, Color.PINK, color);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


}
