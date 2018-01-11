import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class GameScene extends Scene {
    private MainWindow stage;
    private GameLoop gloop;
    private ScrollPane root;
    private Group ggroup = new Group();

    public GameScene(ScrollPane root, MainWindow stage, double width, double height, GameMap gameMap) {
        super(root, width, height);
        this.root = root;
        this.stage = stage;
        root.setContent(ggroup);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Actor bunny = new Actor(1, 1);
        ScoreInfo scoreInfo = new ScoreInfo(700, 50);
        ggroup.getChildren().add(gameMap.getBackground());
        ggroup.getChildren().addAll(gameMap.getBlocks());
        ggroup.getChildren().addAll(gameMap.getItems());
        ggroup.getChildren().add(bunny.getImage());
        ggroup.getChildren().add(scoreInfo);
        gloop = new GameLoop(this, bunny, gameMap, scoreInfo);
        //ggroup.getChildren().add(gloop.getCastDown().getCast());
        //ggroup.getChildren().add(gloop.getCastUp().getCast());
        gloop.start();

    }



    public Group getGameGroup(){
        return ggroup;
    }

    public MainWindow getStage() {
        return stage;
    }

    public ScrollPane getScrollRoot(){ return root;}
}
