package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import game.Board;


class BoardTests {

    
	@Test 
	void testToString() {
	    Board b = new Board();   
	    String expected =
	        "\nEMPTY | EMPTY | EMPTY" +
	        "\n---------------------" +
	        "\nEMPTY | EMPTY | EMPTY" +
	        "\n---------------------" +
	        "\nEMPTY | EMPTY | EMPTY";

	    assertEquals(expected, b.toString());
	}
	
	@Test
	void testValidPlaceToken() {
		 Board b = new Board();
		 b.placeToken(Board.Token.O, 0, 0);
		 b.placeToken(Board.Token.X, 1, 0);
		 b.placeToken(Board.Token.O, 2, 0);
	     String expected =
	        "\nO | EMPTY | EMPTY" +
	        "\n---------------------" +
	        "\nX | EMPTY | EMPTY" +
	        "\n---------------------" +
	        "\nO | EMPTY | EMPTY";

	    assertEquals(expected, b.toString());
	}
	
	@Test
	void testInvalidPlaceToken() {
	    Board b = new Board();
	    b.placeToken(Board.Token.O, 0, 0);
	    
	    String expected =
	    		"\nO | EMPTY | EMPTY" +
	    		"\n---------------------" +
	    		"\nEMPTY | EMPTY | EMPTY" +
	    		"\n---------------------" +
	    		"\nEMPTY | EMPTY | EMPTY";
	    assertEquals(expected, b.toString());

	    // Check that placing X on the same spot throws an exception
	    assertThrows(IllegalArgumentException.class, () -> {
	        b.placeToken(Board.Token.X, 0, 0);
	    });
	    
	 // Test invalid indices throw exception
	    assertThrows(IndexOutOfBoundsException.class, () -> {
	        b.placeToken(Board.Token.X, -1, 0);
	    });
	    
	    //check there where no changes
	    assertEquals(expected, b.toString());
	}
	
	@Test
	void testGetToken() {
	    Board b = new Board();
	    b.placeToken(Board.Token.O, 2, 0);
	    assertEquals(Board.Token.O, b.getToken(2, 0)); //check for token
	    assertEquals(Board.Token.EMPTY, b.getToken(0, 0)); // check for empty

	    // Test invalid indices throw exception
	    assertThrows(IndexOutOfBoundsException.class, () -> b.getToken(0, -1));
	}
	
	
	@Test
	void testGetSize() {
		Board b = new Board();
		assertEquals(3, b.size());
	}
}
