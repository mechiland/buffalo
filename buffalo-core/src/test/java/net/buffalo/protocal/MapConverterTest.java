package net.buffalo.protocal;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.map.MapConverter;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.DefaultUnmarshallingContext;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.UnmarshallingContext;

public class MapConverterTest extends TestCase {
	
	private MarshallingContext mashallingContext;
	private MapConverter converter;
	private UnmarshallingContext unmashallingContext;
	private StringWriter stringWriter;
	private FastStreamWriter streamWriter;
	private DefaultConverterLookup converterLookup;

	protected void setUp() throws Exception {
		super.setUp();
		converter = new MapConverter();
		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		converterLookup = new DefaultConverterLookup();
		mashallingContext = new DefaultMarshallingContext(converterLookup, streamWriter);
	}
	
	public void testShouldSerializeMap() throws Exception {
		MapConverter ms = new MapConverter();
		assertTrue(ms.canConvert(HashMap.class));
		assertFalse(ms.canConvert(null));
		
		Map m = new HashMap();
		m.put("string", "value");
		ms.marshal(m, mashallingContext, streamWriter);
		assertEquals("<map>" +
				"<type>java.util.HashMap</type>" +
				"<string>string</string>" +
				"<string>value</string>" +
				"</map>", stringWriter.getBuffer().toString());
	}
	
	public void testNestedMapElement() throws Exception {
		Map m = new HashMap();
		m.put(new Integer(100), "integer value");
		Map m2 = new TreeMap();
		m2.put("somekey", new ArrayList());
		m.put("nestedMap", m2);
		
		converter.marshal(m, mashallingContext, streamWriter);
		String string = "<map>" +
						"<type>java.util.HashMap</type>" +
						"<int>100</int>" +
						"<string>integer value</string>" +
						"<string>nestedMap</string>" +
						"<map><type>java.util.TreeMap</type>" +
						"<string>somekey</string>" +
						"<list><type>java.util.ArrayList</type>" +
						"<length>0</length>" +
						"</list></map></map>";
		String toString = stringWriter.getBuffer().toString();
	
		assertEquals(string, toString);
		
		StreamReader streamReader = TestUtils.createStreamReader(string);
		unmashallingContext = new DefaultUnmarshallingContext(converterLookup, streamReader);
		Map map = (Map) converter.unmarshal(streamReader, unmashallingContext);
		
		assertNotNull(map);
		assertEquals("integer value", map.get(new Integer(100)));
		Map nestedMap = (Map) map.get("nestedMap");
		assertEquals(TreeMap.class, nestedMap.getClass());
		assertEquals(ArrayList.class, nestedMap.get("somekey").getClass());
		
		assertEquals(3, unmashallingContext.getObjects().size());
		assertEquals(HashMap.class, unmashallingContext.getObjects().get(0).getClass());
		assertEquals(TreeMap.class, unmashallingContext.getObjects().get(1).getClass());
		assertEquals(ArrayList.class, unmashallingContext.getObjects().get(2).getClass());
	
	}
	
	public void testCouldConvertMap() throws Exception {
		
		String mapstr = "<map>" +
		"<type>java.util.HashMap</type>" +
		"<string>string</string>" +
		"<string>value</string>" +
		"</map>";
		
		StreamReader streamReader = TestUtils.createStreamReader(mapstr);
		unmashallingContext = new DefaultUnmarshallingContext(converterLookup, streamReader);
		HashMap map = (HashMap) converter.unmarshal(streamReader, unmashallingContext);
		assertNotNull(map);
		assertEquals("value", map.get("string"));
	}
	
	public void testCouldConvertToMapWithoutType() throws Exception {
		String mapstr = "<map>" +
		"<type></type>" +
		"<string>string</string>" +
		"<string>value</string>" +
		"</map>";
		
		StreamReader streamReader = TestUtils.createStreamReader(mapstr);
		unmashallingContext = new DefaultUnmarshallingContext(converterLookup, streamReader);
		HashMap map = (HashMap) converter.unmarshal(streamReader, unmashallingContext);
		assertNotNull(map);
		
		assertEquals("value", map.get("string"));
	}
	
	public void testCanDeserializeSqlDate() throws Exception {
		String str = "<map><type>java.sql.Date</type><string>value</string><date>20061001T000000Z</date></map>";
		StreamReader streamReader = TestUtils.createStreamReader(str);
		unmashallingContext = new DefaultUnmarshallingContext(converterLookup, streamReader);
		
		java.sql.Date date = (java.sql.Date)converter.unmarshal(streamReader, unmashallingContext);
		assertNotNull(date);
		
		assertEquals(1, unmashallingContext.getObjects().size());
	}
	
	public void testCanDeserializePeople() throws Exception {

		String string = "<map>" +
						"<type>net.buffalo.protocal.People</type>" +
						"<string>name</string>" +
						"<null></null>" +
						"<string>age</string>" +
						"<int>0</int>" +
						"<string>sex</string>" +
						"<boolean>0</boolean>" +
						"<string>friend</string>" +
						"<null></null>" +
						"</map>";
		
		StreamReader streamReader = TestUtils.createStreamReader(string);
		unmashallingContext = new DefaultUnmarshallingContext(converterLookup, streamReader);
		
		People people = (People)converter.unmarshal(streamReader, unmashallingContext);
		assertNotNull(people);
		assertNull(people.getName());
		assertEquals(0, people.getAge());
		assertEquals(1, unmashallingContext.getObjects().size());
	}
	
	public void testWithParentClass() throws Exception {
		String string = "<map>" +
				"<type>net.buffalo.protocal.BirthdayPeople</type>" +
				"<string>birthday</string>" +
				"<date>20060707T220800Z</date>" +
				"<string>name</string>" +
				"<null></null>" +
				"<string>age</string>" +
				"<int>0</int>" +
				"<string>sex</string>" +
				"<boolean>0</boolean>" +
				"<string>friend</string>" +
				"<null></null></map>";
		StreamReader streamReader = TestUtils.createStreamReader(string);
		unmashallingContext = new DefaultUnmarshallingContext(converterLookup, streamReader);
		
		BirthdayPeople people = (BirthdayPeople)converter.unmarshal(streamReader, unmashallingContext);
		assertNotNull(people);
		assertNull(people.getName());
		assertEquals(0, people.getAge());
		assertEquals(2006, people.getBirthday().getYear()+1900);
	}
	
	
}
