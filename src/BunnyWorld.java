import javafx.application.Application;
import javafx.scene.Group;
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
        //initGame(primaryStage);
        initMapBuilder(primaryStage);
    }

    public void initGame(Stage primaryStage){
        Group gameroot = new Group();

        GameMap gm = new GameMap();
        gm.def();

        GameScene gscene = new GameScene(gameroot, sceneWidth, sceneHeight, gm);
        primaryStage.setScene(gscene);

        gameroot.getChildren().add(gscene.getGameGroup());



        primaryStage.show();
    }

    public void initMapBuilder(Stage primaryStage){
        Group mbroot = new Group();
        MapBuilderScene mbscene = new MapBuilderScene(mbroot, sceneWidth, sceneHeight+100);
        primaryStage.setScene(mbscene);

        mbroot.getChildren().add(mbscene.getMbgroup());

        primaryStage.show();
    }

}
