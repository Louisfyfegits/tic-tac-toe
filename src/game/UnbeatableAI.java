package game;

class UnbeatableAI implements PlayerAI {
    private final Board board;

    public UnbeatableAI(Board board) {
        this.board = board;
    }

    @Override
    public void makeMove(Board.Token token) {
        Board.Token humanToken = (token == Board.Token.O) ? Board.Token.X : Board.Token.O; // figure out which token the human has

        int bestScore = Integer.MIN_VALUE; // start with worst possible score
        int bestRow = -1; // placeholder until a real best move is found
        int bestCol = -1; // placeholder until a real best move is found

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                int score;

                if (board.getToken(i, j) == Board.Token.EMPTY) { // only try empty cells
                    board.placeToken(token, i, j); // try placing here
                    score = miniMax(true, humanToken, token); // human moves next after AI just placed
                    board.removeToken(i, j); // undo the move
                    if (score > bestScore) { // if this is the best move so far
                        bestScore = score; // update best score
                        bestRow = i; // remember the row
                        bestCol = j; // remember the col
                    }
                }
            }
        }
        board.placeToken(token, bestRow, bestCol); // place on the best cell found
    }

    public int miniMax(boolean isHuman, Board.Token humanToken, Board.Token AItoken) {
        if (hasWon(humanToken)) return -10; // human won this path, bad for AI
        if (hasWon(AItoken)) return 10; // AI won this path, great
        if (isFull()) return 0; // no moves left and no winner, draw

        int bestScore = isHuman ? Integer.MAX_VALUE : Integer.MIN_VALUE; // human minimises, AI maximises

        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (board.getToken(k, l) == Board.Token.EMPTY) { // only try empty cells
                    if (isHuman) {
                        board.placeToken(humanToken, k, l); // simulate human placing here
                        int score = miniMax(!isHuman, humanToken, AItoken); // recurse as AI's turn
                        if (score < bestScore) bestScore = score; // human wants the lowest score
                        board.removeToken(k, l); // undo
                    } else {
                        board.placeToken(AItoken, k, l); // simulate AI placing here
                        int score = miniMax(!isHuman, humanToken, AItoken); // recurse as human's turn (! just does the opposite)
                        if (score > bestScore) bestScore = score; // AI wants the highest score
                        board.removeToken(k, l); // undo
                    }
                }
            }
        }
        return bestScore; // return the best score found at this level
    }

    private boolean hasWon(Board.Token t) {
        for (int i = 0; i < 3; i++) {
            if (board.getToken(i, 0) == t && board.getToken(i, 1) == t && board.getToken(i, 2) == t)
                return true; // check row
            if (board.getToken(0, i) == t && board.getToken(1, i) == t && board.getToken(2, i) == t)
                return true; // check col
        }
        if (board.getToken(0, 0) == t && board.getToken(1, 1) == t && board.getToken(2, 2) == t)
            return true; // top-left to bottom-right diagonal
        if (board.getToken(0, 2) == t && board.getToken(1, 1) == t && board.getToken(2, 0) == t)
            return true; // top-right to bottom-left diagonal
        return false;
    }

    private boolean isFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board.getToken(i, j) == Board.Token.EMPTY) return false; // found an empty cell so not full
        return true; // no empty cells found
    }
}