import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class GameMap {
    private Image grass = new Image(this.getClass().getClassLoader().getResourceAsStream("img/grass.png"));
    private ArrayList<ImageView> blocks = new ArrayList<>();

    public GameMap(){

        blocks.add(new Block(grass, 200, 305).getImage());
        blocks.add(new Block(grass, 380, 305).getImage());
        blocks.add(new Block(grass, 155, 250).getImage());
        makeFloor();
    }


    private void makeFloor() {
        for (int i = 0; i < 1000; i += 45) {
            Block block = new Block(grass, 0 + i, 355);
            blocks.add(block.getImage());
        }
    }

    public ArrayList<ImageView> getBlocks(){
        return blocks;
    }
}
