// Boid.java
package main;

// Import statements
import java.awt.Color;
import java.util.ArrayList;

// Boid class - holds boids' info and functionality
public class Boid{

	// Initialization variables
	boolean main;
	World world;

	// Location variables
	private double x;
	private double y;
	private double direction;
	private double[] cutVertices;

	// Boid variables
	private ArrayList<Boid> collection;
	private ArrayList<Boid> near;

	// Constructor
	public Boid(boolean main, World world) {

		// Given arguments
		this.main = main;
		this.world = world;

		// Position has a margin of 50 on both x and y directions
		this.x = Math.random() * 1100;
		this.y = Math.random() * 900;

		// Randomly initializes direction
		this.direction = Math.random() * 4;

	}

	// Updates boid information
	public void update() {

		// Updates position based on direction
		this.x += this.world.getSpeed() *
		Math.sin(this.direction * (Math.PI/2.0));
		this.y -= this.world.getSpeed() *
		Math.cos(this.direction * (Math.PI/2.0));

		// Warps along y axis
		if (this.y < -10) { this.y = 905; }
		else if (this.y > 910) { this.y = -5; }

		// Warps along x axis
		if (this.x < -10) { this.x = 1105; }
		else if (this.x > 1110) { this.x = -5; }

		// Updates pie cuts
		this.setCutVertices(this.pieCut());

	}

	// Applies boid rules
	public void applyRules() {

		// Collision avoidance rule
		if (this.world.getCollisionAvoidance()) {
			this.collisionAvoidanceRule(); }

		// Direction alignment rule
		if (this.world.getDirectionAlignment()) {
			this.directionAlignmentRule(); }

		// Flock centering rule
		if (this.world.getFlockCentering()) {
			this.flockCenteringRule(); }

	}

	// Boid rule - Avoid collisions
	public void collisionAvoidanceRule() {

		// Ensures there are boids near
		if (this.near != null) {

			// Defines speed
			double speed = this.world.getCollisionAvoidanceSpeed() / 100;
			double minBuffer = 10;
			double[] distances = new double[3];
			double[] minimums = new double[3];
			Boid future;

			// Computes different directions
			for (int i=0; i<3; i++) {

				// Generates clone boid
				future = new Boid(false, this.world);
				future.setDirection(this.direction);
				future.setX(this.x);
				future.setY(this.y);

				// Updates boid
				future.turn(-speed + (speed * i));
				future.update();
				future.setCollection(this.collection);
				future.setCutVertices(future.pieCut());
				future.setNear(this.near);

				// Updates corresponding distance
				distances[i] = future.neighbourDistance();
				minimums[i] = future.minimumDistance();

			}

			// Maximizes future distance
			if (distances[0] > distances[1] && distances[0] > distances[2] &&
			minimums[0] > minBuffer) { this.turn(-speed); }
			else if (distances[2] > distances[1] && distances[2] > distances[0] &&
			minimums[2] > minBuffer) { this.turn(speed); }
			else if (distances[0] > distances[1] && distances[0] > distances[2]) {
			this.turn(-speed); }
			else if (distances[2] > distances[1] && distances[2] > distances[0]) {
			this.turn(speed); }

		}
	}

	// Computes total distance between self and neighbours
	public double neighbourDistance() {

		// Initializes total distance
		double total = 0;

		// Ensures there are neighbours
		if (this.near != null) {

			// Appends neighbours
			for (int i=0; i<this.getNear().size(); i++) {
				total += this.computeDistance(this.x, this.y,
				this.near.get(i).getX(), this.near.get(i).getY());
			}
		}

		// Returns computed distance
		return total;
	}

	// Computes minimum neighbor distance
	public double minimumDistance() {

		// Initializes minimum distance
		double minimum = 1000000;

		// Ensures there are neighbours
		if (this.near != null) {

			// Sees if neighbour is closer
			for (int i=0; i<this.getNear().size(); i++) {

				// Smaller than minimum
				if (this.computeDistance(this.x, this.y,
				this.near.get(i).getX(), this.near.get(i).getY()) < minimum) {

					// Updates minimum
					minimum = this.computeDistance(this.x, this.y,
					this.near.get(i).getX(), this.near.get(i).getY());
				}
			}
		}

		// Returns computed minimum distance
		return minimum;

	}

	// Boid rule - Align boid direction
	public void directionAlignmentRule() {

		double speed = this.world.getDirectionAlignmentSpeed() / 100;
		double average = this.direction;

		// Ensures there are neighbors
		if (this.near != null) {

			// Adds to average
			for (Boid element : this.near) {
				average += element.direction;
			}

			// Divides by number of members
			average = average / (1 + this.near.size());

		}

		// Updates direction
		if (average > this.direction) { this.turn(speed); }
		if (average < this.direction) { this.turn(-speed); }

	}

