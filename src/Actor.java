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
    public double maxJump = 0;
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

    public Rectangle getCastDown(){
        return castDown.getCast();
    }

    public void move(Direction direction, double distance){
        double newX = playerView.getX();
        switch(direction){
            case LEFT:
                newX -= distance;
                break;
            case RIGHT:
                newX += distance;
                break;
            case NONE:
                break;
        }
        if(newX > 0 && newX < 1950) {
            playerView.setX(newX);
            //System.out.println(newX);
        }
    }
    public void reset(){
        playerView.setX(1);
        playerView.setY(1);
    }

    public void jump(){
        System.out.println("SKOK");
        calculateMaxY(120);
        currentAction = Action.JUMPING;
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                System.out.println(playerView.getY() + " " + maxJump);
                if(playerView.getY()-4 > maxJump) {
                    playerView.setY(playerView.getY() - 4);
                    move(lastDirection, 1);
                }else{
                    this.stop();
                    currentAction = Action.NONE;
                }
            }


        }.start();
    }

    public void fall(double destination){
        System.out.println("spada!");
        currentAction = Action.FALLING;
        new AnimationTimer(){
            @Override
            public void handle(long now) {
               // System.out.println(now);
                if(playerView.getY()+10 <= destination) {
                    playerView.setY(playerView.getY() +10);
                }else{
                    playerView.setY(destination);
                    this.stop();
                    //System.out.println("nie skacze");
                    currentAction = Action.NONE;
                }
            }


        }.start();
    }

    public void gravityCheck(){
        double minY = calculateMinY();
        if(minY > getY() && currentAction != Action.JUMPING && currentAction != Action.FALLING){
           // System.out.println("is falling");
            //currentAction = Action.FALLING;
            fall(minY);
            //currentAction = Action.NONE;
        }
    }

    private double calculateMinY(){
        castDown.updateCoords(this.getImage().getX(), this.getImage().getY());
        while(gameMap.collision(castDown, gameMap.getBlocks()) == null && castDown.getY() < 385){
            castDown.move(Direction.DOWN, 1);
        }
        return castDown.getY()-1;
    }

    public void calculateMaxY(double jumpheight){
        double starty = getY();
        castUp.updateCoords(getImage().getX() ,getImage().getY());
        while(gameMap.collision(castUp, gameMap.getBlocks()) == null && castUp.getY() > starty - jumpheight  && castUp.getY() > 0){
            castUp.move(Direction.UP, 4);
            castUp.move(lastDirection, 1);
        }
        maxJump = castUp.getY()+1;
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
            currentRunningStatePos = (currentRunningStatePos + 0.2)%runningLeft.size();
            currentState = runningLeft.get((int)currentRunningStatePos);
        }else if(dir == Direction.RIGHT){
            currentRunningStatePos = (currentRunningStatePos +0.2)%runningRight.size();
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
