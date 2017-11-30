import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Player {
    private double currentRunningStatePos = 0.0;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private ArrayList<Image> defaultStates = new ArrayList<>();
    private Rectangle shadowLeftRight;
    private Rectangle shadowDown;
    private boolean jumping = false;
    private boolean falling = false;
    private ImageView playerView;
    private Image currentState;
    private Timeline jumpingTimeline;
    private String looking = "RIGHT";


    public Player(int posX, int posY){
        fillStateLists();
        currentState = runningRight.get(0);
        playerView = new ImageView(currentState);
        playerView.setX(posX);
        playerView.setY(posY);
        shadowLeftRight = new Rectangle(playerView.getX(), playerView.getY(), currentState.getWidth(), currentState.getHeight());
        shadowLeftRight.setStroke(Color.DEEPPINK);
        shadowLeftRight.setFill(Color.TRANSPARENT);
        shadowDown = new Rectangle(playerView.getX(), playerView.getY(), currentState.getWidth(), currentState.getHeight());
        shadowDown.setStroke(Color.DEEPPINK);
        shadowDown.setFill(Color.TRANSPARENT);
        if(currentState == null){
            System.out.println("Brak obrazka");
        }
    }

    public Timeline makeJumpingTimeline(double jumpHeight){
        double y = playerView.getY() - jumpHeight;
        Timeline tl = new Timeline();
        tl.setCycleCount(1);
        KeyValue kv = new KeyValue(playerView.yProperty(), y);
        KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
        tl.getKeyFrames().add(kf);

        tl.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jumping = false;
            }
        });

        return tl;
    }

    public boolean isJumpingTimelineSet(){
        return jumpingTimeline == null;
    }

    public void setTimeLine(Timeline tl){
        this.jumpingTimeline = tl;
    }

    public Timeline getTimeline(){
        return this.jumpingTimeline;
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

    public Rectangle getShadowLeftRight(){
        return shadowLeftRight;
    }

    public Rectangle getShadowDown(){
        return shadowDown;
    }

    public Image getState(){
        return currentState;
    }

    public ImageView getImage(){
        playerView.setImage(currentState);
        return playerView;
    }


    public void move(Direction dir){
        if(dir == Direction.LEFT){
            looking = "LEFT";
            currentRunningStatePos = (currentRunningStatePos + 0.2)%runningLeft.size();
            currentState = runningLeft.get((int)currentRunningStatePos);
        }else if(dir == Direction.RIGHT){
            looking = "RIGHT";
            currentRunningStatePos = (currentRunningStatePos +0.2)%runningRight.size();
            currentState = runningRight.get((int)currentRunningStatePos);
            playerView.setImage(currentState);
        }
        updateShadows();
    }

    private void updateShadows(){
        shadowLeftRight.setWidth(currentState.getWidth());
        shadowLeftRight.setHeight(currentState.getHeight());
        shadowDown.setWidth(currentState.getWidth());
        shadowDown.setHeight(currentState.getHeight());
    }


    public Boolean isStanding(){
        return currentState == defaultStates.get(0) || currentState == defaultStates.get(1);
    }


    public Boolean looksRight(){
        return looking.equals("RIGHT");
    }

    public Boolean looksLeft(){
        return looking.equals("LEFT");
    }

    public void setJumpingStateLeft(){
        currentState = defaultStates.get(2);
    }

    public void setJumpingStateRight(){
        currentState = defaultStates.get(3);
    }

    public void setDefaultStateRight(){
        currentState = defaultStates.get(0);
    }

    public void setDefaultStateLeft(){
        currentState = defaultStates.get(1);
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
