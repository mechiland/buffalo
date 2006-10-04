package net.buffalo.protocal;

import java.io.StringWriter;

import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.collection.ArrayConverter;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;
import junit.framework.TestCase;

public class ArraySerializerTest extends TestCase {

	private MarshallingContext context;

	private ArrayConverter as;

	private StringWriter stringWriter;

	private FastStreamWriter streamWriter;

	protected void setUp() throws Exception {
		super.setUp();
		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		context = new DefaultMarshallingContext(new DefaultConverterLookup(), streamWriter);
		as = new ArrayConverter();
	}

	public void testShouldSerializeArray() throws Exception {

		assertTrue(as.canConvert(Integer[].class));
		assertFalse(as.canConvert(null));
		assertFalse(as.canConvert(String.class));

		String[] value = { "a", "b", "c" };
		as.marshal(value, context, streamWriter);
		assertEquals("<list>" + "<type>[string</type>" + "<length>3</length>"
				+ "<string>a</string>" + "<string>b</string>"
				+ "<string>c</string>" + "</list>", stringWriter.getBuffer().toString());
	}

	public void testNestedArray() throws Exception {
		String[][] value = { { "a", "b" }, { "c", "d" } };
		as.marshal(value, context, streamWriter);
		assertEquals("<list>" + "<type>[[string</type>" + "<length>2</length>"
				+ "<list><type>[string</type>" + "<length>2</length>"
				+ "<string>a</string>" + "<string>b</string>" + "</list><list>"
				+ "<type>[string</type>" + "<length>2</length>"
				+ "<string>c</string>" + "<string>d</string>"
				+ "</list></list>", stringWriter.getBuffer().toString());
	}

	public void testShouldOutputIntLongAndDoubleArrays() throws Exception {
		int[] ivalues = new int[] { 100 };
		short[] svalues = new short[] { 10 };

		as.marshal(ivalues, context, streamWriter);
		
		
		assertTrue(stringWriter.getBuffer().toString().indexOf("[short") == -1);
		
		as.marshal(svalues, context, streamWriter);
		
		assertTrue(stringWriter.getBuffer().toString().indexOf("[byte") == -1);
		
		float[] fvalues = new float[] { 1.1f, 2.2f };
		double[] dvalues = new double[] { 1.11d, 2.22d };
		as.marshal(fvalues, context, streamWriter);
		as.marshal(dvalues, context, streamWriter);
		assertTrue(stringWriter.getBuffer().toString().indexOf("[float") == -1);

		boolean[] boolValues = new boolean[] { true, false };
		as.marshal(boolValues, context, streamWriter);
	}
	
	public void testList() throws Exception {
		long[] lvalues = new long[] { 1001 };
		StringWriter stringWriter2 = new StringWriter();
		FastStreamWriter SimpleStreamWriter = new FastStreamWriter(stringWriter2);
		new ArrayConverter().marshal(lvalues, 
				new DefaultMarshallingContext(new DefaultConverterLookup(), SimpleStreamWriter), 
				SimpleStreamWriter);
		assertEquals("<list><type>[long</type><length>1</length><long>1001</long></list>",
				stringWriter2.getBuffer().toString());
	}

	public void testShouldNotOutputTheWrapperClassName() throws Exception {
		as.marshal(new Integer[0], context, streamWriter);
		assertTrue(stringWriter.getBuffer().toString().indexOf("java.lang.Integer") == -1);
		as.marshal(new Byte[0], context, streamWriter);
		assertTrue(stringWriter.getBuffer().toString().indexOf("java.lang.Byte") == -1);
		as.marshal(new Short[0], context, streamWriter);
		assertTrue(stringWriter.getBuffer().toString().indexOf("java.lang.Short") == -1);
		as.marshal(new Float[0], context, streamWriter);
		assertTrue(stringWriter.getBuffer().toString().indexOf("java.lang.Float") == -1);
		as.marshal(new Long[0], context, streamWriter);
		assertTrue(stringWriter.getBuffer().toString().indexOf("java.lang.Long") == -1);
		as.marshal(new Boolean[0], context, streamWriter);
		assertTrue(stringWriter.getBuffer().toString().indexOf("java.lang.Boolean") == -1);
		as.marshal(new Double[0], context, streamWriter);		
		assertTrue(stringWriter.getBuffer().toString().indexOf("java.lang.Double") == -1);
	}
	
	public void testNestedPrimitiveArray() throws Exception {
		int[][] values = new int[][]{
				{1,2},
				{3,4}
		};
		
		as.marshal(values, context, streamWriter);
		
		assertEquals("<list>" +
				"<type>[[int</type>" +
				"<length>2</length>" +
				"<list>" +
				"<type>[int</type>" +
				"<length>2</length>" +
				"<int>1</int>" +
				"<int>2</int>" +
				"</list><list>" +
				"<type>[int</type>" +
				"<length>2</length>" +
				"<int>3</int>" +
				"<int>4</int>" +
				"</list></list>", stringWriter.getBuffer().toString());
		
	}

}
