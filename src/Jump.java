public class Jump implements Command {
    Actor actor;
    Direction direction;
    double lastY;
    double lastX;

    public Jump(Actor actor, Direction direction){
        this.actor = actor;
        this.direction = direction;
        this.lastX = actor.getImage().getX();
        this.lastY = actor.getImage().getY();
    }

    @Override
    public void execute() {
        //actor.getImage().setY(100);
        actor.isJumping = true;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        actor.jump(direction);
    }

    @Override
    public void undo() {
        //actor.getImage().setX(lastX);
        //actor.getImage().setY(lastY);
    }
}
