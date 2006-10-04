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
 * $Id: DefaultStreamReader.java,v 1.4 2006/10/01 16:10:49 mechiland Exp $
 */ 
package net.buffalo.protocal.io;

import java.io.Reader;

//import com.thoughtworks.xstream.io.xml.XppReader;

public class DefaultStreamReader implements StreamReader {
	
//	private XppReader delegateStreamReader;

	public DefaultStreamReader(Reader in) {
//		delegateStreamReader = new XppReader(in);
	}

	public void close() {
//		delegateStreamReader.close();
	}

	public String getNodeName() {
		throw new UnsupportedOperationException();
//		return delegateStreamReader.getNodeName();
	}

	public String getValue() {
		throw new UnsupportedOperationException();
//		return delegateStreamReader.getValue();
	}

	public boolean hasMoreChildren() {
		throw new UnsupportedOperationException();
//		return delegateStreamReader.hasMoreChildren();
	}

	public void moveDown() {
//		delegateStreamReader.moveDown();
	}

	public void moveUp() {
//		delegateStreamReader.moveUp();
	}

}
