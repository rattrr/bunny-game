import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameMap implements Serializable{
    private double width;
    private double height;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Coin> collectables = new ArrayList<>();
    private Background background = new Background(2000, 400);
    private Goal goal;

    public GameMap(double width, double height){
        this.width = width;
        this.height = height;
        background = new Background(width, height);
        goal = new Goal(1700, height-350, 70, 70);
        load();
    }

    public StackPane getBackground() {
        return background.getBackground();
    }

    public ArrayList<Block> getBlocks(){
        return blocks;
    }
    public ArrayList<Coin> getCollectables(){
        return collectables;
    }
    public Node getGoal(){ return goal;}

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
                    collectables.add(new Coin(bp.getX(), bp.getY()));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
    }

    public Collidable collisionWithBlocks(Collidable player){
        for (Collidable obj : blocks){
            if(player.getBounds().intersects(obj.getBounds())){
                return obj;
            }
        }
        return null;
    }

    public Collidable collisionWithCollectable(Collidable player){
        for (Collidable obj : collectables){
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

    double getWidth(){
        return width;
    }

    double getHeight(){
        return height;
    }

}
