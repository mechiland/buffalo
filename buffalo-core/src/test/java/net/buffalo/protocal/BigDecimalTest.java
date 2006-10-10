package net.buffalo.protocal;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.DefaultUnmarshallingContext;
import net.buffalo.protocal.io.FastStreamReader;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.UnmarshallingContext;
import junit.framework.TestCase;

public class BigDecimalTest extends TestCase {
	
	private StringWriter stringWriter;
	private FastStreamWriter streamWriter;
	private MarshallingContext marshallingContext;
	private UnmarshallingContext unmarshallingContext;

	protected void setUp() throws Exception {
		super.setUp();
		stringWriter = new StringWriter();
		streamWriter = new FastStreamWriter(stringWriter);
		marshallingContext = new DefaultMarshallingContext(new DefaultConverterLookup(), streamWriter);
		
	}
	
	public void testMarshallingBigDecimal() throws Exception {
		BigDecimal d = new BigDecimal("123.456");
		marshallingContext.convertAnother(d);
		String str = stringWriter.getBuffer().toString();
		assertEquals("<map><type>java.math.BigDecimal</type>" +
				"<string>value</string>" +
				"<string>123.456</string></map>", str);
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

}
