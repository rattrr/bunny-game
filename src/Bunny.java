import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bunny {
    private double currentRunningStatePos = 0.0;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private ArrayList<Image> defaultStates = new ArrayList<>();
    private Image currentState;

    private int posX;
    private int posY;

    public Bunny(int posX, int posY){
        fillStateLists();
        this.posX = posX;
        this.posY = posY;
        currentState = defaultStates.get(0);
    }

    public Image moveLeft(){
        posX -= 1;
        return runningLeft.get((int)currentRunningStatePos);
    }

    public Image moveRight(){
        posX += 1;
        return runningRight.get((int)currentRunningStatePos);
    }

    public Image getCurrentState(){
        return currentState;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    private void fillStateLists(){
        defaultStates.add(new Image("file:img/default_bunny_right.png"));
        defaultStates.add(new Image("file:img/default_bunny_left.png"));
        runningLeft.add(new Image("file:img/bunny_runleft1.png"));
        runningLeft.add(new Image("file:img/bunny_runleft3.png"));
        runningLeft.add(new Image("file:img/bunny_runleft4.png"));
        runningRight.add(new Image("file:img/bunny_runright1.png"));
        runningRight.add(new Image("file:img/bunny_runright3.png"));
        runningRight.add(new Image("file:img/bunny_runright4.png"));
    }
}
