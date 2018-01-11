import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class BunnyWorld extends Application {

    private double sceneWidth = 800;
    private double sceneHeight = 400;


    public static void main(String [] argv){
        launch(argv);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Bunny World!");
        primaryStage.setResizable(false);
        initGame(primaryStage);
        //initMapBuilder(primaryStage);
    }

    public void initGame(Stage primaryStage){
        ScrollPane gameroot = new ScrollPane();

        GameMap gm = new GameMap();

        GameScene gscene = new GameScene(gameroot, sceneWidth, sceneHeight, gm);
        primaryStage.setScene(gscene);




        primaryStage.show();
    }

    public void initMapBuilder(Stage primaryStage){
        ScrollPane mbroot = new ScrollPane();
        MapBuilderScene mbscene = new MapBuilderScene(mbroot, sceneWidth, sceneHeight+20);
        primaryStage.setScene(mbscene);

        primaryStage.show();

    }

}
