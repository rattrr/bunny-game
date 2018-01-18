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
        double newX = shape.getX();
        double newY = shape.getY();
        switch(dir){
            case UP:
                newY -= px;
                break;
            case DOWN:
                newY += px;
                break;
            case LEFT:
                newX -= px;
                break;
            case RIGHT:
                newX += px;
                break;
        }
        if(newX > 0 && newX < 2000) {
            shape.setX(newX);
        }
        if(newY > 0 && newY < 410){
            shape.setY(newY);
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
