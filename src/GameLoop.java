import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameLoop extends AnimationTimer {
    ArrayList<String> keyboardInput = new ArrayList<>();
    Player bunny;
    GameMap gm;
    GameScene scene;
    ScoreInfo si;

    GameLoop(GameScene scene, Player bunny, GameMap gm, ScoreInfo si){
        listen(scene);
        this.bunny = bunny;
        this.scene = scene;
        this.gm = gm;
        this.si = si;
    }


    @Override
    public void handle(long now) {
        fall();
        //stopAnimationIfCollision();
        collectItems();
        checkJump(120);

        if(keyboardInput.contains("LEFT")){
            run(Direction.LEFT);
        }
        if(keyboardInput.contains("RIGHT")){
            run(Direction.RIGHT);
        }
        if(keyboardInput.contains("UP")){
            if(!bunny.isJumping() && !bunny.isFalling()){
                bunny.setJumping(true);
                bunny.setTimeLine(bunny.makeJumpingTimeline(checkJump(120)));
                bunny.getTimeline().play();
            }
        }
        if(keyboardInput.isEmpty()) {


    }}

    private double checkJump(double jumpHeight){
        bunny.updateShadowsCoords();
        double targetY = bunny.getY() - jumpHeight;
        while(bunny.getShadowUp().getY() > targetY && collision(bunny.getShadowUp(), gm.getBlocks()) == null){
            bunny.getShadowUp().move(Direction.UP, 1);
        }
        return bunny.getShadowUp().getY()+1;
    }

    private void collectItems(){
        Collidable collectable = collision(bunny, gm.getItems());
        if(collectable != null){
            gm.getItems().remove(collectable);
            scene.getGameGroup().getChildren().remove(collectable);
            si.increment();
        }
    }

    private void stopAnimationIfCollision(){
        Collidable collided_object = collision(bunny.getShadowLeftRight(), gm.getBlocks());
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
        int speed = 2;
        if(dir.equals(Direction.LEFT)){
            bunny.getShadowLeftRight().move(Direction.LEFT, speed);
            if((collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null) ){
                gm.moveBlocks(0.5);
                bunny.move(dir);
                bunny.getImage().setX(bunny.getImage().getX() - speed-1);
            }
        }else if(dir.equals(Direction.RIGHT)){
            bunny.getShadowLeftRight().move(Direction.RIGHT, speed);
            if((collision(bunny.getShadowLeftRight(), gm.getBlocks()) == null) ){
                gm.moveBlocks(-0.5);
                bunny.move(dir);
                bunny.getImage().setX(bunny.getImage().getX() + speed+1);
            }
        }
    }

    private Collidable collision(Collidable player, ArrayList<? extends Collidable> objects){
        for (Collidable obj : objects){
            if(player.getBounds().intersects(obj.getBounds())){
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
