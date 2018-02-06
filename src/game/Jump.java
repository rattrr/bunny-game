package game;

public class Jump implements Command {
    private GameLoop gameLoop;
    private Actor actor;

    Jump(Actor actor, GameLoop gameLoop){
        this.gameLoop = gameLoop;
        this.actor = actor;

    }

    @Override
    public void execute() {
        actor.jump(gameLoop.getCastUp().calculateMaxY());
    }

    @Override
    public void undo() {
        //nothing to undo
    }
}
