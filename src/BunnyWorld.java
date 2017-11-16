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

    private int sceneWidth = 800;
    private int sceneHeight = 400;


    public static void main(String [] argv){
        launch(argv);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bunny World!");
        Group root = new Group();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(sceneWidth, sceneHeight);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList <String> keyboardInput = new ArrayList<>();

        Bunny player = new Bunny(50, 350);

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
                    gc.clearRect(0,0,sceneWidth, sceneHeight);
                    player.moveLeft();
                    player.render(gc);
                }
                if(keyboardInput.contains("RIGHT")){
                    gc.clearRect(0,0,sceneWidth, sceneHeight);
                    player.moveRight();
                    player.render(gc);
                }
                if(keyboardInput.contains("UP")){
                    gc.clearRect(0, 0, sceneWidth, sceneHeight);
                    player.setStandingState();
                    player.render(gc);
                }
                if(keyboardInput.isEmpty()) {
                    gc.clearRect(0, 0, sceneWidth, sceneHeight);
                    if (player.looksRight()) {
                        player.setDefaultStateRight();
                        player.render(gc);
                    } else {
                        player.setDefaultStateLeft();
                        player.render(gc);
                    }
                }
            }
        }.start();

        primaryStage.show();
    }

    private void startGameLoop(){

    }
}
