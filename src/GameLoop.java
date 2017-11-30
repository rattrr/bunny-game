import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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

        ImageView collided_object = collision_p(bunny, gm.getBlocks());
        if(collided_object != null){
            if(bunny.getTimeline() != null){
                bunny.getTimeline().stop();
                if(bunny.isJumping()) {
                    bunny.getImage().setY(collided_object.getY() + 46);
                    bunny.setJumping(false);
                    fall();
                }
            }
        }

        if(keyboardInput.contains("LEFT")){
            run(Direction.LEFT);
        }
        if(keyboardInput.contains("RIGHT")){
            run(Direction.RIGHT);
        }
        if(keyboardInput.contains("UP")){
            if(!bunny.isJumping() && !bunny.isFalling()){
                bunny.setJumping(true);
                bunny.setTimeLine(bunny.makeJumpingTimeline(100));
                bunny.getTimeline().play();
            }
        }
        if(keyboardInput.isEmpty()) {


    }}

    private void run(Direction dir){
        if(dir.equals(Direction.LEFT)){
            bunny.getShadowLeftRight().setX(bunny.getImage().getX()-2);
            bunny.getShadowLeftRight().setY(bunny.getImage().getY());
            if((collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null) && !bunny.isFalling()){
                gm.moveBlocks(0.5);
                bunny.move(dir);
                bunny.getImage().setX(bunny.getImage().getX() - 2);
            }
        }else if(dir.equals(Direction.RIGHT)){
            bunny.getShadowLeftRight().setX(bunny.getImage().getX()+2);
            bunny.getShadowLeftRight().setY(bunny.getImage().getY());
            if((collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null) && !bunny.isFalling()){
                gm.moveBlocks(-0.5);
                bunny.move(dir);
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

    private ImageView collision_p(Player player, ArrayList<ImageView> objects){
        for (ImageView obj : objects){
            if(player.getImage().getBoundsInLocal().intersects(obj.getBoundsInLocal())){
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
        bunny.getShadowDown().setY(bunny.getShadowDown().getY()-1);

        if((bunny.getImage().getY() < bunny.getShadowDown().getY()) && !bunny.isJumping() & !bunny.isFalling()){
            bunny.setFalling(true);
            Timeline tl = new Timeline();
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(200), new KeyValue(bunny.getImage().yProperty(), bunny.getShadowDown().getY())));
            tl.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    bunny.setFalling(false);
                }
            });
            tl.play();
        }
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
