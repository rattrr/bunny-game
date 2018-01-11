import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MapBuilderScene extends Scene {
    private Canvas grid = new Canvas(2000, 400);
    private Background background = new Background();
    private Group box = new Group();
    private ArrayList<MapObjectBlueprint> blocks = new ArrayList<>();
    public MapBuilderScene(ScrollPane root, double width, double height) {
        super(root, width, height);
        box.getChildren().add(background.getBackground());
        makeGrid();
        makeGridEvents();
        root.setContent(box);
        root.setMinWidth(2000);
        System.out.println(root.widthProperty());

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
        box.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println((findNearest(event.getX()) + " " + findNearest(event.getY())));
            }
        });
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY) {
                    Block block = new Block(findNearest(event.getX()), findNearest(event.getY()));
                    box.getChildren().add(block);
                    blocks.add(new MapObjectBlueprint(MapObject.BLOCK, findNearest(event.getX()), findNearest(event.getY())));
                    saveMap();
                }
                if(event.getButton() == MouseButton.SECONDARY) {
                    Coin coin = new Coin(event.getX(), event.getY());
                    box.getChildren().add(coin);
                    blocks.add(new MapObjectBlueprint(MapObject.COIN, event.getX(), event.getY()));
                    saveMap();
                }
            }
        });
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().toString() == "F"){
                    for (int i = 0; i < grid.getWidth(); i += 50) {
                        box.getChildren().add(new Block(0+i, 350));
                        blocks.add(new MapObjectBlueprint(MapObject.BLOCK, 0+i, 350));
                    }
                }
            }
        });

    }

    private int findNearest(double x){
        return ((int)x/50)*50;
    }


    private void saveMap(){
        try{
            FileOutputStream fos= new FileOutputStream("map.ser");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(blocks);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
