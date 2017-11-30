import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameLoop extends AnimationTimer {
    ArrayList<String> keyboardInput = new ArrayList<>();
    Player bunny;
    GameMap gm;

    GameLoop(Scene scene, Player bunny, GameMap gm){
        listen(scene);
        this.bunny = bunny;
        this.gm = gm;
    }


    @Override
    public void handle(long now) {
        fall();
        if(keyboardInput.contains("LEFT")){
            run(Direction.LEFT);
        }
        if(keyboardInput.contains("RIGHT")){
            run(Direction.RIGHT);
        }
        if(keyboardInput.contains("UP")){

        }
        if(keyboardInput.isEmpty()) {


    }}

    private void run(Direction dir){
        if(dir.equals(Direction.LEFT)){
            bunny.getShadowLeftRight().setX(bunny.getImage().getX()-2);
            bunny.getShadowLeftRight().setY(bunny.getImage().getY());
            if(collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null){
                bunny.getImage().setX(bunny.getImage().getX() - 2);
            }
        }else if(dir.equals(Direction.RIGHT)){
            bunny.getShadowLeftRight().setX(bunny.getImage().getX()+2);
            bunny.getShadowLeftRight().setY(bunny.getImage().getY());
            if(collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null) {
                bunny.getImage().setX(bunny.getImage().getX() + 2);
            }
        }
    }

    private ImageView collision(Rectangle player, ArrayList<ImageView> objects){
        for (ImageView obj : objects){
            if(player.getBoundsInLocal().intersects(obj.getBoundsInLocal())){
                return obj;
            }
        }
        return null;
    }

    private void fall(){
        bunny.getShadowDown().setX(bunny.getImage().getX());
        bunny.getShadowDown().setY(bunny.getImage().getY());
        while (collision(bunny.getShadowDown(), gm.getBlocks()) == null){
            bunny.getShadowDown().setY(bunny.getShadowDown().getY()+1);
        }
        bunny.getShadowDown().setY(bunny.getShadowDown().getY()+1);
    }

    private void listen(Scene scene){
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent event) {
                        String eventCode = event.getCode().toString();
                        if(!keyboardInput.contains(eventCode)) {
                            keyboardInput.add(eventCode);
                        }
                    }
                }
        );

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent event){
                        keyboardInput.remove(event.getCode().toString());
                    }
                }
        );
    }
}
