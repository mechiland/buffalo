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
 * $Id: RequestWorker.java,v 1.3 2006/09/30 08:45:40 mechiland Exp $
 */ 
package net.buffalo.request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A request worker to serve the specified request
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public interface RequestWorker {
	
	public void validate(HttpServletRequest request, HttpServletResponse response) throws ValidationException;

	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
