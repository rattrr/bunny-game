package gui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Line;
import map.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

class MapBuilderScene extends Scene {
    private MainWindow stage;
    private Canvas grid = new Canvas(2000, 400);
    private Background background = new Background(2000, 400);
    private Group box = new Group();
    private ArrayList<MapObjectBlueprint> blueprints = new ArrayList<>();

    MapBuilderScene(ScrollPane root, MainWindow stage, double width, double height) {
        super(root, width, height);
        this.stage = stage;
        init();
        root.setContent(box);
        root.setMinWidth(2000);
        if(new File("map.ser").isFile()) {
            loadMap();
        }

    }

    private void init(){
        box.getChildren().add(background.getBackground());
        makeGrid();
        makeGridEvents();
        displayManual();
    }

    private void displayManual(){
        Notification notification = new Notification("Set block LMB\nRemove block or set coin RMB\nFill floor with block F\nClear map C\nReturn to menu BACKSPACE\n", 16, 0.5*getWidth(), 0.5*getHeight());
        notification.displayAt(box, 4);
    }

    private void makeGrid(){

        for(int i=0; i<grid.getHeight(); i+=50){
            box.getChildren().add(new Line(0, i, grid.getWidth(), i));
        }

        for(int i=0; i<grid.getWidth(); i+=50){
            box.getChildren().add(new Line(i, 0, i, grid.getHeight()));
        }
    }

    private void makeGridEvents(){
        box.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                Block block = new Block(findNearest(event.getX()), findNearest(event.getY()));
                box.getChildren().add(block);
                blueprints.add(new MapObjectBlueprint(MapObject.BLOCK, findNearest(event.getX()), findNearest(event.getY())));
                saveMap();
            }
            if(event.getButton() == MouseButton.SECONDARY) {
                MapObjectBlueprint blueprint;
                if((blueprint =checkIfBlockExist(new MapObjectBlueprint(MapObject.BLOCK, findNearest(event.getX()), findNearest(event.getY())))) != null){
                    blueprints.remove(blueprint);
                    box.getChildren().remove(removeBlueprintFromBox(blueprint));
                }else {
                    Coin coin = new Coin(event.getX(), event.getY());
                    box.getChildren().add(coin);
                    blueprints.add(new MapObjectBlueprint(MapObject.COIN, event.getX(), event.getY()));
                }
                saveMap();
            }
        });
        this.setOnKeyPressed(event -> {
            if(Objects.equals(event.getCode().toString(), "F")){
                for (int i = 0; i < grid.getWidth(); i += 50) {
                    box.getChildren().add(new Block(i, 350));
                    blueprints.add(new MapObjectBlueprint(MapObject.BLOCK, i, 350));
                }
            }

            if(Objects.equals(event.getCode().toString(), "C")){
                blueprints.clear();
                box.getChildren().clear();
                init();
            }

            if(Objects.equals(event.getCode().toString(), "BACK_SPACE")){
                stage.initMenu();

            }
        });

    }

    private MapObjectBlueprint checkIfBlockExist(MapObjectBlueprint block){
        for(MapObjectBlueprint blueprint : blueprints){
            if(blueprint.getType() == MapObject.BLOCK && blueprint.equals(block)){
                return blueprint;
            }
        }
        return null;
    }

    private Block removeBlueprintFromBox(MapObjectBlueprint blueprint){
        for(Node object : box.getChildren()){
            if(object instanceof Block){
                Block block = (Block) object;
                if(block.getX() == blueprint.getX() && block.getY() == blueprint.getY()){
                    return block;
                }
            }
        }
        return null;
    }

    private int findNearest(double x){
        return ((int)x/50)*50;
    }


    private void saveMap(){
        try{
            FileOutputStream fos= new FileOutputStream("map.ser");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(blueprints);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadMap(){
        try
        {
            FileInputStream fis = new FileInputStream("map.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            blueprints = (ArrayList<MapObjectBlueprint>) ois.readObject();
            for(MapObjectBlueprint blueprint : blueprints){
                if(blueprint.getType().equals(MapObject.BLOCK)) {
                    box.getChildren().add(new Block(blueprint.getX(), blueprint.getY()));
                }else if(blueprint.getType().equals(MapObject.COIN)){
                    box.getChildren().add(new Coin(blueprint.getX(), blueprint.getY()));
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace(); }
    }
}
