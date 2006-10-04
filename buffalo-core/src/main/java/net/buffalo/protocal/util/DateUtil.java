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
 * $Id: DateUtil.java,v 1.1 2006/10/01 15:03:42 mechiland Exp $
 */ 
package net.buffalo.protocal.util;

import java.util.Calendar;
import java.util.regex.Pattern;

import net.buffalo.protocal.ProtocolException;


public class DateUtil {

	public static String toUTCString(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		StringBuffer buffer = new StringBuffer();
		buffer.append(cal.get(Calendar.YEAR));
		buffer.append(DateUtil.leadingZero(cal.get(Calendar.MONTH)));
		buffer.append(DateUtil.leadingZero(cal.get(Calendar.DAY_OF_MONTH)));
		buffer.append("T");
		buffer.append(DateUtil.leadingZero(cal.get(Calendar.HOUR_OF_DAY)));
		buffer.append(DateUtil.leadingZero(cal.get(Calendar.MINUTE)));
		buffer.append(DateUtil.leadingZero(cal.get(Calendar.SECOND)));
		buffer.append("Z");
		return buffer.toString();
	}

	public static String leadingZero(int num) {
		return num < 10 ? "0" + num : String.valueOf(num);
	}

	public static java.util.Date fromUTCString(String string) {
		if (string == null || !Pattern.matches("^\\d{8}T\\d{6}Z$", string)) {
			throw new ProtocolException("date format error: " + string);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(string.substring(0,4)).intValue());
		cal.set(Calendar.MONTH, Integer.valueOf(string.substring(4,6)).intValue());
		cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(string.substring(6,8)).intValue());
		cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(string.substring(9,11)).intValue());
		cal.set(Calendar.MINUTE, Integer.valueOf(string.substring(11,13)).intValue());
		cal.set(Calendar.SECOND, Integer.valueOf(string.substring(13,15)).intValue());
	
		return cal.getTime();
	}

}
