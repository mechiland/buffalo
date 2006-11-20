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
 * $Id: ClassUtilTest.java,v 1.1 2006/10/01 13:58:18 mechiland Exp $
 */ 
package net.buffalo.protocal.util;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.buffalo.protocal.People;
import net.buffalo.protocal.acceptance.Calculator;

public class ClassUtilTest extends TestCase {
	public void testShouldSetFieldValue() throws Exception {
		People p = new People();
		People p2 = new People();
		ClassUtil.setFieldValue(p, "name", "Michael Chen");
		ClassUtil.setFieldValue(p, "friend", p2);
		assertEquals("Michael Chen", p.getName());
		assertEquals(p2, p.getFriend());
	}
	
	public void testCanConvertStringToNumber() throws Exception {
		String value = "100";
		Object output = ClassUtil.convertValue(value, Integer.class);
		assertEquals(new Integer(value), output);
		output = ClassUtil.convertValue(value, int.class);
		assertEquals(new Integer(value), output);
		output = ClassUtil.convertValue(value, Float.class);
		assertEquals(new Float(value), output);
		output = ClassUtil.convertValue(value, Double.class);
		assertEquals(new Double(value), output);
		output = ClassUtil.convertValue(value, Short.class);
		assertEquals(new Short(value), output);
		output = ClassUtil.convertValue(value, Byte.class);
		assertEquals(new Byte(value), output);
		
		value = "100.1";
		try {
			output = ClassUtil.convertValue(value, Integer.class);
			fail();
		} catch (RuntimeException ex) {
			assertEquals(NumberFormatException.class, ex.getCause().getCause().getClass());
		}
		
	}
	
	public void testCanInvokeNumberInsteadOfConcreteNumber() throws Exception {
		Method method = Calculator.class.getMethod("longSum", new Class[]{Long.class, Long.class});
		Object result = ClassUtil.invokeMethod(new Calculator(), method, new Object[]{new Integer(1), new Integer(2)});
		assertEquals(new Long(3), result);
	}
	
	public void testGetPrimitiveType() throws Exception {
		assertEquals(Boolean.class, ClassUtil.getWrapperClass(boolean.class));
		assertEquals(Byte.class, ClassUtil.getWrapperClass(byte.class));
		assertEquals(Short.class, ClassUtil.getWrapperClass(short.class));
		assertEquals(Integer.class, ClassUtil.getWrapperClass(int.class));
		assertEquals(Long.class, ClassUtil.getWrapperClass(long.class));
		assertEquals(Float.class, ClassUtil.getWrapperClass(float.class));
		assertEquals(Double.class, ClassUtil.getWrapperClass(double.class));
	}
	
}
