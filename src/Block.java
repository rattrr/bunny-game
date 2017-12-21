import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends Rectangle implements Collidable{

    public Block(int posX, int posY){
        super(posX, posY, 50, 50);
        setFill(Color.LIMEGREEN);
    }

    @Override
    public Bounds getBounds() {
        return getBoundsInLocal();
    }


}
