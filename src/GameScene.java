import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GameScene extends Scene {
    private MainWindow stage;
    private GameLoop gameLoop;
    private GameMap gameMap;
    private Actor bunny;
    private ScrollPane scrollableArea = new ScrollPane();
    private Group gameObjects = new Group();
    private Pane gameInterface = new Pane();

    public GameScene(StackPane root, MainWindow stage, double width, double height, GameMap gameMap) {
        super(root, width, height);
        this.stage = stage;
        this.gameMap = gameMap;
        scrollableArea.setContent(gameObjects);
        scrollableArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollableArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bunny = new Actor(20, 20, gameMap);
        gameObjects.getChildren().add(gameMap.getBackground());
        gameObjects.getChildren().add(gameMap.getGoal());
        gameObjects.getChildren().addAll(gameMap.getBlocks());
        gameObjects.getChildren().addAll(gameMap.getCollectables());
        gameObjects.getChildren().add(bunny.getImage());
        gameObjects.getChildren().add(bunny.getCastUp());
        //gameObjects.getChildren().add(bunny.getCastDown());
        ScoreInfo scoreInfo = new ScoreInfo(732, 50);
        gameLoop = new GameLoop(this, bunny, gameMap, scoreInfo);
        gameLoop.start();
        gameInterface.getChildren().add(scoreInfo);
        root.getChildren().add(scrollableArea);
        root.getChildren().add(gameInterface);

    }

    public void displayMessage(String message, int size){
        Text text = new Text(getWidth()*0.5 - size, getHeight()*0.5, message);
        text.setFill(Color.LIGHTCORAL);
        text.setStroke(Color.CORAL);
        text.setFont(Font.font(size));
        gameInterface.getChildren().add(text);
    }

    public void resetScrollPosition(){
        scrollableArea.setHvalue(0);
    }

    public void updateScrollPosition(){
        scrollableArea.setHvalue(scrollableArea.getHmax() * ((bunny.getX() + bunny.getWidth()) / gameMap.getWidth()));
    }


    public Group getGameGroup(){
        return gameObjects;
    }

    public void backToMenu() {
        stage.initMenu();
    }

}
