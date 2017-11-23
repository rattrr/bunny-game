import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class GameMap {
    private double sceneWidth;
    private double sceneHeight;
    private GraphicsContext gc;
    private Player bunny;
    private ArrayList<Block> blocks = new ArrayList<>();

    public GameMap(GraphicsContext gc, Player bunny, double sceneWidth, double sceneHeight){
        this.gc = gc;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.bunny = bunny;
        blocks.add(new Block(200, 305));
        blocks.add(new Block(380, 305));
        blocks.add(new Block(155, 250));
        makeFloor();
    }

    public void redraw(){
        gc.clearRect(0,0,sceneWidth, sceneHeight);
        renderAllBlocks();

    }

    private void renderAllBlocks(){
        for (Block block: blocks){
            render(block);
        }
    }

    public boolean detectCollision(Player bunny){
        for(Block block: blocks){
            if(bunny.getBoundary().intersects(block.getBoundary())){
                System.out.println("Kolizja!");
                return true;
            }
        }
        return false;

    }

    public boolean wouldBeCollision(Player bunny){
        for(Block block: blocks){
            if(bunny.getBoundary().intersects(block.getBoundary())){
                bunny.jump();
                bunny.jump();
                //System.out.println("Kolizja!");
                return true;
            }
        }
        return false;
    }

    public void render(Renderable object){
        gc.drawImage(object.getCurrentState(), object.getPosX(), object.getPosY());
    }

    private void makeFloor() {
        for (int i = 0; i < 1000; i += 45) {
            Block block = new Block(0 + i, 355);
            blocks.add(block);
        }
    }
}
