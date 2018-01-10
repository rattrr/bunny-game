import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Actor implements Collidable{
    private double currentRunningStatePos = 0.0;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private ArrayList<Image> defaultStates = new ArrayList<>();
    private ImageView playerView;
    private Image currentState;
    public boolean isJumping = false;
    public double maxJump = 0;


    public Actor(int posX, int posY){
        fillStateLists();
        currentState = runningRight.get(0);
        playerView = new ImageView(currentState);
        playerView.setX(posX);
        playerView.setY(posY);
        if(currentState == null){
            System.out.println("Brak obrazka");
        }
    }

    public void move(Direction direction, double distance){
        switch(direction){
            case LEFT:
                playerView.setX(playerView.getX()-distance);
                break;
            case RIGHT:
                playerView.setX(playerView.getX()+distance);
                break;
            case NONE:
                break;
        }
    }

    public void jump(Direction direction){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                System.out.println(playerView.getY() + " " + maxJump);
                if(playerView.getY()-3 > maxJump) {
                    playerView.setY(playerView.getY() - 4);
                    move(direction, 1);
                }else{
                    this.stop();
                    isJumping = false;
                }
            }


        }.start();
    }

    public void fall(double destination){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
               // System.out.println(now);
                if(playerView.getY()+2 < destination) {
                    playerView.setY(playerView.getY() +2);
                }else{
                    this.stop();
                }
            }


        }.start();
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
