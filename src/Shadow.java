import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shadow implements Collidable {
    Actor actor;
    private Rectangle shape;
    public Shadow(Actor actor, Color color){
        this.actor = actor;
        shape = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        shape.setStroke(color);
        shape.setFill(Color.TRANSPARENT);
    }

    public void updateCoords(double x, double y){
        shape.setX(x);
        shape.setY(y);
    }

    public void updateSize(double width, double height){
        shape.setWidth(width);
        shape.setHeight(height);
    }

    public void move(Direction dir, double px){
        switch(dir){
            case UP:
                shape.setY(shape.getY() - px);
                break;
            case DOWN:
                shape.setY(shape.getY() + px);
                break;
            case LEFT:
                shape.setX(shape.getX() - px);
                break;
            case RIGHT:
                shape.setX(shape.getX() + px);
                break;
        }
    }

    public double getX(){ return shape.getX(); }
    public double getY(){ return shape.getY(); }

    public Bounds getBounds(){
        return shape.getBoundsInLocal();
    }

    public Rectangle getCast(){
        return shape;
    }
}
