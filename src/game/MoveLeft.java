package game;

public class MoveLeft implements Command {
    private Actor actor;

    MoveLeft(Actor actor){
        this.actor = actor;
    }

    @Override
    public void execute() {
        if((!actor.getCurrentAction().equals(Action.FALLING) && !actor.getCurrentAction().equals(Action.JUMPING))) {
            actor.changeStatePicture(Direction.LEFT);
            actor.run(Direction.LEFT);
            actor.setLastDirection(Direction.LEFT);
        }
    }

    @Override
    public void undo() {
        actor.run(Direction.RIGHT);
    }
}
