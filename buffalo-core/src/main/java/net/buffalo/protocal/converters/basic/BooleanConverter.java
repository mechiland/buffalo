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
 * $Id: BooleanConverter.java,v 1.5 2006/10/01 13:58:18 mechiland Exp $
 */
package net.buffalo.protocal.converters.basic;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.ProtocolException;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamWriter;

public class BooleanConverter extends AbstractBasicConverter implements Converter {

	public boolean canConvert(Class type) {
		if (type == null)
			return false;
		return type.equals(boolean.class)
				|| type.equals(Boolean.class);
	}
	
	public Object fromString(String value) {
		if (value.equals("1")) {
			return Boolean.TRUE;
		} else if (value.equals("0")) {
			return Boolean.FALSE;
		} else {
			throw new ProtocolException("<"+ProtocalTag.TAG_BOOLEAN+"> contains only 1 or 0: "+ value);
		}
	}
	
	public void marshal(Object source, MarshallingContext context, StreamWriter streamWriter) {
		Boolean b = (Boolean) source;
		streamWriter.startNode(ProtocalTag.TAG_BOOLEAN);
		streamWriter.setValue(String.valueOf(b.booleanValue() ? 1 : 0));
		streamWriter.endNode();
	}

}
