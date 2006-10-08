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

/**
 * This is for web use as using the {FastStreamReader} will cause encoding issue in Opera
 * 
 * @author michael
 *
 */
public class FastInputStreamReader extends FastStreamReader implements StreamReader {
	
	private final InputStream in;
	
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

	protected int readSingleChar() throws IOException {
		return in.read();
	}
	
}
