package game;

public class GameController {

    private final Game game;
    private final GameView view;

    /**
     * Constructor - takes the Model and View.
     * Registers its own callbacks on the View so GameView never references GameController directly.
     */
    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
        view.setOnCellClick((row, col) -> onCellClick(row, col));
        view.setOnReset(() -> onReset());
    }

    /**
     * Called when a cell button is clicked.
     * Passes the move to Game, then updates the View based on the result.
     */
    private void onCellClick(int row, int col) {
        try {
            game.makeMove(row, col);
        } catch (IllegalArgumentException e) {
            return; // Spot already taken, ignore click
        }
        view.updateCell(row, col, game.getBoard().getToken(row, col).getDisplayText());
        updateGameStatus();
    }

    /**
     * Called when the reset button is clicked.
     * Resets the Model and tells the View to clear and show starting state.
     */
    private void onReset() {
        game.resetGame();
        view.clearBoard();
        view.showCurrentPlayer(game.getCurrentPlayer().toString());
    }

    /**
     * Asks Game for the current state and tells View what to display.
     * All if/else coordination logic lives here, not in the View.
     */
    private void updateGameStatus() {
        if (game.hasWon()) {
            view.showWin(game.getOtherPlayer().toString());
        } else if (game.hasDrawn()) {
            view.showDraw();
        } else {
            view.showCurrentPlayer(game.getCurrentPlayer().toString());
        }
    }
}