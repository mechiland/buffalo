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
 * $Id: ReferenceConverter.java,v 1.3 2006/09/30 04:38:12 mechiland Exp $
 */ 
package net.buffalo.protocal.converters;

import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public class ReferenceConverter implements Converter {

	public boolean canConvert(Class type) {
		throw new UnsupportedOperationException();
	}

	public void marshal(Object source, MarshallingContext context, StreamWriter streamWriter) {
		throw new UnsupportedOperationException();
	}

	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		int idx = Integer.valueOf(reader.getValue()).intValue();
		return unmarshallingContext.getObjects().get(idx);
	}

}
