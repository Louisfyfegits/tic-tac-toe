package game;

import javax.swing.*;
import java.awt.*;

public class HomeView {

    private JFrame frame;
    private GameView gameView = new GameView();
    private JButton vsPlayerButton;
    private JButton vsAIbutton;

    public HomeView() {
        buildGui();
    }

    void buildGui(){
        frame = new JFrame("TicTacToe Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setBackground(UI.DARK_BLUE);

        vsPlayerButton = UI.createButton("VS Player");
        vsAIbutton = UI.createButton("VS AI");

        vsPlayerButton.addActionListener(e -> gameView.buildGui(frame));

        JLabel title = UI.createTitle("Tic Tac Toe");

        frame.add(Box.createVerticalGlue());
        frame.add(title);
        frame.add(Box.createRigidArea(new Dimension(0, 30)));
        frame.add(vsPlayerButton);
        frame.add(Box.createRigidArea(new Dimension(0, 15)));
        frame.add(vsAIbutton);
        frame.add(Box.createVerticalGlue());

        frame.setVisible(true);
    }
}