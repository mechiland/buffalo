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
 * $Id: TestUtils.java,v 1.2 2006/10/01 13:58:23 mechiland Exp $
 */
package net.buffalo.protocal;

import java.io.StringReader;

import net.buffalo.protocal.io.FastStreamReader;
import net.buffalo.protocal.io.StreamReader;


public class TestUtils {

	public static StreamReader createStreamReader(String sourcee) {
		return new FastStreamReader(new StringReader(sourcee));
	}

}
