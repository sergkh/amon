/**
 * 
 */
package ua.vntu.amon.provider;

/**
 * Default group implementation.
 * @author Sergey
 */
public class DefaultGroup extends AbstractEntity implements MonitoringGroup {
	public DefaultGroup() {}
	
	public DefaultGroup(String id, String name) {
		super(id, name);
	}
}
