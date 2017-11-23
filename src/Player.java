import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player implements Renderable {
    private double currentRunningStatePos = 0.0;
    private ArrayList<Image> runningLeft = new ArrayList<>();
    private ArrayList<Image> runningRight = new ArrayList<>();
    private ArrayList<Image> defaultStates = new ArrayList<>();
    private Image currentState;
    private String looking = "RIGHT";

    private int posX;
    private int posY;

    public Player(int posX, int posY){
        fillStateLists();
        this.posX = posX;
        this.posY = posY;
        currentState = defaultStates.get(0);
        if(currentState == null){
            System.out.println("Brak obrazka");
        }
    }


    public Image getCurrentState(){
        return currentState;
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(posX, posY, currentState.getWidth(), currentState.getHeight());
    };

    public void throwback(Direction dir){
        if(dir == Direction.LEFT) {
            posX += 6;
        } else if(dir ==Direction.RIGHT){
            posX -= 3;
        } else if(dir == Direction.UP){
            posY += 3;
        }
    }

    public void move(Direction dir){
        if(dir == Direction.LEFT){
            posX -= 1;
            looking = "LEFT";
            currentRunningStatePos = (currentRunningStatePos + 0.2)%runningLeft.size();
            currentState = runningLeft.get((int)currentRunningStatePos);
        }else if(dir == Direction.RIGHT){
            posX += 1;
            looking = "RIGHT";
            currentRunningStatePos = (currentRunningStatePos +0.2)%runningRight.size();
            currentState = runningRight.get((int)currentRunningStatePos);
        }
    }

    public void jump(){
        posY = posY - 1;
    }

    public void fall(){
        posY += 1;
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

    public int getPosX() {
        return (int)posX;
    }

    public int getPosY() {
        return (int)posY;
    }

    private void fillStateLists(){
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/default_bunny_right.png")));
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/default_bunny_left.png")));
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft3.png")));
        defaultStates.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright3.png")));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft1.png")));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft3.png")));
        runningLeft.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runleft4.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright1.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright3.png")));
        runningRight.add(new Image(this.getClass().getClassLoader().getResourceAsStream("img/bunny_runright4.png")));
    }
}
