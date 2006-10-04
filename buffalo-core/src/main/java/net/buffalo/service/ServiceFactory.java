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
 * $Id: ServiceFactory.java,v 1.1 2006/01/07 03:26:36 mechiland Exp $
 */ 
package net.buffalo.service;

/**
 * Make a service factory to handle the service creation.
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public interface ServiceFactory {
	
	/**
	 * Default implementation
	 */
	public static final String DEFAULT = "default";
	
	/**
	 * Spring implementation
	 */
	public static final String SPRING = "spring";	
	
	/**
	 * return a service instance based on the serviceId and the service name. 
	 * 
	 * @param serviceId the service key, such a loginService, dataService...
	 * @param serviceName the name, may be className by the default implementation, 
	 *        and the spring config bean name by the spring implementation
	 * @return service instance
	 * @throws NoSuchServiceException if serivice id not found
	 * @throws ServiceCreationFailException if service creation failed
	 */
	public Object getService(String serviceId, String serviceName) 
			throws NoSuchServiceException, ServiceCreationFailException;
}
