import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameMap implements Serializable{
    private double width;
    private double height;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Coin> items = new ArrayList<>();
    private Background background = new Background(2000, 400);
    private Goal goal;

    public GameMap(double width, double height){
        this.width = width;
        this.height = height;
        background = new Background(width, height);
        goal = new Goal(700, height-150, 70, 70);
        load();
    }

    public StackPane getBackground() {
        return background.getBackground();
    }

    public ArrayList<Block> getBlocks(){
        return blocks;
    }
    public ArrayList<Coin> getItems(){
        return items;
    }
    public Rectangle getGoal(){ return goal;}

    private void load(){
        ArrayList <MapObjectBlueprint> blueprints;
        try
        {
            FileInputStream fis = new FileInputStream("map.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            blueprints = (ArrayList<MapObjectBlueprint>) ois.readObject();
            for(MapObjectBlueprint bp: blueprints){
                if(bp.getType().equals(MapObject.BLOCK)) {
                    blocks.add(new Block(bp.getX(), bp.getY()));
                }else if(bp.getType().equals(MapObject.COIN)){
                    items.add(new Coin(bp.getX(), bp.getY()));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
    }

    public Collidable collision(Collidable player, ArrayList<? extends Collidable> objects){
        for (Collidable obj : objects){
            if(player.getBounds().intersects(obj.getBounds())){
                return obj;
            }
        }
        return null;
    }

    public void recolor(Color skyColor, Color blocksColor){
        background.setGradient(skyColor, Color.WHITE, Color.LIGHTYELLOW);
        for(Block block: blocks){
            block.changeColor(blocksColor);
        }
        if(blocksColor == Color.GREY){
            goal.changeColor(blocksColor);
        }
    }

    public boolean collidedWithGoal(Actor actor){
        return actor.getBounds().intersects(goal.getBounds());
    }

    public boolean actorOutOfMap(Actor actor){
        return actor.getY() > height;
    }

}
