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
 * $Id: BuffaloInvokerTest.java,v 1.7 2006/10/01 16:10:49 mechiland Exp $
 */ 
package net.buffalo.service.invoker;

import java.io.StringReader;
import java.io.StringWriter;

import net.buffalo.service.ServiceInvocationException;

import junit.framework.TestCase;

public class BuffaloInvokerTest extends TestCase {
	
	public void testShouldInvokeSimple() throws Exception {
		String buffaloCall = "<buffalo-call>" +
				"<method>sum</method>" +
				"<int>1</int>" +
				"<int>1</int></buffalo-call>";
		StringReader reader = new StringReader(buffaloCall);
		StringWriter writer = new StringWriter();
		BuffaloInvoker.getInstance().invoke(new DummyClass(), reader, writer);
		String buffaloReply = "<buffalo-reply><int>2</int></buffalo-reply>";
		assertEquals(buffaloReply, writer.getBuffer().toString());
	}
	
	public void testExceptionInvoke() throws Exception {
		String buffaloCall = "<buffalo-call><method>ex</method></buffalo-call>";
		StringReader reader = new StringReader(buffaloCall);
		StringWriter writer = new StringWriter();
		BuffaloInvoker.getInstance().invoke(new DummyClass(), reader, writer);
		assertTrue(writer.getBuffer().toString().indexOf("java.lang.RuntimeException") != -1);
		assertTrue(writer.getBuffer().toString().indexOf("just throw out") != -1);
	}
	
	public void testCallVoidFunc() throws Exception {
		String buffaloCall = "<buffalo-call><method>voidFunc</method></buffalo-call>";
		StringReader reader = new StringReader(buffaloCall);
		StringWriter writer = new StringWriter();
		BuffaloInvoker.getInstance().invoke(new DummyClass(), reader, writer);
		String buffaloReply = "<buffalo-reply><null></null></buffalo-reply>";
		assertEquals(buffaloReply, writer.getBuffer().toString());
	}
	
	public void testNumberOverload() throws Exception {
		String buffaloCall = "<buffalo-call><method>sum</method><int>1</int><int>2</int></buffalo-call>";
		StringReader reader = new StringReader(buffaloCall);
		StringWriter writer = new StringWriter();
		BuffaloInvoker.getInstance().invoke(new DummyClass2(), reader, writer);
		String buffaloReply = "<buffalo-reply><double>3.0</double></buffalo-reply>";
		assertEquals(buffaloReply, writer.getBuffer().toString());
	}
	
	public void testParentClassMethod() throws Exception {
		// inhrenced method
		String buffaloCall = "<buffalo-call><method>sum</method><double>1</double><double>2</double></buffalo-call>";
		StringReader reader = new StringReader(buffaloCall);
		StringWriter writer = new StringWriter();
		BuffaloInvoker.getInstance().invoke(new DummySubClass(), reader, writer);
		String buffaloReply = "<buffalo-reply><double>3.0</double></buffalo-reply>";
		assertEquals(buffaloReply, writer.getBuffer().toString());
		
		// override method
		buffaloCall = "<buffalo-call><method>sum</method><int>1</int><int>2</int></buffalo-call>";
		reader = new StringReader(buffaloCall);
		writer = new StringWriter();
		BuffaloInvoker.getInstance().invoke(new DummySubClass(), reader, writer);
		buffaloReply = "<buffalo-reply><int>103</int></buffalo-reply>";
		assertEquals(buffaloReply, writer.getBuffer().toString());
	}
	
	public void testNoSuchMethod() throws Exception {
		try {
			String buffaloCall = "<buffalo-call><method>lock</method><string>door</string><double>123</double></buffalo-call>";
			StringReader reader = new StringReader(buffaloCall);
			StringWriter writer = new StringWriter();
			BuffaloInvoker.getInstance().invoke(new DummySubClass(), reader, writer);
			
			fail();
		} catch (ServiceInvocationException ex) {
			assertTrue(ex.getMessage().indexOf("lock") > 0);
			assertTrue(ex.getMessage().indexOf("java.lang.String") > 0);
			assertTrue(ex.getMessage().indexOf("java.lang.Double") > 0);
		}
	}
	
}