	// Boid rule - Attempts to move to flock center
	public void flockCenteringRule() {

		double speed = this.world.getFlockCenteringSpeed() / 100;
		double averageX = this.x;
		double averageY = this.y;

		// Ensures there are neighbors
		if (this.near != null) {

			// Adds to average
			for (Boid element : this.near) {
				averageX += element.getX();
				averageY += element.getY();
			}

			// Divides by number of members
			averageX = averageX / (1 + this.near.size());
			averageY = averageY / (1 + this.near.size());

		}

		// Defines speed
		double[] distances = new double[3];
		Boid future;

		// Computes different directions
		for (int i=0; i<3; i++) {

			// Generates clone boid
			future = new Boid(false, this.world);
			future.setDirection(this.direction);
			future.setX(this.x);
			future.setY(this.y);

			// Updates boid
			future.turn(-speed + (speed * i));
			future.update();
			future.setCollection(this.collection);
			future.setCutVertices(future.pieCut());
			future.setNear(this.near);

			// Updates corresponding distance
			distances[i] = future.computeDistance(
			averageX, averageY, future.getX(), future.getY());

		}

		// Minimizes distance
		if (distances[0] > distances[1] && distances[0] > distances[2]) {
		this.turn(speed); }
		else if (distances[2] > distances[1] && distances[2] > distances[0]) {
		this.turn(-speed); }
	}

	// Determine if a given boid sees another or not
	public boolean sees(Boid target) {

		// Fetches necessary pieces of information
		double distance = computeDistance(
		this.x, this.y, target.getX(), target.getY());

		// Determines if target is near or not
		if ((distance <= this.world.getSightDiameter() / 2) &&
		(this.insideCut(target.x, target.y))) {
			return true;
		} else { return false; }
	}

	// Determines if a given point is inside or outside cut
	public boolean insideCut(double x, double y) {

		// Defines position variables
		double x1 = this.cutVertices[0];
		double y1 = this.cutVertices[1];
		double x2 = this.cutVertices[2];
		double y2 = this.cutVertices[3];
		double x3 = this.cutVertices[4];
		double y3 = this.cutVertices[5];

		// Computes area of full triangle
		double A = triangleArea(x1, y1, x2, y2, x3, y3);

		// Computes area of triangle sections
		double A1 = triangleArea(x, y, x2, y2, x3, y3);
		double A2 = triangleArea(x1, y1, x, y, x3, y3);
		double A3 = triangleArea(x1, y1, x2, y2, x, y);

		// Determines if point is in valid area or not
		if (this.world.getSightDegrees() > 180) {
		return !(Math.abs(A - (A1 + A2 + A3)) < 0.1);
		} else { return (Math.abs(A - (A1 + A2 + A3)) < 0.1); }
	}

	// Computes area of triangle cut
	public double triangleArea(double x1, double y1,
	double x2, double y2, double x3, double y3) {
		return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	// Identifies all near boids
	public void identifyNear() {

		if (this.collection != null) {

			// initializes distance
			double distance = 0;
			this.near = new ArrayList<>();

			for (int i=0; i<this.collection.size(); i++) {

				// Computes distance to potential neighbor
				distance = computeDistance(this.x, this.y,
				this.collection.get(i).x, this.collection.get(i).y);

				// Determines if boid is a potential neighbor
				if (distance > 0 && distance <= this.world.getSightDiameter()
				/ 2 && this.sees(this.collection.get(i))) {

					// Adds current boid
					this.near.add(this.collection.get(i));
				}
			}
		}
	}

	// Computes linear distance between two points
	public double computeDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}

	// Computes triangle used for collision computation
	public double[] pieCut() {

		// Adjusts undefined degree
		if (Math.abs(180 - this.world.getSightDegrees()) < 0.1) {
			this.world.setSightDegrees(180.1); }

		// Initializes vertices - {x1, y1, x2, y2, x3, y3}
		double[] vertices = {this.x, this.y, 0, 0, 0, 0};

		// Computes auxiliary point one
		vertices[2] = this.x + (1000000 * Math.sin((this.direction +
		this.world.getSightDegrees() / 90 / 2) % 4 * (Math.PI/2.0)));

		vertices[3] = this.y - (1000000 * Math.cos((this.direction +
		(this.world.getSightDegrees() / 90) / 2) % 4 * (Math.PI/2.0)));

		// Computes auxiliary point one
		vertices[4] = this.x + (1000000 * Math.sin((this.direction -
		this.world.getSightDegrees() / 90 / 2) % 4 * (Math.PI/2.0)));

		vertices[5] = this.y - (1000000 * Math.cos((this.direction -
		(this.world.getSightDegrees() / 90) / 2) % 4 * (Math.PI/2.0)));

		// Returns vertices
		return vertices;
	}

	// Alters degrees by the given ammount
	public void turn(double amount) {

		// Adjusts direction
		this.direction += amount;

		// Gives direction bounds
		if (this.direction > 4 || this.direction < 0) {
			this.direction = this.direction % 4;
		}
	}

	// Returns corresponding color
	public Color getColor() {

		// Determines color to return
		if (this.main && this.world.getHighlightMain()) {
			return new Color(223, 96, 105); }
		else { return new Color(152, 189, 231); }
	}

	// Getters
	public boolean getMain() { return main; }

	public World getWorld() { return world; }

	public double getX() { return x; }

	public double getY() { return y; }

	public double getDirection() { return direction; }

	public ArrayList<Boid> getNear() { return near; }

	// Setters
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public void setCutVertices(double[] cutVertices) {
		this.cutVertices = cutVertices;
	}

	public void setCollection(ArrayList<Boid> collection) {
		this.collection = collection;
	}

	public void setNear(ArrayList<Boid> near) {
		this.near = near;
	}
}
