import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameLoop extends AnimationTimer {
    LinkedList<Command> commands = new LinkedList<>();
    LinkedList<Command> executed = new LinkedList<>();
    Actor bunny;
    GameMap gm;
    GameScene gameScene;
    ScoreInfo scoreInfo;
    long lastUpdate = 0;

    GameLoop(GameScene gameScene, Actor bunny, GameMap gm, ScoreInfo si){
        listen(gameScene);
        this.bunny = bunny;
        this.gameScene = gameScene;
        this.gm = gm;
        this.scoreInfo = si;

    }

    @Override
    public void handle(long now) {
            //System.out.println(bunny.currentAction);
            gameScene.getScrollRoot().setHvalue(gameScene.getScrollRoot().getHmax()* (bunny.getX()/2000.0));
            bunny.gravityCheck();
            collectItems();
            detectCollisions();

            statesCheck();

            for (Command command : commands) {
                if(bunny.currentAction != Action.FALLING) {
                    command.execute();
                    executed.add(command);
                    commands.remove(command);
                }

                //System.out.println("Last executed command: " + executed.getLast().getClass());

            }

    }

    public void statesCheck(){
        if(bunny.currentAction == Action.RUNNING_RIGHT){
            commands.add(new MoveRight(bunny));
        }
        if(bunny.currentAction == Action.RUNNING_LEFT){
            commands.add(new MoveLeft(bunny));
        }
        if(bunny.currentAction == Action.NEW_JUMP){
            System.out.println("jest ju znwou");
            commands.add(new Jump(bunny));
        }
    }





    private void detectCollisions(){
        if(gm.collision(bunny, gm.getBlocks()) != null){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Collision");
            bunny.gravityCheck();
            if(!executed.isEmpty()) {
                executed.getLast().undo();
                executed.removeLast();
            }else{
                bunny.reset();
                gameScene.getScrollRoot().setHvalue(0);
            }
        }
    }

    private void collectItems(){
        Collidable collectable = gm.collision(bunny, gm.getItems());
        if(collectable != null){
            gm.getItems().remove(collectable);
            gameScene.getGameGroup().getChildren().remove(collectable);
            scoreInfo.increment();
        }
    }




    public static <E> boolean containsInstance(LinkedList<E> list, Class<? extends E> commandClass) {
        return list.stream().anyMatch(e -> commandClass.isInstance(e));
    }


    private void listen(Scene scene){
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent event) {
                        String eventCode = event.getCode().toString();
                        System.out.println("event code " + eventCode + " bunny state " + bunny.currentAction);
                        switch(eventCode){
                            case "RIGHT":
                                if(bunny.currentAction == Action.NONE){
                                    bunny.lastDirection = Direction.RIGHT;
                                    bunny.currentAction = Action.RUNNING_RIGHT;

                                }
                                break;
                            case "LEFT":
                                if(bunny.currentAction == Action.NONE){
                                    bunny.currentAction = Action.RUNNING_LEFT;
                                    bunny.lastDirection = Direction.LEFT;

                                    gameScene.getScrollRoot().setHvalue(gameScene.getScrollRoot().getHvalue()-0.0032);
                                }
                                break;
                            case "UP":
                                if(bunny.currentAction == Action.NONE || bunny.currentAction == Action.RUNNING_RIGHT){
                                    bunny.currentAction = Action.NEW_JUMP;
                                }
                                break;
                            case "DOWN":
                                bunny.lastDirection = Direction.NONE;
                                break;
                            case "N":
                                bunny.lastDirection = Direction.RIGHT;
                                bunny.reset();
                                gameScene.getScrollRoot().setHvalue(0);
                                break;
                            case "BACK_SPACE":
                                gameScene.getStage().initMenu();
                        }
                    }
                }
        );
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String eventCode = event.getCode().toString();
                if(bunny.currentAction == Action.RUNNING_RIGHT || bunny.currentAction == Action.RUNNING_LEFT) {
                    System.out.println("out!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    bunny.currentAction = Action.NONE;
                }
            }
        });
    }
}
