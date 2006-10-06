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
 * $Id: AbstractReferencableConverter.java,v 1.4 2006/10/01 13:58:19 mechiland Exp $
 */ 
package net.buffalo.protocal.converters;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamWriter;

public abstract class AbstractReferencableConverter implements Converter {

public final void marshal(Object value, MarshallingContext context, StreamWriter streamWriter) {
		
		int objectIndex = context.getObjects().indexOf(value);
		if (objectIndex > -1) {
			streamWriter.startNode(ProtocalTag.TAG_REF);
			streamWriter.setValue(String.valueOf(objectIndex));
			streamWriter.endNode();
			return;
		} 
		
		context.addObjectRef(value);
		marshalObject(value, context, streamWriter);
	}

	protected abstract void marshalObject(Object value, MarshallingContext context, StreamWriter streamWriter);
}
