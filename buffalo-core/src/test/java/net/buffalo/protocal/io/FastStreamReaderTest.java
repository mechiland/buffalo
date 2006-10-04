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
 * $Id: FastStreamReaderTest.java,v 1.1 2006/10/01 13:58:24 mechiland Exp $
 */ 
package net.buffalo.protocal.io;

import java.io.StringReader;

import junit.framework.TestCase;

public class FastStreamReaderTest extends TestCase {
	public void testFastStreamReader() throws Exception {
		StringReader reader = new StringReader("<buffalo-call>" +
				"<method>sum</method><int>1</int><int>2</int></buffalo-call>");
		FastStreamReader streamReader = new FastStreamReader(reader);
		assertEquals("buffalo-call",streamReader.getNodeName());
		streamReader.moveDown();
		assertEquals("method",streamReader.getNodeName());
		assertEquals("sum", streamReader.getValue());
		streamReader.moveUp();
		streamReader.moveDown();
		assertEquals("int", streamReader.getNodeName());
		assertEquals("1", streamReader.getValue());
		
		streamReader.moveUp();
		assertEquals("buffalo-call", streamReader.getNodeName());
		streamReader.moveDown();
		assertEquals("int", streamReader.getNodeName());
		assertEquals("2", streamReader.getValue());
		
	}
	
	public void testShoudReadNested() throws Exception {
		StringReader reader = new StringReader("<buffalo-call>"
				+ "<method>sum</method><int>1</int>" +
						"<map>" +
						"<type>java.util.HashMap</type>" +
						"<string>name</string>" +
						"<string>test</string>" +
						"</map>" +
						"</buffalo-call>");
		FastStreamReader streamReader = new FastStreamReader(reader);
		assertEquals("buffalo-call", streamReader.getNodeName());
		streamReader.moveDown();
		assertEquals("method", streamReader.getNodeName());
		assertEquals("sum", streamReader.getValue());
		streamReader.moveUp();
		streamReader.moveDown();
		assertEquals("int", streamReader.getNodeName());
		assertEquals("1", streamReader.getValue());
		
		streamReader.moveUp();
		streamReader.moveDown();
		assertEquals("map", streamReader.getNodeName());
		streamReader.moveDown();
		assertEquals("type", streamReader.getNodeName());
		assertEquals("java.util.HashMap", streamReader.getValue());
		streamReader.moveUp();
		streamReader.moveDown();
		assertEquals("string", streamReader.getNodeName());
		assertEquals("name", streamReader.getValue());
		
		streamReader.moveUp();
		streamReader.moveDown();
		assertEquals("string", streamReader.getNodeName());
		assertEquals("test", streamReader.getValue());
		
		streamReader.moveUp();
		streamReader.moveUp();
	}
	
	public void testPrimitive() throws Exception {
		simpleValueTest("boolean", "1");
		simpleValueTest("long", "1");
		simpleValueTest("double", "1");
		simpleValueTest("date", "20050908T122321Z");
		simpleValueTest("ref", "1");
	}
	
	public void testNull() throws Exception {
		StringReader reader = new StringReader("<null></null>");
		FastStreamReader streamReader = new FastStreamReader(reader);
		assertEquals("null", streamReader.getNodeName());
	}
	
	public void simpleValueTest(String tag, String value) {
		StringReader reader = new StringReader("<"+tag+">"+value+"</"+tag+">");
		FastStreamReader streamReader = new FastStreamReader(reader);
		assertEquals(tag, streamReader.getNodeName());
		assertEquals(value, streamReader.getValue());
		
	}
	
	public void testList() {
		StringReader reader = new StringReader(
						"<list>" +
						"<type>java.util.ArrayList</type>" +
						"<length>2</length>" +
						"<string>name</string>" +
						"<string>test</string>" +
						"</list>");
		FastStreamReader streamReader = new FastStreamReader(reader);
		assertEquals("list", streamReader.getNodeName());
		streamReader.moveDown();
		assertEquals("type", streamReader.getNodeName());
		assertEquals("java.util.ArrayList", streamReader.getValue());
		
		streamReader.moveUp();
		streamReader.moveDown();
		assertEquals("length", streamReader.getNodeName());
		assertEquals("2", streamReader.getValue());
		
		streamReader.moveUp();
		streamReader.moveDown();
		assertEquals("string", streamReader.getNodeName());
		assertEquals("name", streamReader.getValue());
		
		streamReader.moveUp();
		
		assertEquals("list", streamReader.getNodeName());
		
		streamReader.moveDown();
		assertEquals("string", streamReader.getNodeName());
		assertEquals("test", streamReader.getValue());
		
		streamReader.moveUp();
	}
	
	
	public void testHasMoreChildren() throws Exception {
		StringReader reader = new StringReader("<buffalo-call>"
				+ "<method>sum</method>" +
						"<int>1</int>" +
						"<map>" +
						"<type>type</type>" +
						"<string>name</string>" +
						"<list>" +
						"<type>list</type>" +
						"<length>3</length>" +
						"<string>a</string>" +
						"<string>b</string>" +
						"<string>c</string>" +
						"</list>" +
						"</map>" +
						"</buffalo-call>");
		FastStreamReader streamReader = new FastStreamReader(reader);
		assertTrue(streamReader.hasMoreChildren());
		streamReader.moveDown();
		assertFalse(streamReader.hasMoreChildren());
		streamReader.moveUp();
		
		streamReader.moveDown();
		streamReader.moveUp();
		streamReader.moveDown();
		
		assertEquals("map",streamReader.getNodeName());
		assertTrue(streamReader.hasMoreChildren());
		
		streamReader.moveDown();
		streamReader.moveUp();

		streamReader.moveDown();
		streamReader.moveUp();
		
		streamReader.moveDown();
		assertTrue(streamReader.hasMoreChildren());
		assertEquals("list",streamReader.getNodeName());
		
		while(streamReader.hasMoreChildren()) {
			streamReader.moveDown();
			streamReader.moveUp();
		}
		
		streamReader.moveUp();
		assertEquals("map",streamReader.getNodeName());
		
		assertFalse(streamReader.hasMoreChildren());
	}
	
	public void testIntegrationTest() throws Exception {
		StringReader reader = new StringReader("<buffalo-call>"
				+ "<method>sum</method><int>1</int>" +
						"<map>" +
						"<type>java.util.HashMap</type>" +
						"<string>name</string>" +
						"<string>test</string>" +
						"</map>" +
						"</buffalo-call>");
		FastStreamReader streamReader = new FastStreamReader(reader);
		travelReader(streamReader,0);
	}
	
	private void travelReader(FastStreamReader reader, int depth) {
		String empthstr = "";
		for (int i = 0; i < depth; i++) {
			empthstr += "  ";
		}
		System.out.println(empthstr+reader.getNodeName()+"="+reader.getValue());
		depth++;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			travelReader(reader, depth);
			reader.moveUp();
		} 
		depth--;
	}
	
}
