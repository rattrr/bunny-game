package game;

public class MoveRight implements Command {
    private Actor actor;

    MoveRight(Actor actor){
        this.actor = actor;
    }

    @Override
    public void execute() {
        if((!actor.getCurrentAction().equals(Action.FALLING) && !actor.getCurrentAction().equals(Action.JUMPING))) {
            actor.changeStatePicture(Direction.RIGHT);
            actor.run(Direction.RIGHT);
            actor.setLastDirection(Direction.RIGHT);
        }
    }

    @Override
    public void undo() {
        actor.run(Direction.LEFT);
    }
}
