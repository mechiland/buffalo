package net.buffalo.protocal;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import net.buffalo.protocal.acceptance.Calculator;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.basic.BooleanConverter;
import net.buffalo.protocal.converters.basic.DateConverter;
import net.buffalo.protocal.converters.basic.DoubleConverter;
import net.buffalo.protocal.converters.basic.IntegerConverter;
import net.buffalo.protocal.converters.basic.LongConverter;
import net.buffalo.protocal.converters.basic.NullConverter;
import net.buffalo.protocal.converters.basic.StringConverter;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;

public class PrimitiveTypeSerializerTest extends TestCase {
	
	private StringWriter stringWriter;
	private FastStreamWriter streamWriter;
	private MarshallingContext mashallingContext;

	protected void setUp() throws Exception {
		super.setUp();
		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		mashallingContext = new DefaultMarshallingContext(new DefaultConverterLookup(), streamWriter);
	}
	
	private String marshal(Converter converter, Object obj) {
		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		converter.marshal(obj, mashallingContext, streamWriter);
		String toString = stringWriter.getBuffer().toString();
		return toString;
	}
	
	public void testShouldOutputInt() {
		IntegerConverter serializer = new IntegerConverter();
		assertEquals("<int>123</int>", marshal(new IntegerConverter(), new Integer(123)));
		assertEquals("<int>234</int>", marshal(new IntegerConverter(), new Integer(234)));
		assertTrue(serializer.canConvert(Integer.class));
		assertFalse(serializer.canConvert(Long.class));
	}
	
	public void testShouldOutputShortAndByteToInt() {
		IntegerConverter serializer = new IntegerConverter();
		assertTrue(serializer.canConvert(Short.class));
		assertTrue(serializer.canConvert(Byte.class));
	}
	
	public void testShouldOutputBoolean() throws Exception {
		Converter serializer = new BooleanConverter();
		assertEquals("<boolean>1</boolean>", marshal(serializer, Boolean.TRUE));
		assertEquals("<boolean>0</boolean>", marshal(serializer, Boolean.FALSE));
		assertTrue(serializer.canConvert(Boolean.class));
		assertFalse(serializer.canConvert(Long.class));
	}
	
	public void testMethodInvokation() throws Exception {
		Calculator c = new Calculator();
		Method method = c.getClass().getMethod("sum", new Class[]{double.class, double.class});
		Object result = method.invoke(c, new Object[]{new Double(1.0), new Double(2.0)});
		assertEquals("<double>3.0</double>", marshal(new DoubleConverter(),result));
		
		Method method1 = c.getClass().getMethod("test", new Class[]{});
		Object result1 = method1.invoke(c, new Object[]{});
		assertTrue(result1.getClass().isArray());
		
	}
	
	public void testDoubleSerializerCanOutputFloatAndDouble() throws Exception {
		DoubleConverter ds = new DoubleConverter();
		assertTrue(ds.canConvert(Double.class));
		assertTrue(ds.canConvert(Float.class));
	}
	
	public void testShouldOutputLong() {
		LongConverter serializer = new LongConverter();
		assertEquals("<long>123</long>", marshal(serializer, new Long(123)));
		assertEquals("<long>234</long>", marshal(serializer, new Long(234)));
		assertTrue(serializer.canConvert(Long.class));
		assertFalse(serializer.canConvert(String.class));
	}
	
	public void testShouldOutputNull() {
		NullConverter serializer = new NullConverter();
		assertEquals("<null></null>", marshal(serializer, null));
		assertTrue(serializer.canConvert(null));
		assertFalse(serializer.canConvert(ArrayList.class));
	}
	
	public void testShouldOutputString() {
		StringConverter serializer = new StringConverter();
		assertEquals("<string>hello, world</string>", marshal(serializer, "hello, world"));
		assertTrue(serializer.canConvert(String.class));
		assertFalse(serializer.canConvert(null));
	}
	
	public void testShouldEscapeXmlString() {
		StringConverter serializer = new StringConverter();
		assertEquals("<string>a&gt;b; c&lt;d; &amp;this</string>", marshal(serializer, "a>b; c<d; &this"));
		assertTrue(serializer.canConvert(String.class));
		assertFalse(serializer.canConvert(null));
	}
	
	public void testShouldOutputCharacterAsString() throws Exception {
		StringConverter serializer = new StringConverter();
		Character character = new Character('c');
		assertTrue(serializer.canConvert(Character.class));
		assertEquals("<string>c</string>", marshal(serializer, character));
	}
	
	public void testCanOutputDate() throws Exception {
		DateConverter ds = new DateConverter();
		assertTrue(ds.canConvert(Date.class));
		assertFalse(ds.canConvert(null));
		
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 8, 1, 17, 54, 21);
		Date d = cal.getTime();
		assertEquals("<date>20060801T175421Z</date>", marshal(ds, d));
	}
		
}
