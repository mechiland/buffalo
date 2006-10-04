package net.buffalo.protocal;

import java.io.StringWriter;
import java.util.Date;

import junit.framework.TestCase;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.map.ObjectConverter;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;

public class ObjectSerializerTest extends TestCase {
	
	private MarshallingContext context;
	private StringWriter stringWriter;
	private FastStreamWriter streamWriter;

	protected void setUp() throws Exception {
		super.setUp();

		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		context = new DefaultMarshallingContext(new DefaultConverterLookup(), streamWriter);
	}
	
	public void testShouldSerializeObject() throws Exception {
		ObjectConverter os = new ObjectConverter();
		assertFalse(os.canConvert(null));
		People people = new People();
		assertTrue(os.canConvert(People.class));
		
		os.marshal(people, context, streamWriter);
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
		assertEquals(string, stringWriter.getBuffer().toString());
		
	}
	
	public void testShouldSerializeParentClass() throws Exception {
		ObjectConverter os = new ObjectConverter();
		BirthdayPeople people = new BirthdayPeople();
		people.setBirthday(new Date());
		assertTrue(os.canConvert(BirthdayPeople.class));
		os.marshal(people, context, streamWriter);
		String output = stringWriter.getBuffer().toString();
		assertTrue(output.indexOf("date")>-1);
		assertTrue(output.indexOf("name") > -1);
		assertEquals(1, context.getObjects().size());
	}
	
	public void testSqlDate() throws Exception {
		java.util.Date today = new java.util.Date(2006,10,1);
		java.sql.Date sDate = new java.sql.Date(today.getTime());
		ObjectConverter os = new ObjectConverter();
		os.marshal(sDate, context, streamWriter);
		System.out.println(stringWriter.getBuffer().toString());
	}
	
}
