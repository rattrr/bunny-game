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

public class Player implements Collidable{
    private double currentRunningStatePos = 0.0;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private ArrayList<Image> defaultStates = new ArrayList<>();
    private Shadow shadowLeftRight;
    private Shadow shadowDown;
    private Shadow shadowUp;
    private boolean jumping = false;
    private boolean falling = false;
    private ImageView playerView;
    private Image currentState;
    private Timeline jumpingTimeline;


    public Player(int posX, int posY){
        fillStateLists();
        currentState = runningRight.get(0);
        playerView = new ImageView(currentState);
        playerView.setX(posX);
        playerView.setY(posY);
        shadowLeftRight = new Shadow(playerView.getX(), playerView.getY(), currentState.getWidth(), currentState.getHeight(), Color.DEEPPINK);
        shadowDown = new Shadow(playerView.getX(), playerView.getY(), currentState.getWidth(), currentState.getHeight(), Color.ORANGERED);
        shadowUp = new Shadow(playerView.getX(), playerView.getY(), currentState.getWidth(), currentState.getHeight(), Color.BLUE);
        if(currentState == null){
            System.out.println("Brak obrazka");
        }
    }

    public Timeline makeJumpingTimeline(double jumpHeight){
        double y = jumpHeight;
        Timeline tl = new Timeline();
        tl.setCycleCount(1);
        KeyValue kv = new KeyValue(playerView.yProperty(), y);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        tl.getKeyFrames().add(kf);

        tl.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jumping = false;
            }
        });

        return tl;
    }

    public void setTimeLine(Timeline tl){
        this.jumpingTimeline = tl;
    }

    public Timeline getTimeline(){
        return this.jumpingTimeline;
    }

    public Bounds getBounds() {
        return playerView.getBoundsInLocal();
    }

    public void setFalling(boolean value){
        falling = value;
    }

    public void setJumping(boolean value){
        jumping = value;
    }

    public boolean isFalling(){
        return falling;
    }

    public boolean isJumping(){
        return jumping;
    }

    public boolean isOverShadow(){ return getImage().getY() < shadowDown.getY();}

    public Shadow getShadowLeftRight(){
        return shadowLeftRight;
    }

    public Shadow getShadowDown(){
        return shadowDown;
    }

    public Shadow getShadowUp() { return shadowUp; }


    public Image getState(){
        return currentState;
    }

    public double getY(){
        return playerView.getY();
    }

    public ImageView getImage(){
        playerView.setImage(currentState);
        return playerView;
    }


    public void move(Direction dir){
        if(dir == Direction.LEFT){
            currentRunningStatePos = (currentRunningStatePos + 0.2)%runningLeft.size();
            currentState = runningLeft.get((int)currentRunningStatePos);
        }else if(dir == Direction.RIGHT){
            currentRunningStatePos = (currentRunningStatePos +0.2)%runningRight.size();
            currentState = runningRight.get((int)currentRunningStatePos);
            playerView.setImage(currentState);
        }
        updateShadows();
    }

    public void moveTo(Direction dir, double pos){
        switch(dir){
            case UP:
            case DOWN:
                playerView.setY(pos);
                break;
            case LEFT:
            case RIGHT:
                playerView.setX(pos);
                break;
        }
    }

    public void updateShadows(){
        shadowLeftRight.updateSize(currentState.getWidth(), currentState.getHeight());
        shadowDown.updateSize(currentState.getWidth(), currentState.getHeight());
        shadowUp.updateSize(currentState.getWidth(), currentState.getHeight());
    }

    public void updateShadowsCoords(){
        shadowDown.updateCoords(playerView.getX(), playerView.getY());
        shadowLeftRight.updateCoords(playerView.getX(), playerView.getY());
        shadowUp.updateCoords(playerView.getX(), playerView.getY());
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
