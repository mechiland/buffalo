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
 * $Id: AbstractMapConverter.java,v 1.4 2006/10/01 15:03:43 mechiland Exp $
 */ 
package net.buffalo.protocal.converters.map;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.AbstractReferencableConverter;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamWriter;

public abstract class AbstractMapConverter extends AbstractReferencableConverter implements Converter {

	protected final void marshalObject(Object value, MarshallingContext context, StreamWriter streamWriter) {
		streamWriter.startNode(ProtocalTag.TAG_MAP);
		streamWriter.startNode(ProtocalTag.TAG_TYPE);
		streamWriter.setValue(value.getClass().getName());
		streamWriter.endNode();
		
		marshalMapObject(value, context, streamWriter);

		streamWriter.endNode();
	}

	protected abstract void marshalMapObject(Object value, MarshallingContext context, StreamWriter streamWriter) ;

}
