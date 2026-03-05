package game;

import game.Board.Token;
public class Game {

    private Board board;
    private Token currentToken = Token.X;
    private PlayerAI ai;
    private final boolean isAI;

    
    /**
     * Constructs a new game with a new empty board
     */
    public Game(boolean isAI) {
        this.board = new Board();
        this.isAI = isAI;
        if (isAI) this.ai = new PlayerAI(board);
    }
    
    /** comment must be updated to ai update
	 * Attempts to place the current player's token at the specified position,
	 * then current turn of the player. Uses place token of board class
	 * @throws IllegalArgumentException if the specified spot is already occupied.
	 */
	public void makeMove(int row, int col) {
	    if (board.getToken(row, col) != Token.EMPTY) {
	        throw new IllegalArgumentException("Spot already taken");
	    }
	    board.placeToken(currentToken, row, col);
        switchPlayer();

        if (isAI && !hasWon() && !hasDrawn()) {
            ai.makeMove(currentToken);
            switchPlayer();
        }
	}
    
    /**
     * Checks if the previous player has won the game, as player changes after token placement.
     * @return true if the current player has a winning combination
     */
	public boolean hasWon() { 
		Token t = (currentToken == Token.X) ? Token.O : Token.X; //switch to previous player
		for (int i = 0; i < board.size(); i++) {
			if (checkRowWin(t, i) || checkColWin(t, i)) return true;
			}
		return checkDiagonalWin(t);
	}
	
	/**
     * Checks if the game is a draw (i.e., board is full and no winner).
     * Does not check whether a player has won.
     * @return true if all cells are filled.
     */
	public boolean hasDrawn() {
		for(int i = 0; i<board.size(); i++) {
			for(int j = 0; j<board.size(); j++) {
				if(board.getToken(i, j)==Token.EMPTY) {return false;} //return false, is empty slot is found
			}
		}
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
        if (isAI) this.ai = new PlayerAI(board); // recreate AI with new board
    }
	
	private boolean checkRowWin(Token t, int row) {
		if (t == Token.EMPTY) return false;
		for(int i=0; i<board.size(); i++) {
			if(board.getToken(row, i)!=t) return false;
		}
		return true;
	}
	
	private boolean checkColWin(Token t, int col) {
		if (t == Token.EMPTY) return false;
		for(int i=0; i<board.size(); i++) {
			if(board.getToken(i, col)!=t) return false;
		}
		return true;
	}
	
	//hard coded 
	private boolean checkDiagonalWin(Token t) {
		if (t == Token.EMPTY) return false;
		if(board.getToken(1,1)!=t ) return false; //return false if center slot isn't the correct token
		if(board.getToken(0, 0)==t && board.getToken(2, 2)==t) return true;
		if(board.getToken(0, 2)==t && board.getToken(2, 0)==t) return true;
	  return false;
	}


}
