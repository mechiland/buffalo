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
 * $Id: ValidationException.java,v 1.1 2006/01/07 03:26:26 mechiland Exp $
 */ 
package net.buffalo.request;

/**
 * 
 * Request worker validate exception.
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -4841190721418212896L;

	public ValidationException() {
		super();
		
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ValidationException(String message) {
		super(message);
		
	}

	public ValidationException(Throwable cause) {
		super(cause);
		
	}
	
}
