import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class GameScene extends Scene {
    private MainWindow stage;
    private GameLoop gameLoop;
    private ScrollPane scrollableArea = new ScrollPane();
    private Group gameObjects = new Group();
    private Pane gameInterface = new Pane();

    public GameScene(StackPane root, MainWindow stage, double width, double height, GameMap gameMap) {
        super(root, width, height);
        this.stage = stage;
        scrollableArea.setContent(gameObjects);
        scrollableArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollableArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Actor bunny = new Actor(1, 1, gameMap);
        gameObjects.getChildren().add(gameMap.getBackground());
        gameObjects.getChildren().addAll(gameMap.getBlocks());
        gameObjects.getChildren().addAll(gameMap.getItems());
        gameObjects.getChildren().add(bunny.getImage());
        gameObjects.getChildren().add(bunny.getCastUp());
        gameObjects.getChildren().add(bunny.getCastDown());
        ScoreInfo scoreInfo = new ScoreInfo(732, 50);
        gameLoop = new GameLoop(this, bunny, gameMap, scoreInfo);
        gameLoop.start();
        gameInterface.getChildren().add(scoreInfo);
        root.getChildren().add(scrollableArea);
        root.getChildren().add(gameInterface);

    }



    public Group getGameGroup(){
        return gameObjects;
    }

    public MainWindow getStage() {
        return stage;
    }

    public ScrollPane getScrollRoot(){ return scrollableArea;}
}
