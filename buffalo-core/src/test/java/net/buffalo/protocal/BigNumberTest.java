package net.buffalo.protocal;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import net.buffalo.protocal.converters.BigNumberConverter;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.DefaultUnmarshallingContext;
import net.buffalo.protocal.io.FastStreamReader;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.UnmarshallingContext;
import junit.framework.TestCase;

public class BigNumberTest extends TestCase {
	
	private StringWriter stringWriter;
	private FastStreamWriter streamWriter;
	private MarshallingContext marshallingContext;
	private UnmarshallingContext unmarshallingContext;
	private DefaultConverterLookup converterLookup;

	protected void setUp() throws Exception {
		super.setUp();
		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		converterLookup = new DefaultConverterLookup();
		marshallingContext = new DefaultMarshallingContext(converterLookup, streamWriter);
		
	}
	
	public void testMarshallingBigDecimal() throws Exception {
		BigDecimal number = new BigDecimal("123.456");
		Converter converter = converterLookup.lookupConverterForType(BigDecimal.class);
		assertEquals(BigNumberConverter.class, converter.getClass());
		marshallingContext.convertAnother(number);
		String str = stringWriter.getBuffer().toString();
		assertEquals("<map><type>java.math.BigDecimal</type>" +
				"<string>value</string>" +
				"<string>123.456</string></map>", str);
	}
	
	public void testMarshallingBigInteger() throws Exception {
		BigInteger number = new BigInteger("123");
		Converter converter = converterLookup.lookupConverterForType(BigInteger.class);
		assertEquals(BigNumberConverter.class, converter.getClass());
		marshallingContext.convertAnother(number);
		String str = stringWriter.getBuffer().toString();
		assertEquals("<map><type>java.math.BigInteger</type>" +
				"<string>value</string>" +
				"<string>123</string></map>", str);
	}
	
	
	public void testUnmarshallingBigDecimal() throws Exception {
		StringReader reader = new StringReader("<buffalo-call><map><type>java.math.BigDecimal</type>" +
				"<string>value</string>" +
				"<string>123.456</string></map></buffalo-call>");
		StreamReader streamReader = new FastStreamReader(reader);
		unmarshallingContext = new DefaultUnmarshallingContext(new DefaultConverterLookup(), streamReader);
		BigDecimal number = (BigDecimal) unmarshallingContext.convertAnother();
		assertEquals(new BigDecimal("123.456"), number);
	}
	
	public void testUnmarshallingBigInteger() throws Exception {
		StringReader reader = new StringReader("<buffalo-call><map><type>java.math.BigInteger</type>" +
				"<string>value</string>" +
				"<string>123</string></map></buffalo-call>");
		StreamReader streamReader = new FastStreamReader(reader);
		unmarshallingContext = new DefaultUnmarshallingContext(new DefaultConverterLookup(), streamReader);
		BigInteger number = (BigInteger) unmarshallingContext.convertAnother();
		assertEquals(new BigInteger("123"), number);
	}
}
