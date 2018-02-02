public class Jump implements Command {
    Actor actor;


    public Jump(Actor actor){
        System.out.println("NOWA KOMENDA");
        this.actor = actor;

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
