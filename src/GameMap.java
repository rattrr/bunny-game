import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;

public class GameMap implements Serializable{
    private double gameWidth = 1000;
    private double gameHeigth = 400;
    private Image grass = new Image(this.getClass().getClassLoader().getResourceAsStream("img/grass.png"));
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Rectangle background;

    public GameMap(){
        fillItems();
    }

    public void def(){
        blocks.add(new Block(100, 300));
        blocks.add(new Block(150, 250));
        blocks.add(new Block(200, 250));
        blocks.add(new Block(250, 250));
        blocks.add(new Block(400, 250));
        blocks.add(new Block(450, 250));
        blocks.add(new Block(550, 200));
        blocks.add(new Block(600, 150));
        makeFloor();
    }

    public void addBlock(Block block){
        blocks.add(block);
    }

    public void fillItems(){
        items.add(new Item(210, 230));
        items.add(new Item(240, 230));
        items.add(new Item(180, 330));
        items.add(new Item(210, 330));
        items.add(new Item(475, 230));
        items.add(new Item(575, 180));
        items.add(new Item(625, 130));
    }


    public void moveBlocks(double distance){
        for(Rectangle block: blocks){
            block.setX(block.getX() + distance);
        }
        for(Circle item: items){
            item.setCenterX(item.getCenterX() + distance);
        }
    }

    public Rectangle getBackground(){
        background = new Rectangle(0, 0, gameWidth, gameHeigth);
        background.setFill(Color.LIGHTBLUE);
        return background;
    }


    private void makeFloor() {
        for (int i = 0; i < gameWidth; i += 50) {
            Block block = new Block(0 + i, 350);
            blocks.add(block);
        }
    }

    public ArrayList<Block> getBlocks(){
        return blocks;
    }
    public ArrayList<Item> getItems(){
        return items;
    }
}
