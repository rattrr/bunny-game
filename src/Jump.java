public class Jump implements Command {
    Actor actor;
    double lastY;
    double lastX;

    public Jump(Actor actor){
        System.out.println("NOWA KOMENDA");
        this.actor = actor;
        this.lastX = actor.getImage().getX();
        this.lastY = actor.getImage().getY();
    }

    @Override
    public void execute() {
        actor.jump();
    }

    @Override
    public void undo() {
        //nothing to undo
    }
}
