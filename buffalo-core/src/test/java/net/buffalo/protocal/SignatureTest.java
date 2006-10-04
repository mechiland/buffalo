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
 * $Id: SignatureTest.java,v 1.2 2006/09/30 11:48:04 mechiland Exp $
 */ 
package net.buffalo.protocal;

import java.util.HashMap;

import junit.framework.TestCase;

public class SignatureTest extends TestCase {
	
	public void testShouldEqualWithTheSameNameAndParameterTypes() throws Exception {
		Signature s1 = new Signature(SignatureTest.class, "dummy", new Class[]{Double.class});
		Signature s2 = new Signature(SignatureTest.class, "dummy", new Class[]{Double.class});
		
		assertEquals(s1, s2);
	}
	
	public void testPutToCache() throws Exception {
		HashMap cache = new HashMap();
		Signature s1 = new Signature(SignatureTest.class, "dummy", new Class[]{Double.class});
		System.out.println(s1.hashCode());
		Signature s2 = new Signature(SignatureTest.class, "dummy", new Class[]{Double.class});
		System.out.println(s2.hashCode());
		cache.put(s1, new Integer(1));
		cache.put(s2, new Integer(2));
		
		Signature s3 = new Signature(SignatureTest.class, "dummy", new Class[]{Double.class});
		
		System.out.println(cache.get(s3));
	}
}
