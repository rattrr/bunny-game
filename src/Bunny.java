import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bunny {
    private double currentRunningStatePos = 0.0;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private ArrayList<Image> defaultStates = new ArrayList<>();
    private Image currentState;
    private String looking = "RIGHT";

    private int posX;
    private int posY;

    public Bunny(int posX, int posY){
        fillStateLists();
        this.posX = posX;
        this.posY = posY;
        currentState = defaultStates.get(0);
    }

    public void render(GraphicsContext gc){
        gc.drawImage(currentState, posX, posY);
    }


    public void moveLeft(){
        posX -= 1;
        looking = "LEFT";
        currentRunningStatePos = (currentRunningStatePos + 0.2)%runningLeft.size();
        currentState = runningLeft.get((int)currentRunningStatePos);
    }

    public void moveRight(){
        posX += 1;
        looking = "RIGHT";
        currentRunningStatePos = (currentRunningStatePos +0.2)%runningRight.size();
        currentState = runningRight.get((int)currentRunningStatePos);
    }

    public Boolean looksRight(){
        return looking.equals("RIGHT");
    }

    public Boolean looksLeft(){
        return looking.equals("LEFT");
    }

    public void setStandingState(){
        currentState = defaultStates.get(2);
    }

    public void setDefaultStateRight(){
        currentState = defaultStates.get(0);
    }

    public void setDefaultStateLeft(){
        currentState = defaultStates.get(1);
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
        defaultStates.add(new Image("file:img/bunny_stand.png"));
        runningLeft.add(new Image("file:img/bunny_runleft1.png"));
        runningLeft.add(new Image("file:img/bunny_runleft3.png"));
        runningLeft.add(new Image("file:img/bunny_runleft4.png"));
        runningRight.add(new Image("file:img/bunny_runright1.png"));
        runningRight.add(new Image("file:img/bunny_runright3.png"));
        runningRight.add(new Image("file:img/bunny_runright4.png"));
    }
}
