import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class GameLoop extends AnimationTimer {
    ArrayList<String> keyboardInput = new ArrayList<>();
    Player bunny;
    double sceneWidth;
    double sceneHeight;
    GameMap gm;

    GameLoop(Scene scene, Player bunny, GameMap gm){
        listen(scene);
        this.bunny = bunny;
        this.sceneWidth = scene.getWidth();
        this.sceneHeight = scene.getHeight();
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
            gm.redraw();
            if (bunny.looksRight() && bunny.isStanding()) {
                bunny.setJumpingStateRight();
                jump();
                gm.render(bunny);
            } else if(bunny.looksLeft() && bunny.isStanding()){
                bunny.setJumpingStateLeft();
                jump();
                gm.render(bunny);
            }
            gm.render(bunny);
        }
        if(keyboardInput.isEmpty()) {
            gm.redraw();
            if (bunny.looksRight()) {
                bunny.setDefaultStateRight();
                gm.render(bunny);
            } else {
                bunny.setDefaultStateLeft();
                gm.render(bunny);
            }
        }

    }

    private void run(Direction dir){
        gm.redraw();
        if(!gm.detectCollision(bunny)) {
            bunny.move(dir);
        }else{
            bunny.throwback(dir);
        }
        gm.render(bunny);
    }

    private void jump(){
        for(int i=0; i<50; i++){
            gm.redraw();
            if(gm.detectCollision(bunny)){
                bunny.throwback(Direction.UP);
                break;
            }
                bunny.jump();
                //bunny.moveLeft();

            gm.render(bunny);
        }
    }

    private void fall(){
        while(!gm.wouldBeCollision(bunny)){
            bunny.fall();
            gm.render(bunny);
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
