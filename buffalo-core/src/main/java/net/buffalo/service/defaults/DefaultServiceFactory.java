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
 * $Id: DefaultServiceFactory.java,v 1.1 2006/01/07 03:27:17 mechiland Exp $
 */ 
package net.buffalo.service.defaults;

import net.buffalo.service.NoSuchServiceException;
import net.buffalo.service.ServiceCreationFailException;
import net.buffalo.service.ServiceFactory;

/**
 * Simple service factory, using jdk reflection only.
 * 
 * @author michael
 *
 */
public class DefaultServiceFactory implements ServiceFactory {
	
	/**
	 * Get the service instance. serviceName indentified by class name
	 * 
	 */
	public Object getService(String serviceId, String serviceName) 
						throws NoSuchServiceException, 
						ServiceCreationFailException {
		String serviceClass = serviceName;
		Object instance = null;
		try {
			instance = Class.forName(serviceClass).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ServiceCreationFailException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ServiceCreationFailException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new NoSuchServiceException(e);
		}
		
		return instance;
	}
	
}
