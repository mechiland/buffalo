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
 * $Id: ExceptionConverter.java,v 1.3 2006/09/30 13:31:54 mechiland Exp $
 */ 
package net.buffalo.protocal.converters;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamReader;
import net.buffalo.protocal.io.StreamWriter;
import net.buffalo.protocal.io.UnmarshallingContext;

public class ExceptionConverter implements Converter {

	public boolean canConvert(Class type) {
		return Throwable.class.isAssignableFrom(type);
	}

	public void marshal(Object source, MarshallingContext marshallingContext, StreamWriter streamWriter) {
		Throwable ex = (Throwable) source;
		String detail = "";
		if (ex.getCause() != null) {
			detail = "caused by: " + ex.getCause().getMessage();
		}
		streamWriter.startNode(ProtocalTag.TAG_FAULT);
		node(streamWriter, ProtocalTag.TAG_STRING, "code");
		node(streamWriter, ProtocalTag.TAG_STRING, ex.getClass().getName());
		node(streamWriter, ProtocalTag.TAG_STRING, "message");
		node(streamWriter, ProtocalTag.TAG_STRING, ex.getMessage());
		node(streamWriter, ProtocalTag.TAG_STRING, "detail");
		node(streamWriter, ProtocalTag.TAG_STRING, detail);
		streamWriter.endNode();
	}

	private void node(StreamWriter streamWriter, String tagName, String value) {
		streamWriter.startNode(tagName);
		streamWriter.setValue(value);
		streamWriter.endNode();
	}

	public Object unmarshal(StreamReader reader, UnmarshallingContext unmarshallingContext) {
		throw new UnsupportedOperationException();
	}

}
