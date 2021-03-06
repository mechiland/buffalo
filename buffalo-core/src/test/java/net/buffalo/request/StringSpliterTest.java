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
 * $Id: StringSpliterTest.java,v 1.1 2006/01/07 03:27:29 mechiland Exp $
 */ 
package net.buffalo.request;

import junit.framework.TestCase;

public class StringSpliterTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(StringSpliterTest.class);
	}
	
	public void testStringSpliter() throws Exception {
		String pathInfo = "/buffalo/simpleService";
		
		String terms[] = pathInfo.split("/");
		
		assertEquals("splited size", 3, terms.length);
		assertEquals("terms #1", "", terms[0]);
		assertEquals("terms #2", "buffalo", terms[1]);
		assertEquals("terms #3", "simpleService", terms[2]);
		
	}

}
