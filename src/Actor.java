import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Actor implements Collidable{
    private double currentRunningStatePos = 0.0;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private ArrayList<Image> defaultStates = new ArrayList<>();
    private Shadow castUp;
    private Shadow castDown;
    private ImageView playerView;
    private Image currentState;
    public Action currentAction = Action.NONE;
    private GameMap gameMap;
    public Direction lastDirection = Direction.RIGHT;


    public Actor(int posX, int posY, GameMap gameMap){
        fillStateLists();
        currentState = runningRight.get(0);
        playerView = new ImageView(currentState);
        playerView.setX(posX);
        playerView.setY(posY);
        if(currentState == null){
            System.out.println("Brak obrazka");
        }
        this.castDown = new Shadow(this, Color.DEEPPINK);
        this.castUp = new Shadow(this, Color.DEEPPINK);
        this.gameMap = gameMap;
    }

    public Rectangle getCastUp(){
        return castUp.getCast();
    }

    public void reset(){
        currentAction = Action.NONE;
        playerView.setX(20);
        playerView.setY(20);
    }

    public void run(Direction direction, double pxPerStep){
        double newX = playerView.getX();
        switch(direction){
            case LEFT:
                newX -= pxPerStep;
                break;
            case RIGHT:
                newX += pxPerStep;
                break;
            case NONE:
                break;
        }
        if(newX > 0 && newX < gameMap.getWidth()-this.getWidth()) {
            playerView.setX(newX);
        }

    }

    public void jump(){
        double maxy = calculateMaxY(100);
        currentAction = Action.JUMPING;
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(playerView.getY()-4 >= maxy) {
                    playerView.setY(playerView.getY() - 4);
                    run(lastDirection, 1);
                }else{
                    this.stop();
                    currentAction = Action.FALLING;
                    fall(calculateMinY(lastDirection, 1, 4), lastDirection);
                }
            }


        }.start();
    }

    public void fall(double destination, Direction direction){
        currentAction = Action.FALLING;
        System.out.println(destination);
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(playerView.getY()+4 < destination) {
                    playerView.setY(playerView.getY() +4);
                    if(direction != Direction.NONE){
                        run(direction, 1);
                    }
                }else{
                    playerView.setY(destination);
                    this.stop();
                    currentAction = Action.NONE;
                }
            }


        }.start();
    }

    public void gravityCheck(){
        double minY = calculateMinY(Direction.NONE, 1, 4);
        if(minY > getY() && (currentAction == Action.NONE || currentAction == Action.RUNNING_RIGHT || currentAction == Action.RUNNING_LEFT)){
            //System.out.println("minY: " + minY + " > getY " + getY());
            fall(minY, Direction.NONE);
        }
    }

    private double calculateMinY(Direction direction, double pxPerStepX, double pxPerStepY){
        castDown.updateCoords(this.getImage().getX(), this.getImage().getY());
        while(gameMap.collisionWithBlocks(castDown) == null && castDown.getY() < 402){
            castDown.move(Direction.DOWN, pxPerStepY);
            if(direction != Direction.NONE){
                castDown.move(direction, pxPerStepX);
            }
        }
        castDown.move(Direction.UP, 4);
        while(gameMap.collisionWithBlocks(castDown) == null && castDown.getY() < 402){
            castDown.move(Direction.DOWN, 1);
        }
        castDown.move(Direction.UP, 1);
        return castDown.getY();
    }


    private double calculateMaxY(double jumpheight){
        double starty = getY();
        castUp.updateCoords(getImage().getX() ,getImage().getY());
        while(gameMap.collisionWithBlocks(castUp) == null && castUp.getY() > starty - jumpheight  && castUp.getY() > 5){
            castUp.move(Direction.UP, 4);
            castUp.move(lastDirection, 1);
        }
        castUp.move(Direction.DOWN, 1);
        return castUp.getY();
    }


    public Bounds getBounds() {
        return playerView.getBoundsInLocal();
    }

    public void displayShadows(){
        if(currentAction == Action.NONE){
            calculateMinY(Direction.NONE, 1, 4);
            calculateMaxY(100);
        }
    }


    public double getX(){
        return playerView.getX();
    }
    public double getY(){
        return playerView.getY();
    }

    public double getWidth(){
        return currentState.getWidth();
    }

    public double getHeight(){
        return currentState.getHeight();
    }


    public ImageView getImage(){
        playerView.setImage(currentState);
        return playerView;
    }


    public void changeStatePicture(Direction dir){
        if(dir == Direction.LEFT){
            currentRunningStatePos = (currentRunningStatePos + 0.1)%runningLeft.size();
            currentState = runningLeft.get((int)currentRunningStatePos);
        }else if(dir == Direction.RIGHT){
            currentRunningStatePos = (currentRunningStatePos + 0.1)%runningRight.size();
            currentState = runningRight.get((int)currentRunningStatePos);

        }
        playerView.setImage(currentState);

    }

    private void fillStateLists(){
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/default_bunny_right.png")));
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/default_bunny_left.png")));
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft3.png")));
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright3.png")));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft1.png")));
        //runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft3.png")));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft4.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright1.png")));
        //runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright3.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright4.png")));
    }
}
