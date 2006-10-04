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
 * $Id: CollectionUtilTest.java,v 1.1 2006/10/01 16:54:46 mechiland Exp $
 */ 
package net.buffalo.protocal.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import net.buffalo.protocal.util.CollectionUtil;

import junit.framework.TestCase;

public class CollectionUtilTest extends TestCase {
	public void xtestListToArray() throws Exception {
		Collection c = new ArrayList();
		c.add("a");
		c.add("b");
		String[] s = (String[]) CollectionUtil.toArray(c);
		assertEquals(2, s.length);
		
		c = new LinkedList();
		c.add(new Integer(1));
		c.add(new Integer(1));
		c.add(new Integer(3));
		
		Integer[] is = (Integer[]) CollectionUtil.toArray(c);
		assertEquals(3, is.length);
	}
	
	public void xtestNestedArray() throws Exception {
		Collection c = new ArrayList();
		Collection subC1 = new ArrayList();
		subC1.add(new Integer(101));
		subC1.add(new Integer(102));
		
		Collection subC2 = new ArrayList();
		subC2.add(new Integer(201));
		subC2.add(new Integer(202));
		
		c.add(subC1);
		c.add(subC2);
		
		Integer[][] is = (Integer[][]) CollectionUtil.toArray(c);
		assertEquals(2, is.length);
		assertEquals(2, is[0].length);
		assertEquals(2, is[1].length);
	}
	
	public void testArraySetValue() throws Exception {
		int[][] a = new int[][]{
				{1,1},{2,2}
		};
		
		Array.setInt(a[0], 0, 100);
		
	}
	
}	
