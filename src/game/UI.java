package game;

import javax.swing.*;
import java.awt.*;

public class UI {

    public static final Color DARK_BLUE = new Color(0, 5, 100);
    public static final Color LIGHT_YELLOW = new Color(250, 250, 150);
    public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 16);

    // standard button for the game
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(LIGHT_YELLOW);
        button.setForeground(DARK_BLUE);
        button.setFont(FONT);
        button.setFocusPainted(false); // gets rid of the dotted border when clicked
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 50));
        return button;
    }

    //title creation
    public static JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(LIGHT_YELLOW);
        label.setFont(new Font("Segoe UI", Font.BOLD, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}