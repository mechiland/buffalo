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
 * $Id: ServiceRepository.java,v 1.1 2006/01/07 03:26:35 mechiland Exp $
 */ 
package net.buffalo.service;

/**
 * 
 * A service repository, hold all the service definitions.
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public interface ServiceRepository {
	
	/**
	 * The default service properties file location
	 */
	public static final String DEFAULT_SERVICES = "/buffalo-service.properties";
	
	/**
	 * The key for servlet context
	 */
	public static final String WEB_CONTEXT_KEY =  ServiceRepository.class + "_WEB_KEY";
	
	/**
	 * Register a service to repository
	 * 
	 * @param serviceId the service key
	 * @param serviceName the service name
	 * @param factoryId the factory id
	 */
	public void register(String serviceId, String serviceName , String factoryId);
	
	/**
	 * Get the service instance from repository
	 * @param serviceId the service key
	 * @return service instance
	 */
	public Object get(String serviceId);
	
}
