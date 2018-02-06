package game;

import gui.GameScene;
import gui.ScoreInfo;

import map.Collidable;
import map.GameMap;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class GameLoop extends AnimationTimer {
    private LinkedList<Command> commands = new LinkedList<>();
    private LinkedList<Command> executed = new LinkedList<>();
    private Actor bunny;
    private GameMap gameMap;
    private GameScene gameScene;
    private ScoreInfo scoreInfo;
    private Cast castDown;
    private Cast castUp;

    public GameLoop(GameScene gameScene, Actor bunny, GameMap gameMap, ScoreInfo scoreInfo){
        listen(gameScene);
        this.bunny = bunny;
        this.gameScene = gameScene;
        this.gameMap = gameMap;
        this.scoreInfo = scoreInfo;
        this.castDown = new Cast(this.bunny, Color.DEEPPINK, gameMap);
        this.castUp = new Cast(this.bunny, Color.DEEPPINK, gameMap);

        gameScene.displayMessage("Controls:\nRunning ⇦, ⇨\nJumping ⇧\nHelp H\nReturn to menu BACKSPACE\n\nYour goal is TO FIND A CARROT!", 16, 10);

    }

    Cast getCastDown() {
        return castDown;
    }

    Cast getCastUp(){
        return castUp;
    }

    @Override
    public void handle(long now) {
        if(gameMap.collidedWithGoal(bunny)) {
            gameMap.recolor(Color.LIGHTBLUE, Color.LIGHTGREEN, Color.DARKSEAGREEN);
            gameScene.displayWinLoseMessage("Bunny is happy", 90);
            this.stop();
        }else if(gameMap.actorOutOfMap(bunny)) {
            gameMap.recolor(Color.LIGHTGREY, Color.GREY, Color.DARKGREY);
            gameScene.displayWinLoseMessage("YOU DIED", 90);
            this.stop();
        }else{
            gameScene.updateScrollPosition();
            collectItems();
            statesCheck();
            gravityCheck(bunny);

            for (Command command : commands) {
                if (bunny.getCurrentAction() != Action.FALLING) {
                    command.execute();
                    gravityCheck(bunny);
                    detectCollisions();
                    executed.add(command);
                    commands.remove(command);
                }
            }
            displayShadows();

            if(executed.size() > 100){
                executed.subList(0, 90).clear();
            }
        }
    }

    private void gravityCheck(Actor actor){
        if(!actor.getCurrentAction().equals(Action.NEW_FALL) && !actor.getCurrentAction().equals(Action.FALLING)) {
            double minY = castDown.calculateMinY(Direction.NONE, 4);
            if(minY > actor.getY() && (actor.getCurrentAction() == Action.NONE || actor.getCurrentAction() == Action.RUNNING_RIGHT || actor.getCurrentAction() == Action.RUNNING_LEFT)){
                bunny.setCurrentAction(Action.NEW_FALL);
            }
        }

    }

    private void statesCheck(){
        if(bunny.getCurrentAction() == Action.RUNNING_RIGHT){
            bunny.increaseMomentum();
            commands.add(new MoveRight(bunny));
        }
        if(bunny.getCurrentAction() == Action.RUNNING_LEFT){
            bunny.increaseMomentum();
            commands.add(new MoveLeft(bunny));
        }
        if(bunny.getCurrentAction() == Action.NEW_JUMP){
            commands.add(new Jump(bunny, this));
        }
        if(bunny.getCurrentAction() == Action.NEW_FALL){
            commands.add(new Fall(bunny, this));
        }
    }

    private void detectCollisions(){
        if(gameMap.collisionWithBlocks(bunny) != null){
            if(!executed.isEmpty()) {
                executed.getLast().undo();
                executed.removeLast();
            }else{
                gameScene.displayMessage("Something went wrong", 18, 3);
                bunny.reset();
                gameScene.resetScrollPosition();
            }
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void collectItems(){
        Collidable collectable = gameMap.collisionWithCollectable(bunny);
        if(collectable != null){
            gameMap.getCollectables().remove(collectable);
            gameScene.getGameGroup().getChildren().remove(collectable);
            scoreInfo.increment();
        }
    }

    private void displayShadows(){
        if(bunny.getCurrentAction() == Action.NONE){
            castDown.calculateMinY(Direction.NONE, 1*bunny.getMomentum());
            castUp.calculateMaxY();
        }
    }


    private void listen(Scene scene){
        scene.setOnKeyPressed(
                event -> {
                    String eventCode = event.getCode().toString();
                    switch(eventCode){
                        case "RIGHT":
                            if(bunny.getCurrentAction() == Action.NONE){
                                bunny.setCurrentAction(Action.RUNNING_RIGHT);

                            }
                            break;
                        case "LEFT":
                            if(bunny.getCurrentAction() == Action.NONE){
                                bunny.setCurrentAction(Action.RUNNING_LEFT);
                            }
                            break;
                        case "UP":
                            if(bunny.getCurrentAction() == Action.NONE || bunny.getCurrentAction() == Action.RUNNING_RIGHT || bunny.getCurrentAction() == Action.RUNNING_LEFT){
                                bunny.setCurrentAction(Action.NEW_JUMP);
                            }
                            break;

                        case "N":
                            bunny.reset();
                            gameScene.resetScrollPosition();
                            break;
                        case "BACK_SPACE":
                            gameScene.backToMenu();
                            break;
                        case "H":
                            gameScene.displayMessage("Controls:\nRunning ⇦, ⇨ (arrows left, right) \nJumping ⇧ (arrow up)\nHelp H\nReturn to menu BACKSPACE\n\nYour goal is TO FIND A CARROT!", 16, 10);
                    }
                }
        );
        scene.setOnKeyReleased(event -> {
            if(bunny.getCurrentAction() == Action.RUNNING_RIGHT || bunny.getCurrentAction() == Action.RUNNING_LEFT) {
                bunny.setCurrentAction(Action.NONE);
            }
        });
    }
}
