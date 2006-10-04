package net.buffalo.protocal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.io.DefaultUnmarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.UnmarshallingContext;

public class ReferenceTest extends TestCase {

	private UnmarshallingContext unmashallingContext;

	protected void setUp() throws Exception {
		super.setUp();
		unmashallingContext = new DefaultUnmarshallingContext(null, null);		
	}
	public void testObjectRef() throws Exception {
		People p1 = new People("1", 1, true);
		People p2 = new People("2", 2, false);
		p1.setFriend(p2);
		p2.setFriend(p1);
		
		List people = new ArrayList();
		people.add(p1);
		people.add(p2);
	}
	
	public void testObjectRefOnly() throws Exception {
		People p1 = new People("1", 1, true);
		People p2 = new People("2", 2, false);
		p1.setFriend(p2);
		p2.setFriend(p1);
		
		assertTrue(Collection.class.isAssignableFrom(ArrayList.class));
	}
	
	public void testRefObjectUnmarshalling() throws Exception {
		String string = "<buffalo-call><list><type>java.util.ArrayList</type><length>2</length>" +
				"<map><type>net.buffalo.protocal.People</type><string>name</string>" +
				"<string>1</string>" +
				"<string>age</string>" +
				"<int>1</int>" +
				"<string>sex</string>" +
				"<boolean>1</boolean>" +
				"<string>friend</string>" +
				"<map><type>net.buffalo.protocal.People</type>" +
				"<string>name</string>" +
				"<string>2</string>" +
				"<string>age</string>" +
				"<int>2</int>" +
				"<string>sex</string>" +
				"<boolean>0</boolean>" +
				"<string>friend</string><ref>1</ref></map></map>" +
				"<ref>2</ref>" +
				"</list></buffalo-call>";
		
		StreamReader streamReader = TestUtils.createStreamReader(string);
		unmashallingContext = new DefaultUnmarshallingContext(new DefaultConverterLookup(), streamReader);
		List list = (List) unmashallingContext.convertAnother();
		assertEquals(2, list.size());
		People p1 = (People) list.get(0);
		People p2 = (People) list.get(1);
		assertEquals(p1, p2.getFriend());
		assertEquals(p2, p1.getFriend());
	}
	
	public void testRefObjectRefPeople() throws Exception {
		String string = "<buffalo-call>" +
				"<map><type>net.buffalo.protocal.People</type>" +
				"<string>name</string><string>1</string>" +
				"<string>age</string><int>1</int>" +
				"<string>sex</string><boolean>1</boolean>" +
				"<string>friend</string>" +
				"<map><type>net.buffalo.protocal.People</type>" +
				"<string>name</string><string>2</string>" +
				"<string>age</string><int>2</int>" +
				"<string>sex</string><boolean>0</boolean>" +
				"<string>friend</string><ref>0</ref></map></map>" +
				"</buffalo-call>";
		
		StreamReader streamReader = TestUtils.createStreamReader(string);
		unmashallingContext = new DefaultUnmarshallingContext(new DefaultConverterLookup(), streamReader);
		People p1 = (People) unmashallingContext.convertAnother();
		People p2 = p1.getFriend();
		assertNotNull(p2);
		assertEquals(p1, p2.getFriend());
		assertEquals(p2, p1.getFriend());
	}
}
