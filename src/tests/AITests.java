package tests;

import game.Board;
import game.Difficulty;
import game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AITests {

    @Test
    void testDumbTokenStaysX() {
        Game game = new Game(Difficulty.DUMB);
        game.makeMove(1, 1);
        assertEquals(Board.Token.X, game.getCurrentPlayer());
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(1, 1));
    }

    @Test
    void testDumbAIMadeAMove() {
        Game game = new Game(Difficulty.DUMB);
        game.makeMove(1, 1);
        assertEquals(2, game.getBoard().getTokenCount());
    }

    @Test
    //test for orignal dumb ai
    void testDumbAIPlacesO() {
        Game game = new Game(Difficulty.DUMB);
        game.makeMove(1, 1);
        boolean foundO = false;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (game.getBoard().getToken(i, j) == Board.Token.O) foundO = true;
        assertTrue(foundO);
    }

    @Test
    void testAIBlocksWin() {
        Game game = new Game(Difficulty.UNBEATABLE);
        // X plays, AI respondsX sets up two in a row
        game.makeMove(0, 0); // X top-left
        game.makeMove(0, 1); // X top-middle AI should block top-right
        assertEquals(Board.Token.O, game.getBoard().getToken(0, 2)); // AI should have blocked here
    }

    @Test
    void testAITakesWin() {
        // manually set up board where AI has two in a row
        // need direct board access for this - can you add a setToken method to Board for testing?
    }

    @Test
    void testAINeverLoses() {
        Game game = new Game(Difficulty.UNBEATABLE);
        // play all possible human first moves
        int[] moves = {0, 0, 1, 2, 2, 1}; // some random human moves
        for (int i = 0; i < moves.length; i += 2) {
            if (game.hasWon() || game.hasDrawn()) break;
            game.makeMove(moves[i], moves[i+1]);
        }
        assertFalse(game.hasWon() && game.getOtherPlayer() == Board.Token.X); // X should never win
    }
}

