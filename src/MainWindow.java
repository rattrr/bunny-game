import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Stage {
    private double sceneWidth = 800;
    private double sceneHeight = 400;

    public void initMenu(){
        StackPane menuRoot = new StackPane();
        MenuScene menuScene = new MenuScene(menuRoot, this, sceneWidth, sceneHeight);

        setScene(menuScene);
        show();
    }

    public void initGame(){
        ScrollPane gameRoot = new ScrollPane();

        GameMap gameMap = new GameMap();

        GameScene gameScene = new GameScene(gameRoot, this, sceneWidth, sceneHeight, gameMap);
        setScene(gameScene);

        show();
    }

    public void initMapBuilder(){
        ScrollPane mbroot = new ScrollPane();
        MapBuilderScene mbscene = new MapBuilderScene(mbroot, this, sceneWidth, sceneHeight+20);
        setScene(mbscene);

        show();

    }
}
