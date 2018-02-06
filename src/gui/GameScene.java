package gui;

import game.Actor;
import game.GameLoop;
import map.GameMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GameScene extends Scene {
    private MainWindow stage;
    private GameMap gameMap;
    private Actor bunny;
    private ScrollPane scrollableArea = new ScrollPane();
    private Group gameObjects = new Group();
    private Pane gameInterface = new Pane();

    GameScene(StackPane root, MainWindow stage, double width, double height, GameMap gameMap) {
        super(root, width, height);
        this.stage = stage;
        this.gameMap = gameMap;
        scrollableArea.setContent(gameObjects);
        scrollableArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollableArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bunny = new Actor(20, 20, 120, gameMap);
        gameObjects.getChildren().add(gameMap.getBackground());
        gameObjects.getChildren().addAll(gameMap.getDecorations());
        gameObjects.getChildren().add(gameMap.getGoal());
        gameObjects.getChildren().addAll(gameMap.getBlocks());
        gameObjects.getChildren().addAll(gameMap.getCollectables());
        gameObjects.getChildren().add(bunny.getImage());
        ScoreInfo scoreInfo = new ScoreInfo(732, 50);
        GameLoop gameLoop = new GameLoop(this, bunny, gameMap, scoreInfo);
        //gameObjects.getChildren().add(gameLoop.getCastUp().getCast());
        //gameObjects.getChildren().add(gameLoop.getCastDown().getCast());
        gameLoop.start();
        gameInterface.getChildren().add(scoreInfo);
        root.getChildren().add(scrollableArea);
        root.getChildren().add(gameInterface);

    }

    public void displayMessage(String message, int fontSize, int seconds){
        Notification notification = new Notification(message, fontSize, 0.5*getWidth(), 0.5*getHeight());
        notification.displayAt(gameInterface, seconds);
    }

    public void displayWinLoseMessage(String message, int size){
        Text text = new Text(getWidth()*0.2 - size, getHeight()*0.5, message);
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
