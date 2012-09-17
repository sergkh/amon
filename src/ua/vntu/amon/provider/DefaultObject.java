/**
 * 
 */
package ua.vntu.amon.provider;

/**
 * Default object implementation.
 * @author Sergey
 */
public class DefaultObject extends AbstractEntity implements MonitoringObject {

	private State state;
	
	public DefaultObject() {
		this(null, null, null);
	}
	
	public DefaultObject(String id, String name) {
		this(id, name, State.ACTIVE);
	}
	
	public DefaultObject(String id, String name, State state) {
		super(id, name);
		this.state = state;
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
