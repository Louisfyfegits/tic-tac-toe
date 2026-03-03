package game;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.function.BiConsumer;

public class GameView {

    private JFrame frame;
    private JPanel gridPanel;
    private JPanel buttonPanel;
    private JPanel messagePanel;
    private JLabel currentPlayerLabel;
    private JLabel stateLabel;
    private JButton[][] buttons;

    // Callbacks set by GameController - GameView has no idea who is listening
    private Runnable onReset;
    private BiConsumer<Integer, Integer> onCellClick;

    // Colors and font
    private final Color darkBlue = new Color(0, 5, 100);
    private final Color lightYellow = new Color(250, 250, 150);
    private final Font font = new Font("Segoe UI", Font.PLAIN, 16);

    /**
     * Constructor - initializes the button grid only.
     * Knows nothing about Game or GameController.
     */
    public GameView() {
        this.buttons = new JButton[3][3];
    }

    // --- Callback setters, called by GameController to wire itself in ---

    /** Called by GameController to register the reset callback. */
    public void setOnReset(Runnable callback) {
        this.onReset = callback;
    }

    /** Called by GameController to register the cell click callback. */
    public void setOnCellClick(BiConsumer<Integer, Integer> callback) {
        this.onCellClick = callback;
    }

    /**
     * Builds and displays the full GUI.
     * Callbacks must be set before calling this.
     */
    public void buildGui() {
        frame = new JFrame("TicTacToe Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(darkBlue);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(darkBlue);

        messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(darkBlue);

        buildGrid();
        buildResetButton();
        buildMessagePanel();

        frame.add(messagePanel);
        frame.add(gridPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    /**
     * Creates the 3x3 grid of cell buttons.
     * Each button fires the onCellClick callback when clicked.
     */
    private void buildGrid() {
        GridBagConstraints gbc = new GridBagConstraints();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = createGridButton(row, col);
                gbc.gridx = col;
                gbc.gridy = row;
                gridPanel.add(button, gbc);
                buttons[row][col] = button;
            }
        }
    }

    /**
     * Creates the reset button at the bottom.
     * Fires the onReset callback when clicked.
     */
    private void buildResetButton() {
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton resetButton = new JButton("Reset Game");
        resetButton.setBackground(lightYellow);
        resetButton.setForeground(darkBlue);
        resetButton.setFont(font);
        resetButton.setFocusPainted(false);
        resetButton.setContentAreaFilled(true);
        resetButton.setBorderPainted(false);
        resetButton.addActionListener(e -> onReset.run());

        buttonPanel.add(resetButton);
    }

    /**
     * Creates the top message panel with current player and game state labels.
     */
    private void buildMessagePanel() {
        messagePanel.setPreferredSize(new Dimension(500, 50));
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        messagePanel.setLayout(new BorderLayout());

        currentPlayerLabel = new JLabel("Next: X");
        currentPlayerLabel.setForeground(lightYellow);
        currentPlayerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        stateLabel = new JLabel("");
        stateLabel.setForeground(lightYellow);
        stateLabel.setFont(new Font("Segoe UI", Font.BOLD, 50));
        stateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Spacer keeps the state label centered by balancing the left label
        JLabel rightSpacer = new JLabel("");
        rightSpacer.setPreferredSize(currentPlayerLabel.getPreferredSize());

        messagePanel.add(currentPlayerLabel, BorderLayout.WEST);
        messagePanel.add(stateLabel, BorderLayout.CENTER);
        messagePanel.add(rightSpacer, BorderLayout.EAST);
    }

    /**
     * Creates a single cell button.
     * Fires the onCellClick callback with its row/col when clicked.
     */
    private JButton createGridButton(int row, int col) {
        JButton button = new JButton();
        button.setBackground(darkBlue);
        button.setPreferredSize(new Dimension(100, 100));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.BOLD, 36));
        button.setForeground(lightYellow);
        button.addActionListener(e -> onCellClick.accept(row, col));
        button.setBorder(createBorderForPosition(row, col));
        return button;
    }

    /**
     * Creates the correct border for a cell based on its position,
     * so inner edges get dividers but outer edges stay clean.
     */
    private MatteBorder createBorderForPosition(int row, int col) {
        int top    = (row == 0) ? 0 : 5;
        int left   = (col == 0) ? 0 : 5;
        int bottom = (row == 2) ? 0 : 5;
        int right  = (col == 2) ? 0 : 5;
        return new MatteBorder(top, left, bottom, right, lightYellow);
    }

    // --- Public display methods called by GameController ---

    /** Updates a single cell's display text (X, O, or empty). */
    public void updateCell(int row, int col, String text) {
        buttons[row][col].setText(text);
    }

    /** Clears all cell buttons back to empty. */
    public void clearBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
    }

    /** Displays the winning player. */
    public void showWin(String player) {
        stateLabel.setText(player + " Wins");
    }

    /** Displays a draw message. */
    public void showDraw() {
        stateLabel.setText("Draw");
    }

    /** Updates the current player label and clears any state message. */
    public void showCurrentPlayer(String player) {
        currentPlayerLabel.setText("Next: " + player);
        stateLabel.setText("");
    }
}