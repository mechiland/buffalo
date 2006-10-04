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
 * $Id: RequestUtils.java,v 1.1 2006/01/07 03:27:44 mechiland Exp $
 */
package net.buffalo.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RequestUtils {
	private static final Map _localeMap = new HashMap();

	static {
		Locale[] locales = Locale.getAvailableLocales();
		for (int i = 0; i < locales.length; i++) {
			_localeMap.put(locales[i].toString(), locales[i]);
		}
	}

	public static Locale getLocale(String s) {
		Locale result = null;

		synchronized (_localeMap) {
			result = (Locale) _localeMap.get(s);
		}

		if (result == null) {
			String[] terms = s.split("_");

			switch (terms.length) {
			case 1:

				result = new Locale(terms[0], "");
				break;

			case 2:

				result = new Locale(terms[0], terms[1]);
				break;

			case 3:

				result = new Locale(terms[0], terms[1], terms[2]);
				break;

			default:

				throw new IllegalArgumentException("Unable to convert '" + s
						+ "' to a Locale.");
			}

			synchronized (_localeMap) {
				_localeMap.put(s, result);
			}

		}

		return result;

	}
}
