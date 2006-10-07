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
 * $Id: FastStreamReader.java,v 1.1 2006/10/01 13:58:22 mechiland Exp $
 */
package net.buffalo.protocal.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;

import net.buffalo.protocal.ProtocalTag;
import net.buffalo.protocal.ProtocolException;

/**
 * This is for web use as using the {FastStreamReader} will cause encoding issue in Opera
 * 
 * @author michael
 *
 */
public class FastInputStreamReader implements StreamReader {

	public final static int TAG_EOF = -1;
	public final static int TAG_NULL = 0;
	public final static int TAG_BOOLEAN = 1;
	public final static int TAG_INT = 2;
	public final static int TAG_LONG = 3;
	public final static int TAG_DOUBLE = 4;
	public final static int TAG_DATE = 5;
	public final static int TAG_STRING = 6;
	public final static int TAG_MAP = 9;
	public final static int TAG_LIST = 10;
	public final static int TAG_TYPE = 11;
	public final static int TAG_LENGTH = 12;
	public final static int TAG_REF = 13;
	public final static int TAG_CALL = 15;
	public final static int TAG_FAULT = 17;
	public final static int TAG_METHOD = 18;
	public final static int TAG_NULL_END = TAG_NULL + 100;
	public final static int TAG_BOOLEAN_END = TAG_BOOLEAN + 100;
	public final static int TAG_INT_END = TAG_INT + 100;
	public final static int TAG_LONG_END = TAG_LONG + 100;
	public final static int TAG_DOUBLE_END = TAG_DOUBLE + 100;
	public final static int TAG_DATE_END = TAG_DATE + 100;
	public final static int TAG_STRING_END = TAG_STRING + 100;
	public final static int TAG_MAP_END = TAG_MAP + 100;
	public final static int TAG_LIST_END = TAG_LIST + 100;
	public final static int TAG_TYPE_END = TAG_TYPE + 100;
	public final static int TAG_LENGTH_END = TAG_LENGTH + 100;
	public final static int TAG_REF_END = TAG_REF + 100;
	public final static int TAG_CALL_END = TAG_CALL + 100;
	public final static int TAG_FAULT_END = TAG_FAULT + 100;
	public final static int TAG_METHOD_END = TAG_METHOD + 100;

	private static HashMap tagCache;
	private final InputStream in;
	private int peek;
	private int peekTag;
	private StringBuffer sbuf = new StringBuffer();
	protected StringBuffer entityBuffer = new StringBuffer();
	
	private Stack stack  = new Stack();
	
	private int depth = 0;
	
	private Tag currentTag;
	
	public FastInputStreamReader(InputStream in) {
		this.in = in;
	    peek = -1;
	    peekTag = -1;
		moveDown();
	}

	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getNodeName() {
		return tagName(currentTag.tag);
	}

	public String getValue() {
		return currentTag.value;
	}

	public boolean hasMoreChildren() {
		int tag = parseTag();
		peekTag = tag;
		return 0 < tag && tag <100;
	}

	public void moveDown() {
		
		int tag = parseTag();
		switch(tag) {
		case TAG_METHOD:
		case TAG_INT:
		case TAG_LENGTH:
		case TAG_TYPE:
		case TAG_STRING:
		case TAG_BOOLEAN:
		case TAG_LONG:
		case TAG_DATE:
		case TAG_DOUBLE:
		case TAG_NULL:
		case TAG_REF:
		case TAG_LIST:
		case TAG_MAP:
		case TAG_CALL: 
			currentTag = new Tag(tag, parseString());
			stack.push(currentTag);
			depth++;
			break;
			
		default: throw new ProtocolException("unexpected tag: " + tagName(tag));
		}
	}
	
	public void moveUp() {
		int tag = parseTag();
		currentTag = (Tag) stack.pop();
		if (currentTag.tag+100 == tag) {
			depth--;
			currentTag = (Tag) stack.get(depth-1);
		} else {
			throw new ProtocolException("tag not match: " + tagName(currentTag.tag));
		}
	}

	protected String parseString() {
		sbuf.setLength(0);
		int ch;
		while ((ch = readChar()) >= 0)
			sbuf.append((char) ch);
		return sbuf.toString();
	}

