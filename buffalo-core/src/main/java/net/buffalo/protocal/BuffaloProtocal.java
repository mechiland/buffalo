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
 * $Id: BuffaloProtocal.java,v 1.6 2006/10/01 16:10:48 mechiland Exp $
 */
package net.buffalo.protocal;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import net.buffalo.protocal.converters.ConverterLookup;
import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.io.DefaultMarshallingStrategy;
import net.buffalo.protocal.io.FastInputStreamReader;
import net.buffalo.protocal.io.FastStreamReader;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingStrategy;

public class BuffaloProtocal {

	private MarshallingStrategy marshallingStrategy;
	private ConverterLookup converterLookup;

	private static BuffaloProtocal instance = null;
	
	private BuffaloProtocal() {
		marshallingStrategy = new DefaultMarshallingStrategy();
		converterLookup = new DefaultConverterLookup();
	}
	
	public static BuffaloProtocal getInstance() {
		if (instance == null) {
			instance = new BuffaloProtocal();
		}
		return instance;
	}

	public BuffaloCall deserialize(String source) {
		return unmarshall(new StringReader(source));
	}

	public String serialize(Object value) {
		StringWriter writer = new StringWriter();
		marshall(value, writer);
		return writer.getBuffer().toString();
	}

	public void marshall(Object value, Writer writer) {
		marshallingStrategy.marshal(value, converterLookup, new FastStreamWriter(writer));
	}
	
	public BuffaloCall unmarshall(Reader reader) {
		return marshallingStrategy.unmarshal(new FastStreamReader(reader), converterLookup);
	}
	
	public BuffaloCall unmarshall(InputStream inputStream) {
		return marshallingStrategy.unmarshal(new FastInputStreamReader(inputStream), converterLookup);
	}
}
