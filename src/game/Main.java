package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.MatteBorder;


public class Main implements ActionListener {
	private JFrame frame;
	private JPanel gridPanel;
	private JPanel buttonPanel;
	private JPanel messagePanel;
	private JLabel currentPlayer;
	private JLabel state;
	private JButton[][] buttons;  // 3x3 grid of buttons
	private Game game;       // Your game logic
	
	
	
	//Font Settings and Global Colors
	private final Color darkBlue = new Color(0, 5, 100);
	private final Color lightYellow = new Color(250, 250, 150);
	private final Font font = new Font("Segoe UI", Font.PLAIN, 16);
	
	/**
	 * Main entry point for the TicTacToe application.
	 *The GUI is created on the Event Dispatch Thread (EDT) using invokeLater,
	 * which ensures that all GUI updates are done before anything else runs.
	 */
	public static void main(String[] args) { 
	    SwingUtilities.invokeLater(() -> {
	        var main = new Main();
	        main.buildBaseGui();
	    });
	}
	/**
	 * Constructor that initializes the button grid,
	 * and starts the game.
	 */
	public Main() {
		 this.game = new Game(); 
		 this.buttons = new JButton[3][3]; 
	}
	
	public void buildBaseGui() {
	    // Create the main application window with a title
	    frame = new JFrame("TicTacToe Game");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(500, 550);
	    // Stack panels vertically
	    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS)); 

	    // Create grid panel (Tic-Tac-Toe board)
	    gridPanel = new JPanel(new GridBagLayout());
	    gridPanel.setBackground(darkBlue);
	    
	    // Create button panel (bottom)
	    buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
	    buttonPanel.setBackground(darkBlue);
	   
	    // Create a message Panel(Top)
	    messagePanel = new JPanel(new BorderLayout());
	    messagePanel.setBackground(darkBlue);   

	    // Setup grid with buttons
	    setGrid();
	    //Setup play buttons
	    setButtons();
	    //Setup text boxes
	    setText();
	    // Add all panels to the frame
	    frame.add(messagePanel);
	    frame.add(gridPanel);
	    frame.add(buttonPanel);
	   
	    // Show the frame
	    frame.setVisible(true);
	}
	
	public void setGrid() {
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
    
    public void setButtons() {
        // Set the panel's size and prevent vertical stretching
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        //stop stretching
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
       
        // Create "Reset Game" button
        JButton resetButton = new JButton("Reset Game");
        resetButton.setBackground(lightYellow);
        resetButton.setForeground(darkBlue);             // text color
        resetButton.setFont(font);               // font
        resetButton.setFocusPainted(false);            // removes blue outline when clicked
        resetButton.setContentAreaFilled(true);        // keeps button filled on hover
        resetButton.setBorderPainted(false);          
        resetButton.setActionCommand("RESET");  //sets a command given to the listener
        resetButton.addActionListener(this);

        // Add button to the panel
        buttonPanel.add(resetButton);
    } 
    
    public void setText() {
    	 // Set the panel's size and prevent vertical stretching
        messagePanel.setPreferredSize(new Dimension(500, 50));
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        // Use GridBagLayout for perfect centering
        messagePanel.setLayout(new BorderLayout());
       
        // Create current player text box
        currentPlayer = new JLabel("Next: X");
        currentPlayer.setForeground(lightYellow);
        currentPlayer.setFont(new Font("Segoe UI",Font.PLAIN, 30));
        currentPlayer.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // Left padding
        
        // Create game state text box
        state = new JLabel("");
        state.setForeground(lightYellow);
        state.setFont(new Font("Segoe UI", Font.BOLD, 50));
        state.setHorizontalAlignment(SwingConstants.CENTER); // center it
        
        // Create an empty right panel to balance the layout
        JLabel rightSpacer = new JLabel("");
        rightSpacer.setPreferredSize(currentPlayer.getPreferredSize()); //spacer
    	messagePanel.add(currentPlayer, BorderLayout.WEST);    // Left side
	    messagePanel.add(state, BorderLayout.CENTER); // Center
	    messagePanel.add(rightSpacer, BorderLayout.EAST);//spacer
    }
  
    
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
	    if ("RESET".equals(command)) {
	        // Handle reset button
	    	reset();
	    	updateBoard();
	    	updateGameStatus(); 
	    }  
	    //else its a grid click
	}
	
	private JButton createGridButton(int row, int col) {
	    JButton button = new JButton();
	    button.setBackground(darkBlue);
	    button.setPreferredSize(new Dimension(100, 100));
	    button.setFocusPainted(false);
	    button.setContentAreaFilled(false);
	    
	    // Set font and color for the X/O text
	    button.setFont(new Font("Arial", Font.BOLD, 36)); 
	    button.setForeground(lightYellow); // Text color
	    
	    button.addActionListener(e -> handleGridClick(row, col));
	    button.setBorder(createBorderForPosition(row, col));
	    
	    return button;
	}
	
	private MatteBorder createBorderForPosition(int row, int col) {
	    int top = (row == 0) ? 0 : 5;
	    int left = (col == 0) ? 0 : 5;
	    int bottom = (row == 2) ? 0 : 5;
	    int right = (col == 2) ? 0 : 5;
	    
	    return new MatteBorder(top, left, bottom, right, lightYellow);
	}
	
	private void updateBoard() {
	    for (int row = 0; row < 3; row++) {
	        for (int col = 0; col < 3; col++) {
	        	buttons[row][col].setText(game.getBoard().getToken(row, col).getDisplayText());
	        }
	    }
	}
	
	private void handleGridClick(int row, int col) {
		 game.makeMove(row, col);
	        updateBoard();
	        updateGameStatus(); 
	}
	
	private void reset() {
		game.resetGame();
		state.setText("");
		currentPlayer.setText("Next: " + game.getCurrentPlayer());	 
	}
	
	/**
	 * Updates any game status display - e.g players current turn
	 */
	private void updateGameStatus() {
		 if (game.hasWon()) { 
			 state.setText(game.getOtherPlayer() +" Wins"); //due to switch after win
			 return;
		 }
		 if (game.hasDrawn()) {
			 state.setText("Draw");
			 return;
		 } 
		 //no win no draw change next token       
		 currentPlayer.setText("Next: " + game.getCurrentPlayer());	 
	}
}