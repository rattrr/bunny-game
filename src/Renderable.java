import javafx.scene.image.Image;

public interface Renderable {
    int getPosX();
    int getPosY();
    Image getCurrentState();
    javafx.geometry.Rectangle2D getBoundary();
}
