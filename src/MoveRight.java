public class MoveRight implements Command {
    Actor actor;

    public MoveRight(Actor actor){
        this.actor = actor;
    }

    @Override
    public void execute() {
        System.out.println("right");
        actor.changeStatePicture(Direction.RIGHT);
        actor.move(Direction.RIGHT, 4);
    }

    @Override
    public void undo() {
        actor.changeStatePicture(Direction.LEFT);
        actor.move(Direction.LEFT, 4);
    }
}
