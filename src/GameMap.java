import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;

public class GameMap implements Serializable{
    private double gameWidth = 1000;
    private double gameHeigth = 400;
    private Image grass = new Image(this.getClass().getClassLoader().getResourceAsStream("img/grass.png"));
    private ArrayList<ImageView> blocks = new ArrayList<>();
    private Rectangle background;

    public GameMap(){
        //def();
    }

    public void def(){
        blocks.add(new Block(grass, 100, 305).getImage());
        blocks.add(new Block(grass, 150, 250).getImage());
        blocks.add(new Block(grass, 200, 200).getImage());
        blocks.add(new Block(grass, 250, 220).getImage());
        blocks.add(new Block(grass, 295, 220).getImage());
        blocks.add(new Block(grass, 330, 220).getImage());
        blocks.add(new Block(grass, 380, 150).getImage());
        blocks.add(new Block(grass, 425, 150).getImage());
        makeFloor();
    }

    public void addBlock(Block block){
        blocks.add(block.getImage());
    }

    public void moveBlocks(double distance){
        for(ImageView block: blocks){
            block.setX(block.getX() + distance);
        }
    }

    public Rectangle getBackground(){
        background = new Rectangle(0, 0, gameWidth, gameHeigth);
        background.setFill(Color.LIGHTBLUE);
        return background;
    }


    private void makeFloor() {
        for (int i = 0; i < gameWidth; i += 45) {
            Block block = new Block(grass, 0 + i, 355);
            blocks.add(block.getImage());
        }
    }

    public ArrayList<ImageView> getBlocks(){
        return blocks;
    }
}
