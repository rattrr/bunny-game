import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


public class BunnyWorld extends Application {

    private double sceneWidth = 800;
    private double sceneHeight = 400;


    public static void main(String [] argv){
        launch(argv);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bunny World!");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(sceneWidth, sceneHeight);
        root.getChildren().add(canvas);
        //Player bunny = new Player(50, 313);
        Player bunny = new Player(155, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameMap gm = new GameMap(gc, bunny, sceneWidth, sceneHeight);

        GameLoop gl = new GameLoop(scene, bunny, gm);
        gl.start();

        primaryStage.show();
    }

}
