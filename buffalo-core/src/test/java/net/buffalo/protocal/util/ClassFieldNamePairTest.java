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
 * $Id: ClassFieldNamePairTest.java,v 1.1 2006/10/01 13:58:19 mechiland Exp $
 */ 
package net.buffalo.protocal.util;

import java.util.HashMap;
import java.util.Map;

import net.buffalo.protocal.util.ClassFieldNamePair;
import junit.framework.TestCase;

public class ClassFieldNamePairTest extends TestCase {
	public void testEquals() throws Exception {
		ClassFieldNamePair pair1 = new ClassFieldNamePair(Long.class, "something");
		ClassFieldNamePair pair2 = new ClassFieldNamePair(Long.class, "something");
		ClassFieldNamePair pair3 = new ClassFieldNamePair(Double.class, "something");
		
		assertEquals(pair1, pair2);
		assertFalse(pair2.equals(pair3));
		
		Map map = new HashMap();
		map.put(pair1, "pair1");
		map.put(pair2, "pair2");
		
		assertSame("pair2", map.get(new ClassFieldNamePair(Long.class, "something")));
		
	}
}
