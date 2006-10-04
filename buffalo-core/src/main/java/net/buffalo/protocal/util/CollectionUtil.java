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
 * $Id: CollectionUtil.java,v 1.1 2006/10/01 15:03:42 mechiland Exp $
 */ 
package net.buffalo.protocal.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {

	public static Object toArray(Collection collection) {
		
		Object outerArray = Array.newInstance(Integer[].class, collection.size());
		
		int i = 0;
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			if (element instanceof Collection) {
				Array.set(outerArray, i++, toPlatArray((Collection) element));
			}
		}

		return outerArray;
	}
	
	
	private static Object toPlatArray(Collection collection) {
		Object firstElement = collection.iterator().next();
		Object array =  Array.newInstance(firstElement.getClass(), collection.size());
		int i = 0;
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			Object element =  iter.next();
			Array.set(array, i++, element);
		}
		
		return array;
	}

}
