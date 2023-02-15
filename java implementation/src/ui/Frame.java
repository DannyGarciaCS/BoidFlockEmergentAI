// Frame.java
package ui;

// Import statements
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import handlers.EventHandler;

// Frame class - generates window frame
public class Frame extends JFrame {

	// Version ID
	private static final long serialVersionUID = 1176506746250750564L;

	// Class variables
	private Panel panel;

	// Constructor
	public Frame() {

		// Generates panel
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		// Initializes panel
		panel = new Panel();

		// Joins and packs components
		this.add(panel);
		this.pack();

		// Creates event handler
		EventHandler eventHandler = panel.getLogicHandler().getEventHandler();
		this.addKeyListener(eventHandler);

	}

	// Executes bulk of program
	public void execute() {

		// Closes with quit button
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Disables window resizing
		this.setResizable(false);

		// Sets window title
		this.setTitle("Boid Flock Simulator");

		// Changes window icon
		ImageIcon icon = new ImageIcon("src/icon.png");
		this.setIconImage(icon.getImage());

		// Centers window and makes it visible
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}
}
