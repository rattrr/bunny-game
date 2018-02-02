import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class GameLoop extends AnimationTimer {
    LinkedList<Command> commands = new LinkedList<>();
    LinkedList<Command> executed = new LinkedList<>();
    Actor bunny;
    GameMap gameMap;
    GameScene gameScene;
    ScoreInfo scoreInfo;

    GameLoop(GameScene gameScene, Actor bunny, GameMap gameMap, ScoreInfo si){
        listen(gameScene);
        this.bunny = bunny;
        this.gameScene = gameScene;
        this.gameMap = gameMap;
        this.scoreInfo = si;

    }

    @Override
    public void handle(long now) {
        if(gameMap.collidedWithGoal(bunny)) {
            gameMap.recolor(Color.LIGHTBLUE, Color.LIGHTGREEN);
            gameScene.displayMessage("U WIN!", 100);
            this.stop();
        }else if(gameMap.actorOutOfMap(bunny)) {
            gameMap.recolor(Color.LIGHTGREY, Color.GREY);
            gameScene.displayMessage("U LOSE!", 100);
            this.stop();
        }else{
            gameScene.updateScrollPosition();
            bunny.gravityCheck();
            collectItems();
            detectCollisions();
            statesCheck();

            for (Command command : commands) {
                if (bunny.currentAction != Action.FALLING) {
                    command.execute();
                    executed.add(command);
                    commands.remove(command);
                }
                //System.out.println("Last executed command: " + executed.getLast().getClass());
            }
            bunny.displayShadows();
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
            commands.add(new Jump(bunny));
        }
    }


    private void detectCollisions(){
        if(gameMap.collisionWithBlocks(bunny) != null){
            System.out.println("kolizjaaaaaaaaaaaaaaaaaaaaaaa");
            bunny.gravityCheck();
            if(!executed.isEmpty()) {
                executed.getLast().undo();
                executed.removeLast();
            }else{
                bunny.reset();
                gameScene.resetScrollPosition();
            }
        }
    }

    private void collectItems(){
        Collidable collectable = gameMap.collisionWithCollectable(bunny);
        if(collectable != null){
            gameMap.getCollectables().remove(collectable);
            gameScene.getGameGroup().getChildren().remove(collectable);
            scoreInfo.increment();
        }
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
                                    System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
                                    bunny.lastDirection = Direction.RIGHT;
                                    bunny.currentAction = Action.RUNNING_RIGHT;

                                }
                                break;
                            case "LEFT":
                                if(bunny.currentAction == Action.NONE){
                                    bunny.currentAction = Action.RUNNING_LEFT;
                                    bunny.lastDirection = Direction.LEFT;

                                }
                                break;
                            case "UP":
                                if(bunny.currentAction == Action.NONE || bunny.currentAction == Action.RUNNING_RIGHT || bunny.currentAction == Action.RUNNING_LEFT){
                                    bunny.currentAction = Action.NEW_JUMP;
                                }
                                break;
                            case "DOWN":
                                bunny.lastDirection = Direction.NONE;
                                break;
                            case "N":
                                bunny.lastDirection = Direction.RIGHT;
                                bunny.reset();
                                gameScene.resetScrollPosition();
                                break;
                            case "BACK_SPACE":
                                gameScene.backToMenu();
                        }
                    }
                }
        );
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(bunny.currentAction == Action.RUNNING_RIGHT || bunny.currentAction == Action.RUNNING_LEFT) {
                    bunny.currentAction = Action.NONE;
                }
            }
        });
    }
}
