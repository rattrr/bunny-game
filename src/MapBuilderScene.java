import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MapBuilderScene extends Scene {
    private Group mbgroup = new Group();
    private Canvas grid = new Canvas(1000, 400);
    private Image grass = new Image(this.getClass().getClassLoader().getResourceAsStream("img/grass.png"));
    private GameMap gamemap = new GameMap();
    private Block choosen;
    private ArrayList<Block> blocks = new ArrayList<>();
    public MapBuilderScene(Parent root, double width, double height) {
        super(root, width, height);
        makeGrid();
        makeGridEvents();
        mbgroup.getChildren().add(grid);

    }

    private void makeGrid(){
        GraphicsContext gc = grid.getGraphicsContext2D();
        for(int i=0; i<grid.getHeight(); i+=50){
            gc.strokeLine(0, i, grid.getWidth(), i);
        }

        for(int i=0; i<grid.getWidth(); i+=50){
            gc.strokeLine(i, 0, i, grid.getHeight());
        }
        gc.strokeRect(0, 0, grid.getWidth(), grid.getHeight());
    }

    private void makeGridEvents(){
        grid.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println((findNearest(event.getX()) + " " + findNearest(event.getY())));
            }
        });
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Block block = new Block(findNearest(event.getX()), findNearest(event.getY()));
                mbgroup.getChildren().add(block);
                gamemap.addBlock(block);
                //saveMap();
            }
        });
    }

    private int findNearest(double x){
        return ((int)x/50)*50;
    }

    private void saveMap(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("map.ser"));
            oos.writeObject(this.gamemap);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Group getMbgroup(){
        return mbgroup;
    }
}
