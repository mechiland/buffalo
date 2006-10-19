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
 * $Id: DefaultConverterLookup.java,v 1.6 2006/10/02 09:33:53 mechiland Exp $
 */ 
package net.buffalo.protocal.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.ProtocolException;
import net.buffalo.protocal.converters.basic.BooleanConverter;
import net.buffalo.protocal.converters.basic.DateConverter;
import net.buffalo.protocal.converters.basic.DoubleConverter;
import net.buffalo.protocal.converters.basic.IntegerConverter;
import net.buffalo.protocal.converters.basic.LongConverter;
import net.buffalo.protocal.converters.basic.NullConverter;
import net.buffalo.protocal.converters.basic.StringConverter;
import net.buffalo.protocal.converters.collection.ArrayConverter;
import net.buffalo.protocal.converters.collection.CollectionConverter;
import net.buffalo.protocal.converters.map.MapConverter;
import net.buffalo.protocal.converters.map.ObjectConverter;

public class DefaultConverterLookup implements ConverterLookup {

	private Map converterCache;
	private Map tagNameConverterCache;
	private ArrayList converters;
	Converter nullConverter;
	
	public DefaultConverterLookup() {
		converters = new ArrayList(12);
		converterCache = new HashMap();
		nullConverter = new NullConverter();
		tagNameConverterCache = new Hashtable(10);
		
		registerDefaultConverters();
	}
	
	public Converter lookupConverterForType(Class type) {
		Converter result = null;
		if (converterCache.get(type) == null) {
			for (int i = 0; i < converters.size(); i++) {
				Converter converter = (Converter) converters.get(i);
				if (converter.canConvert(type)) {
					converterCache.put(type, converter);
					result = converter;
					break;
				}
			}
		} else {
			result = (Converter) converterCache.get(type);
		}
		return result;
	}

	protected void registerDefaultConverters() {
		BooleanConverter booleanConverter = new BooleanConverter();
		DoubleConverter doubleConverter = new DoubleConverter();
		IntegerConverter integerConverter = new IntegerConverter();
		LongConverter longConverter = new LongConverter();
		StringConverter stringConverter = new StringConverter();
		DateConverter dateConverter = new DateConverter();
		CollectionConverter collectionConverter = new CollectionConverter();
		MapConverter mapConverter = new MapConverter();
		ArrayConverter arrayConverter = new ArrayConverter();
		SqlDateConverter sqlDateConverter = new SqlDateConverter();
		BigNumberConverter bigNumberConverter = new BigNumberConverter();
		ExceptionConverter exceptionConverter = new ExceptionConverter();
		ObjectConverter objectConverter = new ObjectConverter();

		converters.add(nullConverter);
		converters.add(booleanConverter);
		converters.add(doubleConverter);
		converters.add(integerConverter);
		converters.add(longConverter);
		converters.add(stringConverter);
		converters.add(dateConverter);
		converters.add(collectionConverter);
		converters.add(mapConverter);
		converters.add(arrayConverter);
		converters.add(sqlDateConverter);
		converters.add(bigNumberConverter);
		converters.add(exceptionConverter);
		// Should be last one
		converters.add(objectConverter);
		
		tagNameConverterCache.put(ProtocalTag.TAG_BOOLEAN, booleanConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_STRING, stringConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_INT, integerConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_LONG, longConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_DOUBLE, doubleConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_NULL, nullConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_DATE, dateConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_LIST, collectionConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_MAP, mapConverter);
		tagNameConverterCache.put(ProtocalTag.TAG_REF, new ReferenceConverter());
		
		converterCache.put(Boolean.class, booleanConverter);
		converterCache.put(boolean.class, booleanConverter);
		converterCache.put(String.class, stringConverter);
		converterCache.put(Integer.class, integerConverter);
		converterCache.put(int.class, integerConverter);
		converterCache.put(Long.class, longConverter);
		converterCache.put(long.class, longConverter);
		converterCache.put(Double.class, doubleConverter);
		converterCache.put(double.class, doubleConverter);
		converterCache.put(Date.class, dateConverter);
		converterCache.put(ArrayList.class, collectionConverter);
		converterCache.put(LinkedList.class, collectionConverter);
		converterCache.put(HashSet.class, collectionConverter);
		converterCache.put(Vector.class, collectionConverter);
		converterCache.put(TreeSet.class, collectionConverter);
		converterCache.put(HashMap.class, mapConverter);
		converterCache.put(TreeMap.class, mapConverter);
		converterCache.put(java.sql.Date.class, sqlDateConverter);
		converterCache.put(java.math.BigDecimal.class, bigNumberConverter);
		converterCache.put(java.math.BigInteger.class, bigNumberConverter);
	}
	
	
	public Converter getNullConverter() {
		return nullConverter;
	}

	public Converter lookupConverterForTagName(String tagName) {
		Object converter = tagNameConverterCache.get(tagName);
		if (converter == null) {
			throw new ProtocolException("unrecoganized tag: " + tagName);
		}
		
		return (Converter) converter;
	}
}
