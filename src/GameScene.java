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
        ggroup.getChildren().add(gm.getBackground());
        ggroup.getChildren().addAll(gm.getBlocks());
        ggroup.getChildren().add(bunny.getImage());
        //game.getChildren().add(bunny.getShadowLeftRight());
        //game.getChildren().add(bunny.getShadowDown());
        gloop = new GameLoop(this, bunny, gm);
        gloop.start();
    }

    public Group getGameGroup(){
        return ggroup;
    }
}
