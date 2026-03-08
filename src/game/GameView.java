package game;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class GameView {

    private JPanel gridPanel;
    private JPanel buttonPanel;
    private JPanel messagePanel;
    private JLabel currentPlayerLabel;
    private JLabel stateLabel;
    private JButton[][] buttons;
    private Game game;

    public GameView(Difficulty difficulty) {
        this.buttons = new JButton[3][3];
        this.game = new Game(difficulty);
    }

    // clears the frame and rebuilds the game screen
    public void buildGui(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(UI.DARK_BLUE);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(UI.DARK_BLUE);

        messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(UI.DARK_BLUE);

        buildGrid();
        buildResetButton();
        buildMessagePanel();

        frame.add(messagePanel);
        frame.add(gridPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    // places each button into the grid with its position tracked
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

    private void buildResetButton() {
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JButton resetButton = UI.createButton("Reset Game");
        resetButton.addActionListener(e -> onReset());
        buttonPanel.add(resetButton);
    }

    // message panel has the current player on the left and win/draw state in the centre
    private void buildMessagePanel() {
        messagePanel.setPreferredSize(new Dimension(500, 50));
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        messagePanel.setLayout(new BorderLayout());

        currentPlayerLabel = new JLabel("Next: X");
        currentPlayerLabel.setForeground(UI.LIGHT_YELLOW);
        currentPlayerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        stateLabel = new JLabel("");
        stateLabel.setForeground(UI.LIGHT_YELLOW);
        stateLabel.setFont(new Font("Segoe UI", Font.BOLD, 50));
        stateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel rightSpacer = new JLabel(""); // keeps the state label visually centred
        rightSpacer.setPreferredSize(currentPlayerLabel.getPreferredSize());

        messagePanel.add(currentPlayerLabel, BorderLayout.WEST);
        messagePanel.add(stateLabel, BorderLayout.CENTER);
        messagePanel.add(rightSpacer, BorderLayout.EAST);
    }

    private JButton createGridButton(int row, int col) {
        JButton button = new JButton();
        button.setBackground(UI.DARK_BLUE);
        button.setPreferredSize(new Dimension(100, 100));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.BOLD, 36));
        button.setForeground(UI.LIGHT_YELLOW);
        button.addActionListener(e -> onCellClick(row, col));
        button.setBorder(createBorderForPosition(row, col));
        return button;
    }

    // only draws inner borders so the grid doesn't have an outer outline
    private MatteBorder createBorderForPosition(int row, int col) {
        int top    = (row == 0) ? 0 : 5;
        int left   = (col == 0) ? 0 : 5;
        int bottom = (row == 2) ? 0 : 5;
        int right  = (col == 2) ? 0 : 5;
        return new MatteBorder(top, left, bottom, right, UI.LIGHT_YELLOW);
    }

    // ignores clicks if the game is already over or the cell is taken
    private void onCellClick(int row, int col) {
        if (game.hasWon() || game.hasDrawn()) return;
        try {
            game.makeMove(row, col);
        } catch (IllegalArgumentException e) {
            return;
        }
        updateBoard();
        updateGameStatus();
    }

    // syncs button text with the board state
    private void updateBoard() {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                buttons[row][col].setText(game.getBoard().getToken(row, col).getDisplayText());
    }

    private void onReset() {
        game.resetGame();
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                buttons[row][col].setText("");
        currentPlayerLabel.setText("Next: " + game.getCurrentPlayer());
        stateLabel.setText("");
    }

    // updates labels after each move
    private void updateGameStatus() {
        if (game.hasWon()) {
            stateLabel.setText(game.getOtherPlayer() + " Wins");
        } else if (game.hasDrawn()) {
            stateLabel.setText("Draw");
        } else {
            currentPlayerLabel.setText("Next: " + game.getCurrentPlayer());
        }
    }
}