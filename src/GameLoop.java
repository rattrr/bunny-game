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
        stopAnimationIfCollision();

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

    private void stopAnimationIfCollision(){
        ImageView collided_object = collision(bunny, gm.getBlocks());
        if(collided_object != null){
            if(bunny.getTimeline() != null){
                bunny.getTimeline().stop();
                if(bunny.isJumping()) {
                    bunny.moveTo(Direction.DOWN,collided_object.getY() + 51);
                    bunny.setJumping(false);
                    fall();
                }
            }
        }
    }

    private void run(Direction dir){
        int speed = 3;
        if(dir.equals(Direction.LEFT)){
            bunny.getShadowLeftRight().move(Direction.LEFT, speed);
            if((collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null) ){
                gm.moveBlocks(0.5);
                bunny.move(dir);
                bunny.getImage().setX(bunny.getImage().getX() - speed);
            }
        }else if(dir.equals(Direction.RIGHT)){
            bunny.getShadowLeftRight().move(Direction.RIGHT, speed);
            if((collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null) ){
                gm.moveBlocks(-0.5);
                bunny.move(dir);
                bunny.getImage().setX(bunny.getImage().getX() + speed);
            }
        }
    }

    private ImageView collision(Collidable player, ArrayList<ImageView> objects){
        for (ImageView obj : objects){
            if(player.getBounds().intersects(obj.getBoundsInLocal())){
                return obj;
            }
        }
        return null;
    }

    private void fall(){
        bunny.updateShadowsCoords();
        while (collision(bunny.getShadowDown(), gm.getBlocks()) == null){
            bunny.getShadowDown().move(Direction.DOWN, 1);
        }
        bunny.getShadowDown().move(Direction.UP, 1);

        if(bunny.isOverShadow() && !bunny.isJumping() & !bunny.isFalling()){
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
