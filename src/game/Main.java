package game;

import javax.swing.SwingUtilities;

public class Main {

    /**
     * Entry point. Wires together the Model, View, and Controller, then launches the UI.
     * GUI is created on the EDT via invokeLater to ensure thread safety.
     */
    static void main() {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            GameView view = new GameView();
            new GameController(game, view);// controller binds callbacks
            view.buildGui();
        });
    }
}