package net.buffalo.protocal;

import junit.framework.TestCase;

public class BuffaloCallTest extends TestCase {
	public void testToString() throws Exception {
		BuffaloCall call = new BuffaloCall("method", new Object[0]);
		assertEquals("method()", call.toString());
		
		call = new BuffaloCall("method", new Object[]{new Long(1), "str"});
		assertEquals("method(1,str)", call.toString());
	}
}
