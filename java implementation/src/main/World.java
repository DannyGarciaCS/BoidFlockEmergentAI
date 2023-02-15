// World.java
package main;

// World class - contains general world info
public class World {

	// Boid rule No1: Avoid collison
	private boolean collisionAvoidance = true;

	// Boid rule No2: Align average direction
	private boolean directionAlignment = true;

	// Boid rule No3: Steer to center of mass
	private boolean flockCentering = true;

	// Display lines indicating boid directions
	private boolean displayDirection = false;

	// Display distance to other boids using colored lines
	private boolean displayProximity = false;

	// Highlights main boid and displays other metrics
	private boolean highlightMain = true;

	// Number of active boids
	private int boidCount = 125;

	// Degrees of visibility looking straight
	private double sightDegrees = 300;

	// Diameter in pixels of visibility
	private double sightDiameter = 300;

	// Boid movement speed
	private double speed = 8;
	
	// Speed control for first rule
	private double collisionAvoidanceSpeed = 5;
	
	// Speed control for second rule
	private double directionAlignmentSpeed = 2;
	
	// Speed control for third rule
	private double flockCenteringSpeed = 4;

	// Getters
	public boolean getCollisionAvoidance() { return collisionAvoidance; }

	public boolean getDirectionAlignment() { return directionAlignment; }

	public boolean getFlockCentering() { return flockCentering; }

	public boolean getDisplayDirection() { return displayDirection; }

	public boolean getDisplayProximity() { return displayProximity; }

	public boolean getHighlightMain() { return highlightMain; }

	public int getBoidCount() { return boidCount; }

	public double getSightDegrees() { return sightDegrees; }

	public double getSightDiameter() { return sightDiameter; }

	public double getSpeed() { return speed; }

	public double getCollisionAvoidanceSpeed() { return collisionAvoidanceSpeed; }

	public double getDirectionAlignmentSpeed() { return directionAlignmentSpeed; }

	public double getFlockCenteringSpeed() { return flockCenteringSpeed; }

	// Setters
	public void setCollisionAvoidance(boolean collisionAvoidance) {
		this.collisionAvoidance = collisionAvoidance;
	}

	public void setDirectionAlignment(boolean directionAlignment) {
		this.directionAlignment = directionAlignment;
	}

	public void setFlockCentering(boolean flockCentering) {
		this.flockCentering = flockCentering;
	}

	public void setDisplayDirection(boolean displayDirection) {
		this.displayDirection = displayDirection;
	}

	public void setDisplayProximity(boolean displayProximity) {
		this.displayProximity = displayProximity;
	}

	public void setHighlightMain(boolean highlightMain) {
		this.highlightMain = highlightMain;
	}

	public void setSightDegrees(double sightDegrees) {
		this.sightDegrees = sightDegrees;
	}

	public void setSightDiameter(double sightDiameter) {
		this.sightDiameter = sightDiameter;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setCollisionAvoidanceSpeed(double collisionAvoidanceSpeed) {
		this.collisionAvoidanceSpeed = collisionAvoidanceSpeed;
	}

	public void setDirectionAlignmentSpeed(double directionAlignmentSpeed) {
		this.directionAlignmentSpeed = directionAlignmentSpeed;
	}

	public void setFlockCenteringSpeed(double flockCenteringSpeed) {
		this.flockCenteringSpeed = flockCenteringSpeed;
	}
}
