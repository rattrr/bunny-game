package map;

import game.Actor;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameMap implements Serializable{
    private double width;
    private double height;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Coin> collectables = new ArrayList<>();
    private ArrayList<Rectangle> decorations = new ArrayList<>();
    private Background background = new Background(2000, 400);
    private Goal goal;

    public GameMap(double width, double height){
        this.width = width;
        this.height = height;
        background = new Background(width, height);
        goal = new Goal(1700, height-170);
        if(new File("map.ser").isFile()) {
            load();
        }else{
            System.out.println("No map.ser file");
        }
        makeDecorations();
    }

    private void makeDecorations(){
        for(Block block: blocks){
            decorations.add(new Rectangle(block.getX()-1, block.getY()-5, block.getWidth()+3, block.getHeight()+6));
        }
        for(Rectangle rectangle: decorations){
            rectangle.setFill(Color.LIGHTCORAL);
        }
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

    public ArrayList<Rectangle> getDecorations() {
        return decorations;
    }

    public Node getGoal(){ return goal;}

    @SuppressWarnings("unchecked")
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

    public void recolor(Color skyColor, Color blocksColor, Color decoColor){
        background.setGradient(skyColor, Color.WHITE, Color.LIGHTYELLOW);
        for(Block block: blocks){
            block.changeColor(blocksColor);
        }
        for(Rectangle rectangle: decorations){
            rectangle.setFill(decoColor);
        }
    }

    public boolean collidedWithGoal(Actor actor){
        return actor.getBounds().intersects(goal.getBounds());
    }

    public boolean actorOutOfMap(Actor actor){
        return actor.getY() > height;
    }

    public double getWidth(){
        return width;
    }

}
