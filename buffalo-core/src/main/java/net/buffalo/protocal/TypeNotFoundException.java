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
 * $Id: TypeNotFoundException.java,v 1.1 2006/08/03 09:09:55 mechiland Exp $
 */ 
package net.buffalo.protocal;

public class TypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6059732752519238966L;

	public TypeNotFoundException() {
		super();
	}

	public TypeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeNotFoundException(String message) {
		super(message);
	}

	public TypeNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
