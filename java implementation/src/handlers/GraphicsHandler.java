// GraphicsHandler.java
package handlers;

// Import statements
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import main.Boid;

// Graphics handler - Handles program's drawing behavior
public class GraphicsHandler {

	// Class variables
	private ArrayList<Boid> boids;

	// Constructor
	public GraphicsHandler(ArrayList<Boid> boids) { this.boids = boids; }

	// Draw behavior
	public void draw(Graphics2D g2D) {

		// Draws boids + attached components
		for (int i=0; i<this.boids.size(); i++) {

			// Computes boids near given boid
			this.boids.get(i).identifyNear();

			// Generates rotation
			AffineTransform tx = new AffineTransform();
			tx.rotate((boids.get(i).getDirection()) * (Math.PI / 2.0),
			boids.get(i).getX(), boids.get(i).getY());

			// Generate vision arc
			g2D.setPaint(new Color(66, 59, 118));

			// Generates and displays arc if main
			if (this.boids.get(i).getMain() &&
			this.boids.get(i).getWorld().getHighlightMain()) {

				// Arc definition
				Arc2D visionArc = new Arc2D.Double(this.boids.get(i).getX() -
				this.boids.get(i).getWorld().getSightDiameter() / 2,
				this.boids.get(i).getY() -
				this.boids.get(i).getWorld().getSightDiameter() / 2,
				this.boids.get(i).getWorld().getSightDiameter(),
				this.boids.get(i).getWorld().getSightDiameter(), 90 -
				this.boids.get(0).getDirection() * 90 -
				this.boids.get(i).getWorld().getSightDegrees() / 2,
				this.boids.get(i).getWorld().getSightDegrees(), Arc2D.PIE);

				// Arc draw call
				g2D.fill(visionArc);

			}

			// Sets object color
			g2D.setPaint(this.boids.get(i).getColor());

			// Generates rotated boid
			int[] xPoints = {(int) (boids.get(i).getX() - 5),
			(int) boids.get(i).getX(), (int) (boids.get(i).getX() + 5)};
			int[] yPoints = {(int) (boids.get(i).getY() + 6),
			(int) (boids.get(i).getY() - 6), (int) (boids.get(i).getY() + 6)};
			Polygon rawBoid = new Polygon(xPoints, yPoints, 3);
			Shape boid = tx.createTransformedShape(rawBoid);

			// Draws direction if within main sight and visualization is active
			if ((this.boids.get(0).sees(this.boids.get(i)) || i == 0) &&
			this.boids.get(0).getWorld().getHighlightMain()) {

				// Displays directions
				if (this.boids.get(0).getWorld().getDisplayDirection()) {

					// Generates direction line
					g2D.setStroke(new BasicStroke(2));
					Line2D rawDirection = new Line2D.Double(this.boids.get(i).getX(),
					this.boids.get(i).getY(), this.boids.get(i).getX(),
					this.boids.get(i).getY() - 35);
					Shape direction = tx.createTransformedShape(rawDirection);
					g2D.draw(direction);

				}

				// Displays proximity
				if (this.boids.get(0).getWorld().getDisplayProximity()) {

					// Sets distance color
					g2D.setStroke(new BasicStroke(3));
					int alpha = (int) (255 * ((this.boids.get(0).computeDistance(
					this.boids.get(0).getX(), this.boids.get(0).getY(),
					this.boids.get(i).getX(), this.boids.get(i).getY()))  /
					(this.boids.get(i).getWorld().getSightDiameter() / 2)));
					g2D.setPaint(new Color(223, 96, 105, 255 - alpha));

					// Generates distance line
					Line2D directionLine = new Line2D.Double(this.boids.get(i).getX(),
					this.boids.get(i).getY(), this.boids.get(0).getX(),
					this.boids.get(0).getY());
					g2D.draw(directionLine);
					g2D.setStroke(new BasicStroke(1));

				}
			}

			// Sets object color
			g2D.setPaint(this.boids.get(i).getColor());

			// Draws boids
			g2D.fill(boid);

		}

		// Draws settings background
		g2D.setPaint(new Color(47, 41, 93));
		g2D.fillRect(1100, 0, 500, 900);

		// Draws settings header
		g2D.setPaint(new Color(240, 240, 240));
		g2D.setFont(new Font("Calibri", Font.PLAIN, 35));
		g2D.drawString("Simulator Settings", 1150, 75);

		// Draws sub-headers
		g2D.setFont(new Font("Calibri", Font.BOLD, 15));

		g2D.drawString("Avoid collisions", 1180, 125);
		g2D.drawString("Align directions", 1180, 165);
		g2D.drawString("Center flocks", 1180, 205);
		g2D.drawString("Highlight main", 1180, 245);
		g2D.drawString("Display directions", 1180, 285);
		g2D.drawString("Display proximity", 1180, 325);

		g2D.drawString("Sight Degrees", 1150, 365);
		g2D.drawString(String.format("%03.0f",
		this.boids.get(0).getWorld().getSightDegrees()), 1150, 405);
		g2D.drawString("Sight Diameter", 1150, 445);
		g2D.drawString(String.format("%03.0f",
		this.boids.get(0).getWorld().getSightDiameter()), 1150, 485);
		g2D.drawString("Speed", 1150, 525);
		g2D.drawString(String.format("%02.0f",
		this.boids.get(0).getWorld().getSpeed()), 1150, 565);
		
		g2D.drawString("Collision Avoidance Speed", 1150, 605);
		g2D.drawString(String.format("%02.0f",
		this.boids.get(0).getWorld().getCollisionAvoidanceSpeed()), 1150, 645);
		g2D.drawString("Direction Alignment Speed", 1150, 685);
		g2D.drawString(String.format("%02.0f",
		this.boids.get(0).getWorld().getDirectionAlignmentSpeed()), 1150, 725);
		g2D.drawString("Flock Centering Speed", 1150, 765);
		g2D.drawString(String.format("%02.0f",
		this.boids.get(0).getWorld().getFlockCenteringSpeed()), 1150, 805);

	}

	// Getters
	public ArrayList<Boid> getBoids() { return this.boids; }

	// Setters
	public void setBoids(ArrayList<Boid> boids) {
		this.boids = boids;
	}
}
