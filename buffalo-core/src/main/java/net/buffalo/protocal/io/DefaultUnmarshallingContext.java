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
 * $Id: DefaultUnmarshallingContext.java,v 1.1 2006/09/30 04:38:13 mechiland Exp $
 */
package net.buffalo.protocal.io;

import java.util.ArrayList;
import java.util.List;

import net.buffalo.protocal.converters.Converter;
import net.buffalo.protocal.converters.ConverterLookup;

public class DefaultUnmarshallingContext implements UnmarshallingContext {

	private List objects;
	private ConverterLookup converterLookup;
	private StreamReader streamReader;

	public DefaultUnmarshallingContext(ConverterLookup converterLookup, StreamReader in) {
		this.converterLookup = converterLookup;
		this.streamReader = in;
		this.objects = new ArrayList();
	}
	
	public Object convertAnother() {
		streamReader.moveDown();
		Converter converter = converterLookup.lookupConverterForTagName(streamReader.getNodeName());
		Object object = converter.unmarshal(streamReader, this);
		streamReader.moveUp();
		return object;
	}

	public void addObject(Object object) {
		this.objects.add(object);
	}

	public List getObjects() {
		return this.objects;
	}

}
