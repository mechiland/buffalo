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
 * $Id: DeserializerFunctionalTest.java,v 1.3 2006/09/30 05:04:50 mechiland Exp $
 */ 
package net.buffalo.protocal.acceptance;

import net.buffalo.protocal.BuffaloCall;
import net.buffalo.protocal.BuffaloProtocal;
import junit.framework.TestCase;

public class DeserializerFunctionalTest extends TestCase {
	public void testShouldDeserialize() throws Exception {
		BuffaloCall call = BuffaloProtocal.getInstance().deserialize("<buffalo-call>" +
				"<method>sum</method>" +
				"<double>0.1</double>" +
				"<double>0.2</double>" +
				"</buffalo-call>");
		
		assertEquals("sum", call.getMethodName());
		assertEquals(2, call.getArguments().length);
		assertTrue(call.getArguments()[0] instanceof Double);
	}
	
}
