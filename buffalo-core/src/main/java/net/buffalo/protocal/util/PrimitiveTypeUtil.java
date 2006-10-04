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
 * $Id: PrimitiveTypeUtil.java,v 1.1 2006/10/01 15:03:41 mechiland Exp $
 */ 
package net.buffalo.protocal.util;

public class PrimitiveTypeUtil {

	public static Object toWrapperArrayIfNeeded(Object value) {
		
		if (value.getClass().equals(int[].class)) {
			int[] primitiveValues = (int[])value;
			Integer[] wrapperValues = new Integer[primitiveValues.length];
			for (int i = 0; i < primitiveValues.length; i++) {
				wrapperValues[i] = new Integer(primitiveValues[i]);
			}
	
			return wrapperValues;
		}
		
		if (value.getClass().equals(short[].class)) {
			short[] primitiveValues = (short[])value;
			Integer[] wrapperValues = new Integer[primitiveValues.length];
			for (int i = 0; i < wrapperValues.length; i++) {
				wrapperValues[i] = new Integer(primitiveValues[i]);
			}
			return wrapperValues;
		}
		
		if (value.getClass().equals(byte[].class)) {
			byte[] primitiveValues = (byte[])value;
			Integer[] wrapperValues = new Integer[primitiveValues.length];
			for (int i = 0; i < wrapperValues.length; i++) {
				wrapperValues[i] = new Integer(primitiveValues[i]);
			}
			return wrapperValues;
		}
		
		if (value.getClass().equals(long[].class)) {
			long[] primitiveValues = (long[])value;
			Long[] wrapperValues = new Long[primitiveValues.length];
			for (int i = 0; i < wrapperValues.length; i++) {
				wrapperValues[i] = new Long(primitiveValues[i]);
			}
			return wrapperValues;
		}
		
		if (value.getClass().equals(float[].class)) {
			float[] primitiveValues = (float[])value;
			Double[] wrapperValues = new Double[primitiveValues.length];
			for (int i = 0; i < wrapperValues.length; i++) {
				wrapperValues[i] = new Double(primitiveValues[i]);
			}
			return wrapperValues;
		}
		
		if (value.getClass().equals(double[].class)) {
			double[] primitiveValues = (double[])value;
			Double[] wrapperValues = new Double[primitiveValues.length];
			for (int i = 0; i < wrapperValues.length; i++) {
				wrapperValues[i] = new Double(primitiveValues[i]);
			}
			return wrapperValues;
		}
		
		if (value.getClass().equals(boolean[].class)) {
			boolean[] primitiveValues = (boolean[])value;
			Boolean[] wrapperValues = new Boolean[primitiveValues.length];
			for (int i = 0; i < wrapperValues.length; i++) {
				wrapperValues[i] = new Boolean(primitiveValues[i]);
			}
			return wrapperValues;
		}
		
		if (value.getClass().equals(char[].class)) {
			char[] primitiveValues = (char[])value;
			String[] wrapperValues = new String[primitiveValues.length];
			for (int i = 0; i < wrapperValues.length; i++) {
				wrapperValues[i] = String.valueOf(primitiveValues[i]);
			}
			return wrapperValues;
		}
		
		return value;
	}

}
