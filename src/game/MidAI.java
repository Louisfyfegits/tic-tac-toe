package game;

class MidAI implements PlayerAI {
    private final DumbAI dumb;
    private final UnbeatableAI unbeatable;

    public MidAI(Board board) {
        System.out.println("mid makeMove called");
        this.dumb = new DumbAI(board);
        this.unbeatable = new UnbeatableAI(board);
    }

    @Override
    public void makeMove(Board.Token token) {
        double random =  Math.random();
        if (random < 0.7) {unbeatable.makeMove(token); } else {dumb.makeMove(token);}
    }
}