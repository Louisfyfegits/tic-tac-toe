package tests;

import game.Board;
import game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AITests {
    @Test
    void testTokenStaysX() {
        Game game = new Game(true);
        game.makeMove(1, 1);
        assertEquals(Board.Token.X, game.getCurrentPlayer());
        assertThrows(IllegalArgumentException.class, () -> game.makeMove(1, 1));

    }

    @Test
    void testAIMadeAMove() {
        Game game = new Game(true);
        game.makeMove(1, 1);
        assertEquals(2, game.getBoard().getTokenCount());
    }
}
