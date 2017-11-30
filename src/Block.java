import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Block {
    private ImageView img;

    public Block(Image image, int posX, int posY){
        img = new ImageView(image);
        img.setX(posX);
        img.setY(posY);
    }


    public ImageView getImage() {
        return img;
    }

}
