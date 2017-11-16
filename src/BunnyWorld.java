import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BunnyWorld extends Application {

    public static void main(String [] argv){
        launch(argv);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bunny World!");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(800, 400);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList <String> keyboardInput = new ArrayList<>();

        Bunny player = new Bunny(50, 350);
        gc.drawImage(player.getCurrentState(), player.getPosX(), player.getPosY());

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent event) {
                        String eventCode = event.getCode().toString();
                        if(!keyboardInput.contains(eventCode)) {
                            keyboardInput.add(eventCode);
                        }
                    }
                }
        );

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent event){
                        keyboardInput.remove(event.getCode().toString());
            }
        }
        );

        new AnimationTimer(){
            public void handle(long now){
                if(keyboardInput.contains("LEFT")){
                    gc.clearRect(0,0,800, 400);
                    gc.drawImage(player.moveLeft(), player.getPosX(), player.getPosY());
                }
                if(keyboardInput.contains("RIGHT")){
                    gc.clearRect(0,0,800, 400);
                    gc.drawImage(player.moveRight(), player.getPosX(), player.getPosY());
                }
            }
        }.start();

        primaryStage.show();
    }
}
