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

    // basic frame config
    private void initializeFrame() {
        frame = new JFrame("TicTacToe Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setBackground(UI.DARK_BLUE);
    }

    // builds buttons and wires everything up
    private void createComponents() {
        vsPlayerButton = UI.createButton("VS Player");
        vsAIbutton = UI.createButton("VS AI");
        setupDifficultySlider();
        setupActionListeners();
    }

    // slider goes 1-3 mapping to DUMB, MID, UNBEATABLE
    private void setupDifficultySlider() {
        difficultySlider = new JSlider(1, 3, 2); // default to MID
        difficultySlider.setMajorTickSpacing(1);
        difficultySlider.setPaintTicks(true);
        difficultySlider.setPaintLabels(true);
        difficultySlider.setSnapToTicks(true); // only lands on whole numbers
        difficultySlider.setBackground(UI.DARK_BLUE);
        difficultySlider.setForeground(UI.LIGHT_YELLOW);
        difficultySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultySlider.setMaximumSize(new Dimension(150, 50));
    }

    // null difficulty means 2 player, otherwise reads the slider
    private void setupActionListeners() {
        vsPlayerButton.addActionListener(e -> startGame(null));
        vsAIbutton.addActionListener(e -> {
            Difficulty difficulty = mapSliderToDifficulty(difficultySlider.getValue());
            startGame(difficulty);
        });
    }

    // passes difficulty to GameView, null if 2 player
    private void startGame(Difficulty difficulty) {
        this.gameView = new GameView(difficulty);
        gameView.buildGui(frame);
    }

    // slider values don't map cleanly to an enum so we do it manually
    private Difficulty mapSliderToDifficulty(int value) {
        return switch (value) {
            case 1 -> Difficulty.DUMB;
            case 2 -> Difficulty.MID;
            default -> Difficulty.UNBEATABLE; // 3, but default covers anything unexpected too
        };
    }

    // uses vertical glue to keep everything centred regardless of window size
    private void setupLayout() {
        JLabel title = UI.createTitle("Tic Tac Toe");
        JLabel difficultyLabel = createDifficultyLabel();

        frame.add(Box.createVerticalGlue());
        frame.add(title);
        frame.add(Box.createRigidArea(new Dimension(0, 30))); // spacing between title and buttons
        frame.add(vsPlayerButton);
        frame.add(Box.createRigidArea(new Dimension(0, 15)));
        frame.add(vsAIbutton);
        frame.add(Box.createRigidArea(new Dimension(0, 20))); // bigger gap before difficulty section
        frame.add(difficultyLabel);
        frame.add(Box.createRigidArea(new Dimension(0, 5)));
        frame.add(difficultySlider);
        frame.add(Box.createVerticalGlue());
    }

    // styled label for above the difficulty slider
    private JLabel createDifficultyLabel() {
        JLabel label = new JLabel("AI Difficulty");
        label.setForeground(UI.LIGHT_YELLOW);
        label.setFont(UI.FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}