package game;

public class Fall implements Command {
    private Actor actor;
    private GameLoop gameLoop;
    Fall(Actor actor, GameLoop gameLoop){
        this.gameLoop = gameLoop;
        this.actor = actor;

    }
    @Override
    public void execute() {
        actor.fall(gameLoop.getCastDown().calculateMinY(actor.getLastDirection(), 4), actor.getLastDirection());
    }

    @Override
    public void undo() {
        //nothing to undo
    }
}
