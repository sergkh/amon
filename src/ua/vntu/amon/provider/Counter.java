package ua.vntu.amon.provider;

import java.util.Date;
import java.util.List;

/**
 * Counter is one monitored parameter, for example CPU, memory
 * disk i/o etc. The monitor displays information got from counter*s
 * 
 * @author Sergey
 */
public interface Counter extends MonitoredEntity {
	public Integer getMax();
	public Integer getMin();
	public Integer getAverage();
	
	public List<Integer> getValues(Date since, Date until);
	
	public Date getMinDate();
	public Date getMaxDate();
}
