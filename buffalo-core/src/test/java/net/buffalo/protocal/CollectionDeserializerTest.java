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
 * $Id: CollectionDeserializerTest.java,v 1.5 2006/09/30 04:38:10 mechiland Exp $
 */ 
package net.buffalo.protocal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.collection.CollectionConverter;
import net.buffalo.protocal.io.DefaultUnmarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.UnmarshallingContext;

public class CollectionDeserializerTest extends TestCase {
	
	private CollectionConverter cd;
	private DefaultConverterLookup converterLookup;	
	UnmarshallingContext unmarshallingContext;
	protected void setUp() throws Exception {
		super.setUp();
		cd = new CollectionConverter();
		converterLookup = new DefaultConverterLookup();
	}

	public void testCouldGetArrayList() throws Exception {
		String string = "<list>" +
				"<type>java.util.ArrayList</type>" +
				"<length>2</length>" +
				"<string>hello</string>" +
				"<string>world</string>" +
				"</list>";
		
		Collection col = (Collection) unmarshal(string);
		assertEquals(2, col.size());
		assertTrue(col instanceof ArrayList);
		assertEquals("hello", col.iterator().next());
	}
	
	public void testCouldGetIntegerLinkedList() throws Exception {
		String string = "<list>" +
		"<type>java.util.LinkedList</type>" +
		"<length>2</length>" +
		"<int>100</int>" +
		"<int>201</int>" +
		"</list>";
		
		Collection col = (Collection)unmarshal(string);
		
		assertEquals(2, col.size());
		assertTrue(col instanceof LinkedList);
		assertEquals(new Integer(100), col.iterator().next());
	}
	
	public void testMixedTypes() throws Exception {
		String string = "<list>" +
		"<type>java.util.HashSet</type>" +
		"<length>2</length>" +
		"<int>100</int>" +
		"<string>IamString</string>" +
		"</list>";
		
		Collection col = (Collection)unmarshal(string);
		assertEquals(2, col.size());
		assertTrue(col instanceof HashSet);
		Iterator iterator = col.iterator();
		assertEquals(new Integer(100), iterator.next());
		assertEquals("IamString", iterator.next());
	}
	
	public void testCouldGetImplictType() throws Exception {
		String string = "<list>" +
		"<type></type>" +
		"<length>0</length>" +
		"</list>";
		
		Collection col = (Collection)unmarshal(string);
		assertTrue(col instanceof ArrayList);
	}
	
	public void testShouldGetNestedList() throws Exception {
		String str = "<list>" + "<type></type>" + "<length>2</length>"
		+ "<list><type></type>" + "<length>2</length>"
		+ "<string>a</string>" + "<string>b</string>" + "</list><list>"
		+ "<type></type>" + "<length>2</length>"
		+ "<string>c</string>" + "<string>d</string>"
		+ "</list></list>";
		
		ArrayList list =(ArrayList) unmarshal(str);
		assertEquals(2, list.size());
		assertEquals(2, ((List)list.get(0)).size());
		assertEquals(3, unmarshallingContext.getObjects().size());
		assertTrue(unmarshallingContext.getObjects().get(0) instanceof Collection);
		assertTrue(unmarshallingContext.getObjects().get(1) instanceof Collection);
		assertTrue(unmarshallingContext.getObjects().get(2) instanceof Collection);
	}
	
	
	public void xtestCouldGetArray() throws Exception {
		String string = "<list>" +
		"<type>[string</type>" +
		"<length>2</length>" +
		"<string>hello</string>" +
		"<string>world</string>" +
		"</list>";
		
		Object[] strarr = (Object[])unmarshal(string);
		assertEquals(2, strarr.length);
		
		string = "<list>" +
		"<type>[int</type>" +
		"<length>2</length>" +
		"<int>100</int>" +
		"<int>200</int>" +
		"</list>";

		strarr=(Object[]) unmarshal(string);
		assertEquals(2, strarr.length);
	}
	
	public void testShouldDealWithNestedArray() throws Exception {
		String str = "<list>" + "<type>[[string</type>" + "<length>2</length>"
		+ "<list><type>[string</type>" + "<length>2</length>"
		+ "<string>a</string>" + "<string>b</string>" + "</list><list>"
		+ "<type>[string</type>" + "<length>2</length>"
		+ "<string>c</string>" + "<string>d</string>"
		+ "</list></list>";
		
		String[][] array = (String[][]) unmarshal(str);
		
		assertEquals(2, array.length);
		assertEquals(2, array[0].length);
		
	}
	
	private Object unmarshal(String source) {
		StreamReader streamReader = TestUtils.createStreamReader(source);
		unmarshallingContext = new DefaultUnmarshallingContext(converterLookup, streamReader);
		return cd.unmarshal(streamReader, unmarshallingContext);
	}
}













