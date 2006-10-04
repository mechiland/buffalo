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
 * $Id: DefaultStreamWriter.java,v 1.3 2006/10/01 16:10:49 mechiland Exp $
 */ 
package net.buffalo.protocal.io;

import java.io.Writer;

//import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
//import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class DefaultStreamWriter implements StreamWriter {

//	private PrettyPrintWriter delegateWriter;
	
	public DefaultStreamWriter(Writer writer) {
//		delegateWriter = new CompressedPrintWriter(writer);
	}

	public void close() {
//		delegateWriter.close();
	}

	public void endNode() {
//		delegateWriter.endNode();
	}

	public void flush() {
//		delegateWriter.flush();
	}

	public void setValue(String text) {
//		delegateWriter.setValue(text);
	}

	public void startNode(String name) {
//		delegateWriter.startNode(name);
	}
	
//	class CompressedPrintWriter extends PrettyPrintWriter {
//		
//		public CompressedPrintWriter(Writer writer) {
//			super(writer, new char[0], "", new NullXmlFriendlyReplacer());
//		}
//		protected void endOfLine() {
//			// do nothing
//		}
//	}
//	
//	class NullXmlFriendlyReplacer extends XmlFriendlyReplacer {
//		public String escapeName(String name) {
//			return name;
//		}
//		public String unescapeName(String name) {
//			return name;
//		}
//	}
}
