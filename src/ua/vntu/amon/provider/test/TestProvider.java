package ua.vntu.amon.provider.test;

import java.util.ArrayList;
import java.util.List;

import ua.vntu.amon.provider.Counter;
import ua.vntu.amon.provider.DefaultGroup;
import ua.vntu.amon.provider.DefaultObject;
import ua.vntu.amon.provider.MonitoringGroup;
import ua.vntu.amon.provider.MonitoringObject;
import ua.vntu.amon.provider.Provider;
import ua.vntu.amon.provider.State;

/**
 * Test implementation of monitoring provider.
 * @author Sergey
 */
public class TestProvider implements Provider {

	public TestProvider() {}
	
	public List<MonitoringGroup> getGroups() {
		List<MonitoringGroup> list = new ArrayList<MonitoringGroup>();
		list.add(new DefaultGroup("1", "test group 1"));
		list.add(new DefaultGroup("2", "test group 2"));
		return list;
	}

	public List<MonitoringObject> getObjects(MonitoringGroup group) {
		List<MonitoringObject> list = new ArrayList<MonitoringObject>();
		list.add(new DefaultObject("1", "host1"));
		list.add(new DefaultObject("2", "host2", State.OFF));
		list.add(new DefaultObject("2", "host2", State.DISABLED));
		return list;
	}
	
	public List<Counter> getCounters(MonitoringObject group) {
		return null;
	}
}
