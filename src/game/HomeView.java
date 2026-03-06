package game;

import javax.swing.*;
import java.awt.*;

public class HomeView {

    private JFrame frame;
    private GameView gameView;
    private JButton vsPlayerButton;
    private JButton vsAIbutton;
    private JSlider difficultySlider;

    public HomeView() {
        initializeFrame();
        createComponents();
        setupLayout();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("TicTacToe Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setBackground(UI.DARK_BLUE);
    }

    private void createComponents() {
        vsPlayerButton = UI.createButton("VS Player");
        vsAIbutton = UI.createButton("VS AI");

        setupDifficultySlider();
        setupActionListeners();
    }

    private void setupDifficultySlider() {
        difficultySlider = new JSlider(1, 3, 2);
        difficultySlider.setMajorTickSpacing(1);
        difficultySlider.setPaintTicks(true);
        difficultySlider.setPaintLabels(true);
        difficultySlider.setSnapToTicks(true);
        difficultySlider.setBackground(UI.DARK_BLUE);
        difficultySlider.setForeground(UI.LIGHT_YELLOW);
        difficultySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultySlider.setMaximumSize(new Dimension(150, 50));
    }

    private void setupActionListeners() {
        vsPlayerButton.addActionListener(e -> startGame(null));

        vsAIbutton.addActionListener(e -> {
            Difficulty difficulty = mapSliderToDifficulty(difficultySlider.getValue());
            startGame(difficulty);
        });
    }

    private void startGame(Difficulty difficulty) {
        this.gameView = new GameView(difficulty);
        gameView.buildGui(frame);
    }

    private Difficulty mapSliderToDifficulty(int value) {
        return switch (value) {
            case 1 -> Difficulty.DUMB;
            case 2 -> Difficulty.MID;
            default -> Difficulty.UNBEATABLE;
        };
    }

    private void setupLayout() {
        JLabel title = UI.createTitle("Tic Tac Toe");
        JLabel difficultyLabel = createDifficultyLabel();

        frame.add(Box.createVerticalGlue());
        frame.add(title);
        frame.add(Box.createRigidArea(new Dimension(0, 30)));
        frame.add(vsPlayerButton);
        frame.add(Box.createRigidArea(new Dimension(0, 15)));
        frame.add(vsAIbutton);
        frame.add(Box.createRigidArea(new Dimension(0, 20)));
        frame.add(difficultyLabel);
        frame.add(Box.createRigidArea(new Dimension(0, 5)));
        frame.add(difficultySlider);
        frame.add(Box.createVerticalGlue());
    }

    private JLabel createDifficultyLabel() {
        JLabel label = new JLabel("AI Difficulty");
        label.setForeground(UI.LIGHT_YELLOW);
        label.setFont(UI.FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}