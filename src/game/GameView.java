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

    public GameView() {
        this.buttons = new JButton[3][3];
        this.game = new Game();
    }

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

        JLabel rightSpacer = new JLabel("");
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

    private MatteBorder createBorderForPosition(int row, int col) {
        int top    = (row == 0) ? 0 : 5;
        int left   = (col == 0) ? 0 : 5;
        int bottom = (row == 2) ? 0 : 5;
        int right  = (col == 2) ? 0 : 5;
        return new MatteBorder(top, left, bottom, right, UI.LIGHT_YELLOW);
    }

    private void onCellClick(int row, int col) {
        try {
            game.makeMove(row, col);
        } catch (IllegalArgumentException e) {
            return;
        }
        buttons[row][col].setText(game.getBoard().getToken(row, col).getDisplayText());
        updateGameStatus();
    }

    private void onReset() {
        game.resetGame();
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                buttons[row][col].setText("");
        currentPlayerLabel.setText("Next: " + game.getCurrentPlayer());
        stateLabel.setText("");
    }

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