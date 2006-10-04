package net.buffalo.protocal.acceptance;

import net.buffalo.protocal.BuffaloProtocal;
import junit.framework.TestCase;

public class CalculatorOutputTest extends TestCase {
	
	public void testCalcSum() throws Exception {
		Calculator c = new Calculator();
		double sum = c.sum(1.0, 2.0);
		assertEquals("<buffalo-reply><double>3.0</double></buffalo-reply>", 
				BuffaloProtocal.getInstance().serialize(new Double(sum)));
	}
	
	public void testCalcMinus() throws Exception {
		Calculator c = new Calculator();
		double value = c.minus(1.0, 2.0);
		assertEquals("<buffalo-reply><double>-1.0</double></buffalo-reply>", 
				BuffaloProtocal.getInstance().serialize(new Double(value)));
	}
	
	public void testNullResult() throws Exception {
		assertEquals("<buffalo-reply><null></null></buffalo-reply>", BuffaloProtocal.getInstance().serialize(null));
	}
	
}


