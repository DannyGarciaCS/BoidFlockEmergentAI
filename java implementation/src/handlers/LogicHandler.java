// LogicHandler.java
package handlers;

//Import statements
import java.awt.Checkbox;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import main.Boid;
import main.World;

// Logic handler - Handles program logic
public class LogicHandler {

	// Class variables
	EventHandler eventHandler = new EventHandler(this);
	private ArrayList<Boid> boids;
	private World world;
	private Checkbox[] checkboxes;
	private Scrollbar[] sliders;

	// Constructor
	public LogicHandler(ArrayList<Boid> boids, World world,
	Checkbox[] checkboxes, Scrollbar[] sliders) {

		// Given variables
		this.boids = boids;
		this.world = world;
		this.checkboxes = checkboxes;
		this.sliders = sliders;

	}

	// Handles event's logic
	public void actionLogic(ActionEvent e) {

		// Handles checkbox logic
		for (int i=0; i<this.checkboxes.length; i++) {

			// Collision avoidance checkbox
			if (this.checkboxes[0].getState()) {
			this.world.setCollisionAvoidance(true); }
			else { this.world.setCollisionAvoidance(false); }

			// Direction alignment checkbox
			if (this.checkboxes[1].getState()) {
			this.world.setDirectionAlignment(true); }
			else { this.world.setDirectionAlignment(false); }

			// Direction alignment checkbox
			if (this.checkboxes[2].getState()) {
			this.world.setFlockCentering(true); }
			else { this.world.setFlockCentering(false); }

			// Highlight main checkbox
			if (this.checkboxes[3].getState()) {
			this.world.setHighlightMain(true); }
			else { this.world.setHighlightMain(false); }

			// Display directions checkbox
			if (this.checkboxes[4].getState()) {
			this.world.setDisplayDirection(true); }
			else { this.world.setDisplayDirection(false); }

			// Display proximity checkbox
			if (this.checkboxes[5].getState()) {
			this.world.setDisplayProximity(true); }
			else { this.world.setDisplayProximity(false); }

		}

		// Handles slider logic
		for (int i=0; i<this.sliders.length; i++) {

			// Sight degrees slider
			if (this.sliders[0].getUnitIncrement() != 0) {
			this.world.setSightDegrees(this.sliders[0].getValue()); }

			// Sight diameter slider
			if (this.sliders[1].getUnitIncrement() != 0) {
			this.world.setSightDiameter(this.sliders[1].getValue()); }

			// Speed slider
			if (this.sliders[2].getUnitIncrement() != 0) {
			this.world.setSpeed(this.sliders[2].getValue()); }

			// Rule 1 speed
			if (this.sliders[3].getUnitIncrement() != 0) {
			this.world.setCollisionAvoidanceSpeed(this.sliders[3].getValue()); }

			// Rule 2 speed
			if (this.sliders[4].getUnitIncrement() != 0) {
			this.world.setDirectionAlignmentSpeed(this.sliders[4].getValue()); }

			// Rule 3 speed
			if (this.sliders[5].getUnitIncrement() != 0) {
			this.world.setFlockCenteringSpeed(this.sliders[5].getValue()); }

		}

		// Updates boid positions and vertices
		for (Boid element : this.boids) {
			element.update();
			element.applyRules();
		}
	}

	// Getters
	public EventHandler getEventHandler() { return this.eventHandler; }

}
