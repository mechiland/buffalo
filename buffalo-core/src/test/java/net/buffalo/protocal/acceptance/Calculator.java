package net.buffalo.protocal.acceptance;

public class Calculator {
	public double sum(double d1, double d2) {
		return d1+d2;		
	}

	public double minus(double d, double e) {
		return d - e;
	}
	
	public double[] test() {
		return new double[]{1.1, 2.2};		
	}
	
	public Object nullResult() {
		return null;
	}
	
	public Long longSum(Long long1, Long long2) {
		return new Long(long1.longValue() + long2.longValue());
	}
	
}
