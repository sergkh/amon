package ua.vntu.amon.provider;


/**
 * Monitoring object presents one entity that can be monitored - 
 * host in most cases. Any host can have list of monitoring parameters
 * called counters, for e.g. one counter for CPU load, one for memory, etc.
 * 
 * @author Sergey
 */
public interface MonitoringObject extends MonitoredEntity {
	public State getState(); 
}
