public class MoveRight implements Command {
    Actor actor;

    public MoveRight(Actor actor){
        this.actor = actor;
    }

    @Override
    public void execute() {
        if((!actor.currentAction.equals(Action.FALLING) && !actor.currentAction.equals(Action.JUMPING))) {
            actor.changeStatePicture(Direction.RIGHT);
            actor.move(Direction.RIGHT, 2);
        }
    }

    @Override
    public void undo() {
        actor.changeStatePicture(Direction.LEFT);
        actor.move(Direction.LEFT, 2);
    }
}