	private int readChar() {
		int ch = read();
		if (ch == '<' || ch < 0) {
			peek = ch;
			return -1;
		}

		if (ch == '&') {
			ch = read();
			if (ch == '#') {
				ch = read();
				if (ch >= '0' && ch <= '9') {
					int v = 0;
					for (; ch >= '0' && ch <= '9'; ch = read()) {
						v = 10 * v + ch - '0';
					}

					if (ch != ';')
						throw new ProtocolException("expected ';' at " + (char) ch);

					return (char) v;
				} else
					throw new ProtocolException("expected digit at " + (char) ch);
			} else {
				entityBuffer.setLength(0);
				for (; ch >= 'a' && ch <= 'z'; ch = read())
					entityBuffer.append((char) ch);

				String entity = entityBuffer.toString();

				if (ch != ';')
					throw expectedChar("';'", ch);

				if (entity.equals("amp"))
					return '&';
				else if (entity.equals("apos"))
					return '\'';
				else if (entity.equals("quot"))
					return '"';
				else if (entity.equals("lt"))
					return '<';
				else if (entity.equals("gt"))
					return '>';
				else
					throw new ProtocolException("unknown XML entity &"
							+ entity + "; at `" + (char) ch + "'");
			}
		} else if (ch < 0x80)
			return (char) ch;
		else if ((ch & 0xe0) == 0xc0) {
			int ch1 = read();
			int v = ((ch & 0x1f) << 6) + (ch1 & 0x3f);

			return (char) v;
		} else if ((ch & 0xf0) == 0xe0) {
			int ch1 = read();
			int ch2 = read();
			int v = ((ch & 0x0f) << 12) + ((ch1 & 0x3f) << 6) + (ch2 & 0x3f);
			return (char) v;
		} else
			throw new ProtocolException("bad utf-8 encoding");
	}
	
	protected static String tagName(int tag)
	  {
	    switch (tag) {
	    case TAG_NULL: return "null";
	    case TAG_BOOLEAN: return "boolean";
	    case TAG_INT: return "int";
	    case TAG_LONG: return "long";
	    case TAG_DOUBLE: return "double";
	    case TAG_STRING: return "string";
	    case TAG_MAP: return "map";
	    case TAG_LIST: return "list";
	    case TAG_TYPE: return "type";
	    case TAG_LENGTH: return "length";
	    case TAG_REF: return "ref";
	    case TAG_CALL: return "buffalo-call";
	    case TAG_FAULT: return "fault";
	    case TAG_METHOD: return "method";
	    case TAG_DATE: return "date";
	    case -1: return "end of file";
	    default: return "unknown " + tag;
	    }
	}
	
	private int parseTag() {
		if (peekTag >= 0) {
			int tag = peekTag;
			peekTag = -1;
			return tag;
		}
		int ch = skipWhitespace();
		int endTagDelta = 0;
		if (ch != '<') throw expectedChar("'<'", ch);
		ch = read(); 
		if (ch == '/') {
			endTagDelta = 100;
			try {
				ch = in.read();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (!isTagChar(ch)) throw expectedChar("tag", ch);
		sbuf.setLength(0);
		for (; isTagChar(ch); ch = read()) sbuf.append((char) ch);
		if (ch != '>') throw expectedChar("'>'", ch); 
		Integer value = (Integer) tagCache.get(sbuf.toString());
		if (value == null) throw new ProtocolException("Unknown tag <" + sbuf + ">");

		return value.intValue() + endTagDelta;
	}

	private boolean isTagChar(int ch) {
		return (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0'
				&& ch <= '9' || ch == ':' || ch == '-');
	}
	
	private int skipWhitespace() {
		int ch = read();
		for (; ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r'; ch = read()) {}
		return ch;
	}

	private int read() {
		if (peek >= 0) {
			int value = peek;
			peek = -1;
			return value;
		}
		try {
			return in.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ProtocolException expectedChar(String expect, int ch) {
		if (ch < 0)
			return new ProtocolException("expected " + expect + " at end of file");
		else
			return new ProtocolException("expected " + expect + " at " + (char) ch);
	}

	static {
		tagCache = new HashMap();
		tagCache.put(ProtocalTag.TAG_NULL, new Integer(TAG_NULL));
		tagCache.put(ProtocalTag.TAG_BOOLEAN, new Integer(TAG_BOOLEAN));
		tagCache.put(ProtocalTag.TAG_INT, new Integer(TAG_INT));
		tagCache.put(ProtocalTag.TAG_LONG, new Integer(TAG_LONG));
		tagCache.put(ProtocalTag.TAG_DOUBLE, new Integer(TAG_DOUBLE));
		tagCache.put(ProtocalTag.TAG_DATE, new Integer(TAG_DATE));
		tagCache.put(ProtocalTag.TAG_STRING, new Integer(TAG_STRING));
		tagCache.put(ProtocalTag.TAG_MAP, new Integer(TAG_MAP));
		tagCache.put(ProtocalTag.TAG_LIST, new Integer(TAG_LIST));
		tagCache.put(ProtocalTag.TAG_TYPE, new Integer(TAG_TYPE));
		tagCache.put(ProtocalTag.TAG_LENGTH, new Integer(TAG_LENGTH));
		tagCache.put(ProtocalTag.TAG_REF, new Integer(TAG_REF));
		tagCache.put(ProtocalTag.TAG_CALL, new Integer(TAG_CALL));
		tagCache.put(ProtocalTag.TAG_FAULT, new Integer(TAG_FAULT));
		tagCache.put(ProtocalTag.TAG_METHOD, new Integer(TAG_METHOD));
	}
	
	class Tag {
		protected final int tag;
		protected final String value;
		public Tag(int tag, String value) {
			this.tag = tag;
			this.value = value;
		}
	}
}
