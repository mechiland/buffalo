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
 * $Id: ServiceCreationFailException.java,v 1.1 2006/01/07 03:26:37 mechiland Exp $
 */ 
package net.buffalo.service;

/**
 * Create failed when initialize the service.
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha1
 */
public class ServiceCreationFailException extends RuntimeException {

	private static final long serialVersionUID = -8751690886216464063L;

	public ServiceCreationFailException() {
		super();
	}

	public ServiceCreationFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceCreationFailException(String message) {
		super(message);
	}

	public ServiceCreationFailException(Throwable cause) {
		super(cause);
	}
	
}
