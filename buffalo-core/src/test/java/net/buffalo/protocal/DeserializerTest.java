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
 * $Id: DeserializerTest.java,v 1.4 2006/09/30 04:38:11 mechiland Exp $
 */ 
package net.buffalo.protocal;

import junit.framework.TestCase;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.io.DefaultUnmarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.UnmarshallingContext;

public class DeserializerTest extends TestCase {
	
	public void testShouldGetCorrectType() throws Exception {
		StreamReader streamReader = TestUtils.createStreamReader("<buffalo-call><string>hi</string></buffalo-call>");
		UnmarshallingContext des = new DefaultUnmarshallingContext(new DefaultConverterLookup(), streamReader);
		Object o = des.convertAnother();
		assertEquals("hi", o);
	}
}
