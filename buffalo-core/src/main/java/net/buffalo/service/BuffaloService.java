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
 * $Id: BuffaloService.java,v 1.3 2006/09/08 05:04:31 mechiland Exp $
 */ 
package net.buffalo.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

/**
 * Buffalo Service is a service for those service want to get information 
 * from ServletContext or ServletRequest. See the number guess demo 
 * for a demostration. 
 * 
 * A common java bean (with an constructor for no argument) 
 * is good for most case.
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 * @deprecated since 1.2.4, if you want to get the context information, please use RequestContext.getContext()
 */
public class BuffaloService {
	
	/**
	 * Get the request context.
	 * @return request context
	 */
	public ServletContext getContext() {
		return RequestContext.getContext().getServletContext();
	}
	
	/**
	 * Get the http servlet request
	 * @return the HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return RequestContext.getContext().getHttpRequest();
	}
	
	/**
	 * Get the http session object for your persistence usage.
	 * @return http session object
	 */
	public HttpSession getSession() {
		return RequestContext.getContext().getHttpSession();
	}
	
}
