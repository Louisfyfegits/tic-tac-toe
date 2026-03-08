package game;

// picks a random empty cell to place on
class DumbAI implements PlayerAI {
    private final Board board;

    public DumbAI(Board board) {
        this.board = board;
    }

    @Override
    public void makeMove(Board.Token token) {
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (board.getToken(row, col) != Board.Token.EMPTY); // keep rolling until an empty cell is found
        board.placeToken(token, row, col);
    }
}