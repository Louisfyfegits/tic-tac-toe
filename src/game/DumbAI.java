package game;

class DumbAI implements PlayerAI {
    private final Board board;

    public DumbAI(Board board) {
        this.board = board;
    }

    @Override
    public void makeMove(Board.Token token) {
        System.out.println("UnbeatableAI  dumb makeMove called");
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (board.getToken(row, col) != Board.Token.EMPTY); // keep rolling until empty cell found
        board.placeToken(token, row, col); // place on the first empty cell found
    }
}