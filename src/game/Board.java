package game;

public class Board {
	private Token[][] board;
	private static int SIZE = 3;
	
	/**
	 * Token enum representing board cell states, with a overrided to String
	 */
	public enum Token {
	    EMPTY, X, O;
		public String getDisplayText() {
		    return this == EMPTY ? "" : name();
		}
	}
	
	/**
	 * Constructs a new Board and initializes it to all EMPTY tokens.
	 */
	public Board(){
		board = new Token[SIZE][SIZE];                         
		initializeBoard();
	}
	
	/**
	 * Fills the board with EMPTY tokens.
	 */
	private void initializeBoard() {
		for (int i = 0; i < SIZE; i++) {
	        for (int j = 0; j < SIZE; j++) {
	            board[i][j] = Token.EMPTY;
	        }
		}
	}
	
	/**
	 * Returns the token at a given position.
	 * @throws IndexOutOfBoundsException if indices are out of range
	 */
	public Token getToken(int i, int j) {
	    if (i < 0 || i >= SIZE || j < 0 || j >= SIZE) {
	        throw new IndexOutOfBoundsException("Invalid spot at (" + i + ", " + j + ")");
	    }
	    return board[i][j];
	}
	
	public int size() {
		return SIZE;
	}
	
	/**
	 * Places a token on the board at a given position if the spot is valid and empty.
	 * @throws IndexOutOfBoundsException if indices are out of range
	 * @throws IllegalArgumentException if the spot is already occupied
	 */
	public void placeToken(Token t, int i, int j) {
	    if (i < 0 || i >= SIZE || j < 0 || j >= SIZE) {
	        throw new IndexOutOfBoundsException("Can't place token at (" + i + ", " + j + ")");
	    }
	    if (board[i][j] != Token.EMPTY) {
	        throw new IllegalArgumentException("Spot at (" + i + ", " + j + ") is already occupied");
	    }
	    board[i][j] = t;
	}
		
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
	    for (int i = 0; i < SIZE; i++) {
	        for (int j = 0; j < SIZE; j++) {
	            sb.append(getToken(i, j));
	            if (j < SIZE - 1) {
	                sb.append(" | ");
	            }
	        }
	        if (i < SIZE - 1) {
	            sb.append("\n---------------------\n"); 
	        }
	    }
	    return sb.toString();
	}

}