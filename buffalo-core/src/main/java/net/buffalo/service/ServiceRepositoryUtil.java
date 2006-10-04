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
 * $Id: ServiceRepositoryUtil.java,v 1.1 2006/01/07 03:26:39 mechiland Exp $
 */ 
package net.buffalo.service;

import javax.servlet.ServletContext;

/**
 * Helper class to handle repository
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public class ServiceRepositoryUtil {
	
	/**
	 * Get service repository from servlet context, if any
	 * @param context the servlet context
	 * @return the repository stored in the servlet context if any
	 */
	public static ServiceRepository getServiceRepository(ServletContext context) {
		Object rep = context.getAttribute(ServiceRepository.WEB_CONTEXT_KEY);
		if (rep == null) {
			return null;
		}
		if (!(rep instanceof ServiceRepository)) {
			throw new IllegalStateException("context attribute is not of type : ServiceRepository" + rep);
		} 
		
		return (ServiceRepository)rep;
	}
}
