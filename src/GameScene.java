import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameScene extends Scene {
    private GameLoop gloop;
    private Group ggroup = new Group();

    public GameScene(Parent root, double width, double height, GameMap gamemap) {
        super(root, width, height);
        Actor bunny = new Actor(50, 200);
        GameMap gm = gamemap;
        ScoreInfo si = new ScoreInfo(720, 50);
        ggroup.getChildren().add(gm.getBackground());
        ggroup.getChildren().addAll(gm.getBlocks());
        ggroup.getChildren().addAll(gm.getItems());
        ggroup.getChildren().add(bunny.getImage());
        ggroup.getChildren().add(si);
        gloop = new GameLoop(this, bunny, gm, si);
        ggroup.getChildren().add(gloop.getCastDown().getCast());
        ggroup.getChildren().add(gloop.getCastUp().getCast());
        gloop.start();
    }

    public Group getGameGroup(){
        return ggroup;
    }
}
