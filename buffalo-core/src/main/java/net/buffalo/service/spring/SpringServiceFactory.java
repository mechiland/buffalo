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
 * $Id: SpringServiceFactory.java,v 1.1 2006/01/07 03:27:02 mechiland Exp $
 */ 
package net.buffalo.service.spring;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.buffalo.service.BuffaloServiceConfigurer;
import net.buffalo.service.NoSuchServiceException;
import net.buffalo.service.ServiceCreationFailException;
import net.buffalo.service.ServiceFactory;

/**
 * Create service by spring.
 * 
 * @author michael
 *
 */
public class SpringServiceFactory implements ServiceFactory {

	private ServletContext context;
	
	/**
	 * Construct the instance.
	 * 
	 * @param ctx the servlet context
	 */
	public SpringServiceFactory(ServletContext ctx) {
		this.context = ctx;
	}
	
	/**
	 * return serivce via the service id and the service name.
	 */
	public Object getService(String serviceId, String serviceName) throws 
							NoSuchServiceException, ServiceCreationFailException {
		
		WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(this.context);
		
		BuffaloServiceConfigurer config = (BuffaloServiceConfigurer) (appCtx.getBean(serviceName));
		
		Object instance = config.getServices().get(serviceId);
		
		if (instance == null) {
			throw new NoSuchServiceException("In configBean '" + serviceName + 
					"', no service named '" + serviceId + "' configed.");
		}
		
		return instance;
		
	}

}
