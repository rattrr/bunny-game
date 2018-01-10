import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameLoop extends AnimationTimer {
    LinkedList<Command> commands = new LinkedList<>();
    LinkedList<Command> executed = new LinkedList<>();
    Actor bunny;
    GameMap gm;
    GameScene scene;
    ScoreInfo si;
    Shadow bunnyCastDown;
    Shadow bunnyCastUp;
    Direction lastDirection = Direction.RIGHT;

    GameLoop(GameScene scene, Actor bunny, GameMap gm, ScoreInfo si){
        listen(scene);
        this.bunny = bunny;
        this.scene = scene;
        this.gm = gm;
        this.si = si;
        this.bunnyCastDown = new Shadow(bunny, Color.DEEPPINK);
        this.bunnyCastUp = new Shadow(bunny, Color.DEEPPINK);
    }

    public Shadow getCastDown(){
        return bunnyCastDown;
    }

    public Shadow getCastUp(){
        return bunnyCastUp;
    }


    @Override
    public void handle(long now) {

        double minY = calculateMinY();
        collectItems();
        detectCollisions();
        if(bunny.isJumping == false) {
            bunny.fall(minY);
            calculateMaxY(120, lastDirection);
        }

        for(Command command: commands){
            command.execute();
            calculateMinY();
            executed.add(command);
            commands.remove(command);

            System.out.println("Last executed command: " + executed.getLast().getClass());
        }

    }

    private double calculateMinY(){
        bunnyCastDown.updateCoords(bunny.getImage().getX() ,bunny.getImage().getY());
        while(collision(bunnyCastDown, gm.getBlocks()) == null){
            bunnyCastDown.move(Direction.DOWN, 1);
        }
        return bunnyCastDown.getY()-1;
    }

    private double calculateMaxY(double jumpheight, Direction direction){
        double starty = bunny.getY();
        bunnyCastUp.updateCoords(bunny.getImage().getX() ,bunny.getImage().getY());
        while(collision(bunnyCastUp, gm.getBlocks()) == null && bunnyCastUp.getY() > starty - jumpheight ){
            bunnyCastUp.move(Direction.UP, 1);
            bunnyCastUp.move(direction, 0.25);
        }
        bunny.maxJump = bunnyCastUp.getY()+1;
        return bunnyCastUp.getY()-1;
    }

    private void detectCollisions(){
        if(collision(bunny, gm.getBlocks()) != null){
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Collision");
            bunny.fall(calculateMinY());
            executed.getLast().undo();
            executed.removeLast();
        }
    }

    private void collectItems(){
        Collidable collectable = collision(bunny, gm.getItems());
        if(collectable != null){
            gm.getItems().remove(collectable);
            scene.getGameGroup().getChildren().remove(collectable);
            si.increment();
        }
    }


    private Collidable collision(Collidable player, ArrayList<? extends Collidable> objects){
        for (Collidable obj : objects){
            if(player.getBounds().intersects(obj.getBounds())){
                return obj;
            }
        }
        return null;
    }
    public static <E> boolean containsInstance(LinkedList<E> list, Class<? extends E> commandClass) {
        return list.stream().anyMatch(e -> commandClass.isInstance(e));
    }


    private void listen(Scene scene){
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent event) {
                        String eventCode = event.getCode().toString();
                        System.out.println(eventCode);
                        switch(eventCode){
                            case "RIGHT":
                                if(!containsInstance(commands, MoveRight.class)){
                                    commands.add(new MoveRight(bunny));
                                    lastDirection = Direction.RIGHT;
                                }
                                break;
                            case "LEFT":
                                if(!containsInstance(commands, MoveLeft.class)){
                                    commands.add(new MoveLeft(bunny));
                                    lastDirection = Direction.LEFT;
                                }
                                break;
                            case "UP":
                                if(!containsInstance(commands, Jump.class)){
                                    commands.add(new Jump(bunny, lastDirection));
                                }
                                break;
                            case "DOWN":
                                lastDirection = Direction.NONE;
                        }
                    }
                }
        );
    }
}
