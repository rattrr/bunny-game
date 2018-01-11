import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

import java.awt.*;

public class GameScene extends Scene {
    private GameLoop gloop;
    private ScrollPane root;
    private Group ggroup = new Group();

    public GameScene(ScrollPane root, double width, double height, GameMap gamemap) {
        super(root, width, height);
        this.root = root;
        root.setContent(ggroup);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Actor bunny = new Actor(1, 1);
        GameMap gm = gamemap;
        ScoreInfo si = new ScoreInfo(700, 50);
        ggroup.getChildren().add(gm.getBackground());
        ggroup.getChildren().addAll(gm.getBlocks());
        ggroup.getChildren().addAll(gm.getItems());
        ggroup.getChildren().add(bunny.getImage());
        ggroup.getChildren().add(si);
        gloop = new GameLoop(this, bunny, gm, si);
        //ggroup.getChildren().add(gloop.getCastDown().getCast());
        ggroup.getChildren().add(gloop.getCastUp().getCast());
        gloop.start();
    }

    public Group getGameGroup(){
        return ggroup;
    }

    public ScrollPane getScrollRoot(){ return root;}
}
