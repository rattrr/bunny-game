public class MoveLeft implements Command {
    Actor actor;

    public MoveLeft(Actor actor){
        this.actor = actor;
    }

    @Override
    public void execute() {
        actor.changeStatePicture(Direction.LEFT);
        actor.move(Direction.LEFT, 4);
    }

    @Override
    public void undo() {
        actor.changeStatePicture(Direction.RIGHT);
        actor.move(Direction.RIGHT, 4);
    }
}
