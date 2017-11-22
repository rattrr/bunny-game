import javafx.scene.canvas.GraphicsContext;

public class GameMap {
    private double sceneWidth;
    private double sceneHeight;
    private GraphicsContext gc;

    public GameMap(GraphicsContext gc, double sceneWidth, double sceneHeight){
        this.gc = gc;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    public void redraw(){
        gc.clearRect(0,0,sceneWidth, sceneHeight);
        //renderFloor();
    }

    public void render(Renderable object){
        gc.drawImage(object.getCurrentState(), object.getPosX(), object.getPosY());
    }

    private void renderFloor() {
        for (int i = 0; i < 1000; i += 45) {
            gc.drawImage(new Block(0 + i, 355).getCurrentState(), 0 + i, 355);
            gc.strokeRect(0+i, 355, 45, 45);
        }
    }
}
