package ua.vntu.amon.provider;

import java.util.List;

/**
 * Monitoring provider describes whole data provider, like Yandex.Metrics or 
 * Zabbix server. Provider can consist of monitoring objects (monitored entities or hosts)
 * grouped in {@link MonitoringGroup}.
 * 
 * @author Sergey
 */
public interface Provider {
	List<MonitoringGroup> getGroups();
	
	List<MonitoringObject> getObjects(MonitoringGroup group);
	
	List<Counter> getCounters(MonitoringObject obj);
}
