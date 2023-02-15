// Panel.java
package ui;

//Import statements
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import handlers.GraphicsHandler;
import handlers.LogicHandler;
import main.Boid;
import main.World;

// Panel class - defines window attributes and behaviors
public class Panel extends JPanel implements ActionListener{

	// Version ID
	private static final long serialVersionUID = 1176506746250750564L;

	// Shared variables
	private ArrayList<Boid> boids = new ArrayList<>();
	private Checkbox[] checkboxes = new Checkbox[6];
	private Scrollbar[] sliders = new Scrollbar[6];
	private World world = new World();
	private Timer timer;

	// Handler variables
	private GraphicsHandler graphicsHandler;
	private LogicHandler logicHandler;

	// Performance variables
	long lastTime = System.nanoTime();
	int FPSCount = 0;

	// Constructor
	Panel() {

		// Generates checkboxes
		Checkbox tempCheck = new Checkbox("", this.world.getCollisionAvoidance());
        tempCheck.setBounds(1155, 115, 10, 10);
        this.checkboxes[0] = tempCheck;

        tempCheck = new Checkbox("", this.world.getDirectionAlignment());
        tempCheck.setBounds(1155, 155, 10, 10);
        this.checkboxes[1] = tempCheck;

        tempCheck = new Checkbox("", this.world.getFlockCentering());
        tempCheck.setBounds(1155, 195, 10, 10);
        this.checkboxes[2] = tempCheck;

        tempCheck = new Checkbox("", this.world.getHighlightMain());
        tempCheck.setBounds(1155, 235, 10, 10);
        this.checkboxes[3] = tempCheck;

        tempCheck = new Checkbox("", this.world.getDisplayDirection());
        tempCheck.setBounds(1155, 275, 10, 10);
        this.checkboxes[4] = tempCheck;

        tempCheck = new Checkbox("", this.world.getDisplayProximity());
        tempCheck.setBounds(1155, 315, 10, 10);
        this.checkboxes[5] = tempCheck;

        // Generates sliders
 		Scrollbar tempSlider = new Scrollbar(Scrollbar.HORIZONTAL,
 		(int) this.world.getSightDegrees(), 1, 1, 361);
 		tempSlider.setBounds(1180, 393, 355, 15);
        this.sliders[0] = tempSlider;

        tempSlider = new Scrollbar(Scrollbar.HORIZONTAL,
        (int) this.world.getSightDiameter(), 1, 1, 501);
        tempSlider.setBounds(1180, 473, 355, 15);
        this.sliders[1] = tempSlider;

        tempSlider = new Scrollbar(Scrollbar.HORIZONTAL,
        (int) this.world.getSpeed(), 1, 1, 16);
        tempSlider.setBounds(1180, 553, 355, 15);
        this.sliders[2] = tempSlider;

        tempSlider = new Scrollbar(Scrollbar.HORIZONTAL,
 		(int) this.world.getCollisionAvoidanceSpeed(), 1, 1, 11);
 		tempSlider.setBounds(1180, 633, 355, 15);
        this.sliders[3] = tempSlider;

        tempSlider = new Scrollbar(Scrollbar.HORIZONTAL,
        (int) this.world.getDirectionAlignmentSpeed(), 1, 1, 11);
        tempSlider.setBounds(1180, 713, 355, 15);
        this.sliders[4] = tempSlider;

        tempSlider = new Scrollbar(Scrollbar.HORIZONTAL,
        (int) this.world.getFlockCenteringSpeed(), 1, 1, 11);
        tempSlider.setBounds(1180, 793, 355, 15);
        this.sliders[5] = tempSlider;

		// Sets window size
		this.setPreferredSize(new Dimension(1600, 900));

		// Sets background color
		this.setBackground(new Color(29, 26, 59));

		// Defines frame delay
		this.timer = new Timer(1000 / 60, this);
		this.timer.start();

		// Generates boid list
		this.boids.add(new Boid(true, world));
		for (int i=0; i<this.world.getBoidCount(); i++) {
			this.boids.add(new Boid(false, this.world));
		}

		// Updates boid collection
		for (Boid element : this.boids) {
			element.setCollection(this.boids); }

		// Disables pre-defined layout
		this.setLayout(null);

		// Adds all checkboxes
		for (Checkbox element : this.checkboxes) { this.add(element); }

		// Adds all sliders
		for (Scrollbar slider : this.sliders) { this.add(slider); }

		// Creates handlers
		this.logicHandler = new LogicHandler(this.boids,
		this.world, this.checkboxes, this.sliders);
		this.graphicsHandler = new GraphicsHandler(this.boids);

	}

	// Determines program drawing behavior
	@Override
	public void paint(Graphics g) {

		// Draw behavior
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		this.graphicsHandler.draw(g2D);

		// Paints updated frame
		repaint();
	}

	// Event handler's logic
	@Override
	public void actionPerformed(ActionEvent e) { this.logicHandler.actionLogic(e); }

	// Getters
	public LogicHandler getLogicHandler() { return logicHandler; }

}
