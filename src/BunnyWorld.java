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
        Group game = new Group();

        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        root.getChildren().add(game);
        Player bunny = new Player(155, 200);
        GameMap gm = new GameMap();
        game.getChildren().addAll(gm.getBlocks());
        game.getChildren().add(bunny.getImage());
        game.getChildren().add(bunny.getShadowLeftRight());
        game.getChildren().add(bunny.getShadowDown());

        GameLoop gl = new GameLoop(scene, bunny, gm);
        gl.start();

        primaryStage.show();
    }

}
