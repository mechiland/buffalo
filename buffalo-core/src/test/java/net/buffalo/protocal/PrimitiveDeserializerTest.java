/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id: PrimitiveDeserializerTest.java,v 1.7 2006/10/01 13:58:23 mechiland Exp $
 */ 
package net.buffalo.protocal;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import net.buffalo.protocal.converters.basic.BooleanConverter;
import net.buffalo.protocal.converters.basic.DateConverter;
import net.buffalo.protocal.converters.basic.DoubleConverter;
import net.buffalo.protocal.converters.basic.IntegerConverter;
import net.buffalo.protocal.converters.basic.LongConverter;
import net.buffalo.protocal.converters.basic.NullConverter;
import net.buffalo.protocal.converters.basic.StringConverter;

public class PrimitiveDeserializerTest extends TestCase {
	public void testCanDeserializeBoolean() throws Exception {
		BooleanConverter bd = new BooleanConverter();

		Boolean b = (Boolean)bd.unmarshal(TestUtils.createStreamReader("<boolean>1</boolean>"), null);
		assertTrue(b.booleanValue());
		b = (Boolean)bd.unmarshal(TestUtils.createStreamReader("<boolean>0</boolean>"), null);
		assertFalse(b.booleanValue());
	}
	
	public void testShouldThrowExceptionIfBooleanContainsInvalidString() throws Exception {
		BooleanConverter bd = new BooleanConverter();
		try {
			bd.unmarshal(TestUtils.createStreamReader("<boolean>false</boolean>"), null);
			fail();
		} catch (ProtocolException e) {
			assertNotNull(e);
			assertTrue(e.toString().indexOf("boolean") > -1);
		}
	}
	
	public void testShouldGetString() throws Exception {
		StringConverter sd = new StringConverter();
		String s = (String)sd.unmarshal(TestUtils.createStreamReader("<string>hello, world</string>"), null);
		assertEquals("hello, world", s);
	}
	
	public void testShouldReturnTheOrginalStringFromXmlEscaped() throws Exception {
		StringConverter sd = new StringConverter();
		String s = (String)sd.unmarshal(TestUtils.createStreamReader("<string>&lt;&gt;&amp;HH</string>"), null);
		assertEquals("<>&HH", s);
	}
	
	public void testCouldGetDate() throws Exception {
		DateConverter dd = new DateConverter();
		Date d = (Date)dd.unmarshal(TestUtils.createStreamReader("<date>20060803T090127Z</date>"), null);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		assertEquals(2006, cal.get(Calendar.YEAR));
		assertEquals(27, cal.get(Calendar.SECOND));
	}
	
	public void testChouldGetDouble() throws Exception {
		DoubleConverter nd = new DoubleConverter();
		Double d = (Double)nd.unmarshal(TestUtils.createStreamReader("<double>1.1</double>"), null);
		assertEquals(new Double(1.1d), d);
	}
	
	public void testCouldGetInt() throws Exception {
		IntegerConverter id = new IntegerConverter();
		Integer d = (Integer)id.unmarshal(TestUtils.createStreamReader("<int>1001</int>"), null);
		assertEquals(new Integer(1001), d);
	
	}
	
	public void testCouldGetLong() throws Exception {
		LongConverter id = new LongConverter();
		Long d = (Long)id.unmarshal(TestUtils.createStreamReader("<long>710010098</long>"), null);
		assertEquals(new Long(710010098), d);
	}
	
	public void testCouldGetNull() throws Exception {
		NullConverter nd = new NullConverter();
		assertNull(nd.unmarshal(TestUtils.createStreamReader("<null></null>"), null));
		assertNull(nd.unmarshal(TestUtils.createStreamReader("<null></null>"), null));
	}
	
}
