package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

class Sprites {
    private ImageView actorView;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private Image defaultLeft;
    private Image defaultRight;
    private double currentRunningStatePos = 0.0;


    Sprites(ImageView actorView){
        this.actorView = actorView;
        initializeGraphics();
        actorView.setImage(defaultRight);
    }

    void setNextLeft(){
        currentRunningStatePos = (currentRunningStatePos + 0.2)%runningLeft.size();
        actorView.setImage(runningLeft.get((int)currentRunningStatePos));
    }

    void setNextRight(){
        currentRunningStatePos = (currentRunningStatePos + 0.2)%runningRight.size();
        actorView.setImage(runningRight.get((int)currentRunningStatePos));
    }

    void setDefaultLeft(){
        actorView.setImage(defaultLeft);
    }

    void setDefaultRight(){
        actorView.setImage(defaultRight);
    }

    void setJumpingLeft(){
        actorView.setImage(runningLeft.get(1));
    }

    void setJumpingRight(){
        actorView.setImage(runningRight.get(1));
    }

    private void initializeGraphics(){
        defaultRight = new Image(this.getClass().getClassLoader().getResourceAsStream("img/default_bunny_right.png"));
        defaultLeft = new Image(this.getClass().getClassLoader().getResourceAsStream("img/default_bunny_left.png"));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft1.png")));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft3.png")));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft4.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright1.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright3.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright4.png")));
    }
}
