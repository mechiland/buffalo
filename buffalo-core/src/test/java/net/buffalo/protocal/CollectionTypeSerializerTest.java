package net.buffalo.protocal;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.collection.CollectionConverter;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;

import junit.framework.TestCase;

public class CollectionTypeSerializerTest extends TestCase {
	private MarshallingContext context;
	private StringWriter stringWriter;
	private FastStreamWriter streamWriter;

	protected void setUp() throws Exception {
		super.setUp();

		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		context = new DefaultMarshallingContext(new DefaultConverterLookup(), streamWriter);
	}
	
	public void testCanApplyList() throws Exception {
		CollectionConverter s = new CollectionConverter();
		assertTrue(s.canConvert(ArrayList.class));
		assertTrue(s.canConvert(LinkedList.class));		
		assertFalse(s.canConvert(String.class));		
	}

	public void testStringList() throws Exception {
		List list = new ArrayList();
		list.add("string1");
		list.add("string2");
		
		CollectionConverter s = new CollectionConverter();
		s.marshal(list, context, streamWriter);
		assertEquals("<list>" +
				"<type>java.util.ArrayList</type>" +
				"<length>2</length>" +
				"<string>string1</string>" +
				"<string>string2</string>" +
				"</list>", stringWriter.getBuffer().toString());
	}
	
	public void testIntegerList() throws Exception {
		List list = new LinkedList();
		list.add(new Integer(100));
		list.add(new Integer(101));
		
		CollectionConverter s = new CollectionConverter();
		s.marshal(list, context, streamWriter);
		assertEquals("<list>" +
				"<type>java.util.LinkedList</type>" +
				"<length>2</length>" +
				"<int>100</int>" +
				"<int>101</int>" +
				"</list>", stringWriter.getBuffer().toString());
	}

	
	public void testComplextList() throws Exception {
		CollectionConverter s = new CollectionConverter();
		
		List list = new ArrayList();
		list.add("abdedefg");
		list.add(Boolean.TRUE);
		list.add(new Integer(100));
		list.add(new Double(9.09));
		s.marshal(list, context, streamWriter);
		assertEquals("<list>" +
				"<type>java.util.ArrayList</type>" +
				"<length>4</length>" +
				"<string>abdedefg</string>" +
				"<boolean>1</boolean>" +
				"<int>100</int>" +
				"<double>9.09</double>" +
				"</list>", stringWriter.getBuffer().toString());
	}
	
	public void testNestedList() throws Exception {
		CollectionConverter s = new CollectionConverter();
		List list2 = new ArrayList();
		list2.add(new Integer(100));
		List list1 = new ArrayList();
		list1.add("man");
		list1.add(list2);
		s.marshal(list1, context, streamWriter);
		assertEquals("<list>" +
				"<type>java.util.ArrayList</type>" +
				"<length>2</length>" +
				"<string>man</string>" +
				"<list>" +
				"<type>java.util.ArrayList</type>" +
				"<length>1</length>" +
				"<int>100</int>" +
				"</list>" +
				"</list>", stringWriter.getBuffer().toString());
	}
}
