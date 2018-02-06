package map;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.shape.Rectangle;



public class Goal extends ImageView implements Collidable {

    Goal(double x, double y){
        super();
        setX(x);
        setY(y);
        setImage(new Image(this.getClass().getClassLoader().getResourceAsStream("img/carrot.png")));
    }

    @Override
    public Bounds getBounds() {
        return new Rectangle(getX()+100, getY(),50, 100).getBoundsInLocal();
    }


}
