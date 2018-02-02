public class MoveLeft implements Command {
    Actor actor;

    public MoveLeft(Actor actor){
        this.actor = actor;
    }

    @Override
    public void execute() {
        if((!actor.currentAction.equals(Action.FALLING) && !actor.currentAction.equals(Action.JUMPING))) {
            actor.changeStatePicture(Direction.LEFT);
            actor.run(Direction.LEFT, 2);
        }
    }

    @Override
    public void undo() {
        actor.changeStatePicture(Direction.RIGHT);
        actor.run(Direction.RIGHT, 2);
    }
}
