import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameScene extends Scene {
    private GameLoop gloop;
    private Group ggroup = new Group();

    public GameScene(Parent root, double width, double height, GameMap gamemap) {
        super(root, width, height);
        Player bunny = new Player(50, 200);
        GameMap gm = gamemap;
        ScoreInfo si = new ScoreInfo(720, 50);
        ggroup.getChildren().add(gm.getBackground());
        ggroup.getChildren().addAll(gm.getBlocks());
        ggroup.getChildren().addAll(gm.getItems());
        ggroup.getChildren().add(bunny.getImage());
        //ggroup.getChildren().add(bunny.getShadowLeftRight().getShape());
        //ggroup.getChildren().add(bunny.getShadowDown().getShape());
        //ggroup.getChildren().add(bunny.getShadowUp().getShape());
        ggroup.getChildren().add(si);
        gloop = new GameLoop(this, bunny, gm, si);
        gloop.start();
    }

    public Group getGameGroup(){
        return ggroup;
    }
}
