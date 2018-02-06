package game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import map.Collidable;
import map.GameMap;


public class Actor implements Collidable {
    private ImageView playerView = new ImageView();
    private Sprites sprites = new Sprites(this.playerView);
    private double jumpHeight;
    private int commandsCounter = 0;
    private double momentum = 1;
    private Action currentAction = Action.NONE;
    private GameMap gameMap;
    private Direction lastDirection = Direction.RIGHT;
    private boolean jumpLeft = false;

    public Actor(int posX, int posY, double jumpHeight, GameMap gameMap){
        playerView.setX(posX);
        playerView.setY(posY);
        this.jumpHeight = jumpHeight;
        this.gameMap = gameMap;
    }

    void reset(){
        currentAction = Action.NONE;
        lastDirection = Direction.RIGHT;
        jumpLeft = false;
        playerView.setX(20);
        playerView.setY(20);
    }

    void run(Direction direction){
        double newX = playerView.getX();
        switch(direction){
            case LEFT:
                newX -= momentum;
                break;
            case RIGHT:
                jumpLeft = false;
                newX += momentum;
                break;
            case NONE:
                break;
        }
        if(newX > 0 && newX < gameMap.getWidth() - this.getWidth()) {
            playerView.setX(newX);
        }

    }

    void jump(double maxJumpY){
        currentAction = Action.JUMPING;
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(playerView.getY()-4 >= maxJumpY) {
                    playerView.setY(playerView.getY() - 4);
                    run(lastDirection);
                }else{
                    this.stop();
                    currentAction = Action.NEW_FALL;
                }
            }


        }.start();
    }

    void fall(double destination, Direction direction){
        setCurrentAction(Action.FALLING);
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(playerView.getY()+4 < destination) {
                    playerView.setY(playerView.getY() +4);
                    if(!direction.equals(Direction.NONE)){
                        run(direction);
                    }
                }else{
                    playerView.setY(destination);
                    this.stop();
                    if(direction.equals(Direction.LEFT)){
                        jumpLeft = true;
                    }
                    lastDirection = Direction.NONE;
                    setCurrentAction(Action.NONE);
                }
            }


        }.start();
        changeStatePicture(lastDirection);
    }

    void setCurrentAction(Action action){
        if(action.equals(Action.NONE)){
            setDefaultPose(lastDirection);
            commandsCounter = 0;
            momentum = 1;
        }else if(action.equals(Action.NEW_JUMP)){
            setJumpingPose(lastDirection);
        }else if(action.equals(Action.NEW_FALL) || action.equals(Action.FALLING)){
            if(jumpLeft){
                sprites.setNextLeft();
            }else {
                sprites.setNextRight();
            }
        }
        currentAction = action;
    }

    void changeStatePicture(Direction dir){
        if(dir == Direction.LEFT){
            sprites.setNextLeft();
        }else if(dir == Direction.RIGHT){
            sprites.setNextRight();
        }

    }

    public Bounds getBounds() {
        return playerView.getBoundsInLocal();
    }

    public double getX(){
        return playerView.getX();
    }

    public double getY(){
        return playerView.getY();
    }

    public double getWidth(){
        return playerView.getImage().getWidth();
    }

    double getHeight(){
        return playerView.getImage().getHeight();
    }

    double getJumpHeight(){
        return jumpHeight;
    }

    double getMomentum(){
        return momentum;
    }

    public ImageView getImage(){
        return playerView;
    }

    Direction getLastDirection(){
        return lastDirection;
    }

    void setLastDirection(Direction direction){
        this.lastDirection = direction;
    }

    private void setDefaultPose(Direction dir){
        if(dir == Direction.LEFT || jumpLeft){
            sprites.setDefaultLeft();
        }else{
            sprites.setDefaultRight();
        }
    }

    private void setJumpingPose(Direction dir){
        if(dir == Direction.LEFT || jumpLeft){
            sprites.setJumpingLeft();
        }else{
            sprites.setJumpingRight();
        }
    }

    void increaseMomentum(){
            commandsCounter++;
            if (commandsCounter == 20) {
                commandsCounter = 0;
                momentum++;
            }
    }

    Action getCurrentAction() {
        return currentAction;
    }
}
