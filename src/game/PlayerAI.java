package game;


//to be used by game controller to get move when not player ones turn
public class PlayerAI {
    private Board board;
    private Board.Token token;

    public PlayerAI(Board board) {
        this.board = board;
    }

    public void makeMove(Board.Token token) {
        this.token = token;
        makeDumbMove(); //only current will later be decidded by a paremeter which one to call
    }
    //random placement
    public void makeDumbMove() {
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (board.getToken(row, col) != Board.Token.EMPTY);
        board.placeToken(token, row, col);
    }
    //semi thought thru placement
    public void makeMidMove(){}

    //full minimax placement
    public void makeUnbeatableMove(){}

}
