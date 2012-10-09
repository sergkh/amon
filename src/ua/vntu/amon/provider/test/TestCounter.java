/**
 * 
 */
package ua.vntu.amon.provider.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ua.vntu.amon.provider.AbstractEntity;
import ua.vntu.amon.provider.Counter;

/**
 * Test counter implementation.
 * @author Sergey
 */
public class TestCounter extends AbstractEntity implements Counter {

	private static final int MAX_VAL= 1000;
	private static final int VALUES_COUNT= 5000;
	
	private List<Integer> data;
	private Date start;
	private Date end;
	private int avg;
	private int max;
	private int min;
	
	public TestCounter(String id, String name) {
		generateData();
	}
	
	private void generateData() {
		Random r = new Random(System.nanoTime());
		
		long now = System.currentTimeMillis();
		start = new Date(now - r.nextInt(10000000));
		end = new Date(start.getTime() - r.nextInt(1000000));
		
		data = new ArrayList<Integer>(VALUES_COUNT);
		
		long sum = 0;
		max = Integer.MIN_VALUE;
		min = Integer.MAX_VALUE;
		
		for(int i=0;i < VALUES_COUNT; i++) {
			int val = r.nextInt(MAX_VAL);
			sum += val;
			if(min > val) min = val;
			if(max < val) max = val;
			
			data.add(val);
		}

		avg = (int) (sum / VALUES_COUNT);
	}

	public Integer getMax() {
		return max;
	}

	public Integer getMin() {
		return min;
	}

	public Integer getAverage() {
		return avg;
	}

	public List<Integer> getValues(Date since, Date until) {
		return null;
	}

	public Date getMinDate() { return start; }

	public Date getMaxDate() { return end; }

}
