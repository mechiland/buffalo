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
 * $Id: AbstractListConverter.java,v 1.1 2006/09/29 16:01:32 mechiland Exp $
 */ 
package net.buffalo.protocal.converters.collection;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.converters.AbstractReferencableConverter;
import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.io.StreamWriter;

public abstract class AbstractListConverter extends AbstractReferencableConverter implements Converter {
	
	protected void writeListHeader(StreamWriter streamWriter, String type, int size) {
		streamWriter.startNode(ProtocalTag.TAG_LIST);
		streamWriter.startNode(ProtocalTag.TAG_TYPE);
		streamWriter.setValue(type);
		streamWriter.endNode();
		streamWriter.startNode(ProtocalTag.TAG_LENGTH);
		streamWriter.setValue(String.valueOf(size));
		streamWriter.endNode();	
	}
	
}
