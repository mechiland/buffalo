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
 * $Id: FastStreamWriter.java,v 1.1 2006/10/01 16:10:49 mechiland Exp $
 */ 
package net.buffalo.protocal.io;

import java.io.IOException;
import java.io.Writer;

import net.buffalo.protocal.util.FastStack;
import net.buffalo.protocal.util.QuickWriter;

public class FastStreamWriter implements StreamWriter {

	private static final char[] NULL = "&#x0;".toCharArray();
    private static final char[] AMP = "&amp;".toCharArray();
    private static final char[] LT = "&lt;".toCharArray();
    private static final char[] GT = "&gt;".toCharArray();
    private static final char[] SLASH_R = "&#x0D;".toCharArray();
    private static final char[] QUOT = "&quot;".toCharArray();
    private static final char[] APOS = "&apos;".toCharArray();
    
	private QuickWriter writer;
	private FastStack stack;
	private int depth=0;
	public FastStreamWriter(Writer out) {
		this.writer = new QuickWriter(out);
		stack = new FastStack(5);
	}
	
	public void close() {
		writer.close();
	}

	public void endNode() {
		depth--;
		write("</");
		write((String)stack.pop());
		write(">");
		if (depth == 0) {
			flush();
		}
	}

	public void flush() {
		writer.flush();
	}

	public void setValue(String text) {
		if (text == null) { 
			write("");
			return;
		} 
		try {
			writeText(text);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeText(String text) throws IOException {
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            switch (c) {
                case '\0':
                    writer.write(NULL);
                    break;
                case '&':
                    writer.write(AMP);
                    break;
                case '<':
                    writer.write(LT);
                    break;
                case '>':
                    writer.write(GT);
                    break;
                case '"':
                    writer.write(QUOT);
                    break;
                case '\'':
                    writer.write(APOS);
                    break;
                case '\r':
                    writer.write(SLASH_R);
                    break;
                default:
                    writer.write(c);
            }
        }
    }
	
	private void write(String text) {
		writer.write(text);
	}

	public void startNode(String name) {
		writer.write("<");
		writer.write(name);
		writer.write(">");
		stack.push(name);
		depth++;
	}

}
