package game;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import map.Collidable;
import map.GameMap;

public class Cast implements Collidable {
    private Actor actor;
    private GameMap gameMap;
    private Rectangle shape;
    Cast(Actor actor, Color color, GameMap gameMap){
        this.actor = actor;
        this.gameMap = gameMap;
        shape = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        shape.setStroke(color);
        shape.setFill(Color.TRANSPARENT);
    }

    private void updateCoords(double x, double y){
        shape.setX(x);
        shape.setY(y);
    }

    private void move(Direction dir, double px){
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
                newX -= px * actor.getMomentum();
                break;
            case RIGHT:
                newX += px * actor.getMomentum();
                break;
        }
        if(newX > 0 && newX < 2000 - actor.getWidth()) {
            shape.setX(newX);
        }
        if(newY > 0 && newY < 410){
            shape.setY(newY);
        }
    }

    public Bounds getBounds(){
        return shape.getBoundsInLocal();
    }

    @SuppressWarnings("unused")
    public Rectangle getCast(){
        return shape;
    }

    double calculateMinY(Direction direction, double pxPerStepY){
        updateCoords(actor.getImage().getX(), actor.getImage().getY());
        while(gameMap.collisionWithBlocks(this) == null && shape.getY() < 402){
            move(Direction.DOWN, pxPerStepY);
            if(direction != Direction.NONE){
                move(direction, (double) 1);
            }
        }
        move(Direction.UP, 4);
        while(gameMap.collisionWithBlocks(this) == null && shape.getY() < 402){
            move(Direction.DOWN, 1);
        }
        move(Direction.UP, 1);
        return shape.getY();
    }

    double calculateMaxY() {
        double startY = actor.getY();
        updateCoords(actor.getImage().getX(), actor.getImage().getY());
        while (gameMap.collisionWithBlocks(this) == null && shape.getY() > startY - actor.getJumpHeight() && shape.getY() > 5) {
            move(Direction.UP, 4);
            move(actor.getLastDirection(), 1);
        }
        move(Direction.DOWN, 1);
        return shape.getY();
    }
}
