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
 * $Id: DefaultMarshallingStrategy.java,v 1.3 2006/10/01 13:58:22 mechiland Exp $
 */ 
package net.buffalo.protocal.io;

import java.util.ArrayList;
import java.util.List;

import net.buffalo.protocal.BuffaloCall;
import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.ConverterLookup;

public class DefaultMarshallingStrategy implements MarshallingStrategy {
	
	public void marshal(Object obj, ConverterLookup converterLookup, StreamWriter streamWriter) {
		streamWriter.startNode(ProtocalTag.TAG_REPLY);
		MarshallingContext marshallingContext = new DefaultMarshallingContext(converterLookup, streamWriter);
		marshallingContext.convertAnother(obj);
		streamWriter.endNode();
		streamWriter.flush();
		streamWriter.close();
	}

	public BuffaloCall unmarshal(StreamReader in, ConverterLookup converterLookup) {
		in.moveDown();
		String methodName = in.getValue();
		in.moveUp();
		
		UnmarshallingContext unmarshallingContext = new DefaultUnmarshallingContext(converterLookup, in);
		List arguments = new ArrayList();
		while(in.hasMoreChildren()) {
			arguments.add(unmarshallingContext.convertAnother());
		}
		in.close();
		return new BuffaloCall(methodName, arguments.toArray());
	}

}
