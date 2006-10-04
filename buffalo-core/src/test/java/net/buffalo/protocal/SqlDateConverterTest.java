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
 * $Id: SqlDateConverterTest.java,v 1.3 2006/10/02 09:33:53 mechiland Exp $
 */ 
package net.buffalo.protocal;

import java.io.StringWriter;

import junit.framework.TestCase;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.SqlDateConverter;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamWriter;

public class SqlDateConverterTest extends TestCase {
	public void testShouldConverterSqlDate() throws Exception {
		java.sql.Date sdate = new java.sql.Date(new java.util.Date(2006-1900, 10, 1).getTime());
		SqlDateConverter converter = new SqlDateConverter();
		
		assertTrue(converter.canConvert(sdate.getClass()));
		assertFalse(converter.canConvert(java.util.Date.class));
		
		StringWriter stringWriter = new StringWriter();
		StreamWriter streamWriter = new FastStreamWriter(stringWriter);
		
		DefaultConverterLookup defaultConverterLookup = new DefaultConverterLookup();
		MarshallingContext context = new DefaultMarshallingContext(defaultConverterLookup, streamWriter);
		converter.marshal(sdate, context, streamWriter);
		
		String string = "<map><type>java.sql.Date</type><string>value</string><date>20061001T000000Z</date></map>";
		assertEquals(string, 
				stringWriter.getBuffer().toString());
		
	}
	
	public void testConverterLookupContainsSqlDateConverter() throws Exception {
		DefaultConverterLookup converterLookup = new DefaultConverterLookup();
		assertEquals(SqlDateConverter.class, converterLookup.lookupConverterForType(java.sql.Date.class).getClass());
		
	}
}
