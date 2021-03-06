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
 * $Id: ClassUtil.java,v 1.1 2006/10/01 13:58:20 mechiland Exp $
 */
package net.buffalo.protocal.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.buffalo.protocal.AccessFieldException;
import net.buffalo.protocal.InitializeObjectFailedException;
import net.buffalo.protocal.TypeNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassUtil {
	
	private static final Log LOGGER = LogFactory.getLog(ClassUtil.class);
	
	private static Map fieldCache = new HashMap();

	public static Object newInstanceOfType(String className) {
		Object result = null;
		try {
			result = getClassFor(className).newInstance();
		} catch (InstantiationException e) {
			throw new InitializeObjectFailedException(
					"fail to initialize type: " + className, e);
		} catch (IllegalAccessException e) {
			throw new InitializeObjectFailedException(
					"fail to initialize type: " + className, e);
		} 
		
		return result;
	}
	
	public static Class getClassFor(String className) {
		try {
			return ClassUtil.class.getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new TypeNotFoundException(e);
		}
	}
	
	public static void setFieldValue(Object obj, String property, Object value) {
		Class type =  obj.getClass();
		Field field = null;
		ClassFieldNamePair pair = new ClassFieldNamePair(type, property);
		if (fieldCache.get(pair) == null) {
			field = (Field) getFieldMap(type).get(property);
			if (field != null) {
				fieldCache.put(pair, field);
			} else {
				throw new AccessFieldException("Cannot find field ["+property+"] for " + type);
			}
		} else {
			field = (Field) fieldCache.get(pair);
		}
		
		try {
			try {
				field.set(obj, value);
			} catch(IllegalArgumentException ex) {
				field.set(obj, convertValue(value, field.getType()));
			}
		} catch (SecurityException e) {
			throw new net.buffalo.protocal.AccessFieldException(e);
		} catch (IllegalAccessException e) {
			throw new net.buffalo.protocal.AccessFieldException(e);
		}
	}
	
	private static HashMap getFieldMap(Class cl) {
		HashMap fieldMap = new HashMap();
		for (; cl != null; cl = cl.getSuperclass()) {
			Field[] fields = cl.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (Modifier.isTransient(field.getModifiers())
						|| Modifier.isStatic(field.getModifiers()))
					continue;
				field.setAccessible(true);
				fieldMap.put(field.getName(), field);
			}
		}

		return fieldMap;
	}
	
	public static Object convertValue(Object value, Class targetType) {
		
		if (value.getClass().equals(targetType)) return value;
		
		if (targetType.isPrimitive()) {
			targetType = getWrapperClass(targetType);
		}
		
		if (targetType.isAssignableFrom(value.getClass())) return value;
		
		if ((value instanceof String || value instanceof Number) && Number.class.isAssignableFrom(targetType)) {
			try {
				Constructor ctor = targetType.getConstructor(new Class[]{String.class});
				return ctor.newInstance(new Object[] { value.toString() });
			} catch (Exception e) {
				LOGGER.error("convert type error", e);
				throw new RuntimeException("Cannot convert from "+value.getClass().getName() + " to " + targetType, e);
			}
		}
		
		if (targetType.isArray() && Collection.class.isAssignableFrom(value.getClass())) {
			Collection collection = (Collection)value;
			Object array = Array.newInstance(targetType.getComponentType(), collection.size());
			int i = 0; 
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				Object val = iter.next();
				Array.set(array, i++, val);
			}
			
			return array;
		}
		
		if (Collection.class.isAssignableFrom(targetType) && value.getClass().isArray()) {
			return Arrays.asList((Object[]) value);
		}
		
		throw new IllegalArgumentException("Cannot convert from "+value.getClass().getName() + " to " + targetType);
	}

	public static Class getWrapperClass(Class primitiveClass) {
		return primitiveClass == int.class ? Integer.class : 
			   primitiveClass == long.class ? Long.class : 
			   primitiveClass == short.class ? Short.class :
               primitiveClass == byte.class ? Byte.class :
			   primitiveClass == float.class ? Float.class :
			   primitiveClass == double.class ? Double.class : 
			   primitiveClass == boolean.class ? Boolean.class :
			   primitiveClass;
	}
	
	public static Object invokeMethod(Object instance, Method method, Object[] arguments) throws 
									IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			if (!parameterTypes[i].isAssignableFrom(arguments[i].getClass())) {
				arguments[i] = convertValue(arguments[i], parameterTypes[i]);
			}
		}
		return method.invoke(instance, arguments);
	}
}
