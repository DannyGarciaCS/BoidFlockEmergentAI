// EventsHandler.java
package handlers;

// Import statements
import java.awt.event.KeyAdapter;

// Events handler - Handles event behavior
public class EventHandler extends KeyAdapter {

	// Class variables
	LogicHandler logicHandler;

	// Constructor
	public EventHandler(LogicHandler logicHandler) {
		this.logicHandler = logicHandler;
	}
}
