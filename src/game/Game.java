package game;

import game.Board.Token;

public class Game {

    private Board board;
    private Token currentToken = Token.X;
    private PlayerAI ai;
    private final Difficulty difficulty;

    public Game(Difficulty difficulty) {
        System.out.println("Game created with difficulty: " + difficulty);
        this.board = new Board();
        this.difficulty = difficulty;
        if (difficulty != null) {
            this.ai = switch (difficulty) {
                case DUMB -> new DumbAI(board);
                case MID -> new MidAI(board);
                case UNBEATABLE -> new UnbeatableAI(board);
            };
        }
    }

    /**
     * Attempts to place the current player's token at the specified position,
     * then triggers AI move if applicable.
     * @throws IllegalArgumentException if the specified spot is already occupied.
     */
    public void makeMove(int row, int col) {
        if (board.getToken(row, col) != Token.EMPTY) {
            throw new IllegalArgumentException("Spot already taken");
        }
        board.placeToken(currentToken, row, col);
        switchPlayer();
        if (ai != null && !hasWon() && !hasDrawn()) {
            System.out.println("calling ai: " + ai.getClass().getSimpleName());
            ai.makeMove(currentToken);
            switchPlayer();
        }
    }

    /**
     * Checks if the previous player has won the game.
     * @return true if the previous player has a winning combination
     */
    public boolean hasWon() {
        Token t = (currentToken == Token.X) ? Token.O : Token.X;
        for (int i = 0; i < board.size(); i++) {
            if (checkRowWin(t, i) || checkColWin(t, i)) return true;
        }
        return checkDiagonalWin(t);
    }

    /**
     * Checks if the game is a draw (board is full and no winner).
     * @return true if all cells are filled.
     */
    public boolean hasDrawn() {
        for (int i = 0; i < board.size(); i++)
            for (int j = 0; j < board.size(); j++)
                if (board.getToken(i, j) == Token.EMPTY) return false;
        return true;
    }

    public void switchPlayer() {
        currentToken = (currentToken == Token.X) ? Token.O : Token.X;
    }

    public Token getCurrentPlayer() {
        return currentToken;
    }

    public Token getOtherPlayer() {
        return (currentToken == Token.X) ? Token.O : Token.X;
    }

    public Board getBoard() {
        return board;
    }

    public void resetGame() {
        this.board = new Board();
        currentToken = Token.X;
        if (difficulty != null) {
            this.ai = switch (difficulty) {
                case DUMB -> new DumbAI(board);
                case MID -> new MidAI(board);
                case UNBEATABLE -> new UnbeatableAI(board);
            };
        }
    }

    private boolean checkRowWin(Token t, int row) {
        if (t == Token.EMPTY) return false;
        for (int i = 0; i < board.size(); i++)
            if (board.getToken(row, i) != t) return false;
        return true;
    }

    private boolean checkColWin(Token t, int col) {
        if (t == Token.EMPTY) return false;
        for (int i = 0; i < board.size(); i++)
            if (board.getToken(i, col) != t) return false;
        return true;
    }

    private boolean checkDiagonalWin(Token t) {
        if (t == Token.EMPTY) return false;
        if (board.getToken(1, 1) != t) return false;
        if (board.getToken(0, 0) == t && board.getToken(2, 2) == t) return true;
        if (board.getToken(0, 2) == t && board.getToken(2, 0) == t) return true;
        return false;
    }
}