package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import game.Game;
import game.Board.Token;

class GameTests{ 

    @Test
    void testInitialPlayerIsX() {
        Game game = new Game(false);
        assertEquals(Token.X, game.getCurrentPlayer());
    }


    @Test
    void testMakeMove() {
        Game game = new Game(false);
        game.makeMove(0, 0);
        assertEquals(Token.O, game.getCurrentPlayer());
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(0, 0));
        game.makeMove(0, 1);  // This should work fine
        assertEquals(Token.X, game.getCurrentPlayer());
    }

    @Test
    void testHasWonRow() {
        Game game = new Game(false);
        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 2); // X
        assertTrue(game.hasWon());  
    }
    
    @Test
    void testHasWonColumn() {
        Game game = new Game(false);
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(1, 0); // X
        game.makeMove(1, 1); // O
        game.makeMove(2, 0); // X
        assertTrue(game.hasWon());
    }

    @Test
    void testHasWonDiagonal() {
        Game game = new Game(false);
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(1, 1); // X
        game.makeMove(0, 2); // O
        game.makeMove(2, 2); // X
        assertTrue(game.hasWon());
    }
    @Test
    void testHasWonDiagonal2() {
        Game game = new Game(false);
        game.makeMove(0, 2); // X 
        game.makeMove(0, 1); // O
        game.makeMove(1, 1); // X 
        game.makeMove(0, 0); // O
        game.makeMove(2, 0); // X
        assertTrue(game.hasWon());
    }
    
    @Test
    void testHasWonDiagonalCenterButNoWin() {
        Game game = new Game(false);
        game.makeMove(1, 1); // X in center
        // Now: center is X, but no diagonal win
        assertFalse(game.hasWon());
    }

    @Test
    void testHasDrawn() {
        Game game = new Game(false);
        // Fill the board without a winner
        game.makeMove(0, 0); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 1); // X
        game.makeMove(0, 2); // O
        game.makeMove(2, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(1, 2); // X
        game.makeMove(2, 1); // O
        game.makeMove(2, 2); // X
        assertTrue(game.hasDrawn());
        assertFalse(game.hasWon());
        
        game = new Game(false);
        assertFalse(game.hasDrawn());  
    }
 

    @Test
    void testGetBoard() {
        Game game = new Game(false);
        assertNotNull(game.getBoard());
        game.makeMove(0, 0);
        assertEquals(Token.X, game.getBoard().getToken(0, 0));
    }
    @Test
    void testResetGame() {
        Game g = new Game(false);
        // Make some moves to make the board "dirty"
        g.makeMove(0, 0);
        g.makeMove(1, 1);
        g.makeMove(2, 2);
        
        // Clear the board
        g.resetGame();
        
        String expected =
            "\nEMPTY | EMPTY | EMPTY" +
            "\n---------------------" +
            "\nEMPTY | EMPTY | EMPTY" +
            "\n---------------------" +
            "\nEMPTY | EMPTY | EMPTY";
        
        assertEquals(expected, g.getBoard().toString());
    }
    
    
}
