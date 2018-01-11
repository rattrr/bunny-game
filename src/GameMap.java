import javafx.scene.Group;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameMap implements Serializable{
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Coin> items = new ArrayList<>();
    private Background background = new Background(2000, 400);

    public GameMap(){
        load();
    }


    public Group getBackground() {
        return background.getBackground();
    }


    public ArrayList<Block> getBlocks(){
        return blocks;
    }
    public ArrayList<Coin> getItems(){
        return items;
    }

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

}
