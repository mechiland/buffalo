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
 * $Id: DefaultServiceRepository.java,v 1.1 2006/01/07 03:26:38 mechiland Exp $
 */ 
package net.buffalo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.buffalo.service.defaults.DefaultServiceFactory;
import net.buffalo.service.defaults.ServiceHolder;
import net.buffalo.service.spring.SpringServiceFactory;
import net.buffalo.service.spring.SpringUtil;

/**
 * Default service repository implentation
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public class DefaultServiceRepository implements ServiceRepository {
	
	private static final Log LOG = LogFactory.getLog(DefaultServiceRepository.class);
	
	/**
	 * spring web package identifier
	 */
	private static final String SPRING_IDENTIFIER = "org.springframework.web.context.WebApplicationContext";
	
	private Map services = new HashMap();
	
	private ServiceFactory simpleFactory ;
	private ServiceFactory springFactory;
	private ServletContext context;
	private String serviceConfigProperties = DEFAULT_SERVICES;
	
	public DefaultServiceRepository(ServletContext ctx) {
		this(ctx, null);
	}
	
	public DefaultServiceRepository(ServletContext ctx, String defaultProperties) {
		simpleFactory = new DefaultServiceFactory();
		
		if (springAvailable()) {
			springFactory = new SpringServiceFactory(ctx);
		}
		
		this.context = ctx;
		
		if (defaultProperties != null) {
			this.serviceConfigProperties = defaultProperties;
		}
		
		initialize();
	}
	
	/**
	 * Get the service via service id
	 */
	public Object get(String serviceId) {
		Object serviceInstance = null;
		ServiceHolder s = (ServiceHolder) (services.get(serviceId));
		if (s == null) {
			throw new NoSuchServiceException(serviceId);
		}
		
		if (s.getFactoryId().equals(ServiceFactory.DEFAULT)) {
			serviceInstance = simpleFactory.getService(s.getServiceId(), s.getServiceName());
		} else if (s.getFactoryId().equals(ServiceFactory.SPRING)) {
			if (springAvailable()) {
				serviceInstance = springFactory.getService(s.getServiceId(), s.getServiceName());
			}
		} else {
			// ommited, should throw NoSuchServiceFactoryException
		}
		
		return serviceInstance;
	}
	
	/**
	 * register a service
	 */
	public void register(String serviceId, String serviceName, String factoryId) {
		services.put(serviceId, new ServiceHolder(serviceId, serviceName, factoryId));
	}
	
	/**
	 * register a service with default factory 
	 * @param serviceId
	 * @param serviceName
	 */
	public void register(String serviceId, String serviceName) {
		register(serviceId, serviceName, ServiceFactory.DEFAULT);
	}
	
	/**
	 * Register all services from a map 
	 * @param services map contains services(key - value)
	 * @param factoryId factory id
	 */
	public void registerAll(Map services, String factoryId) {
		Iterator iter = services.keySet().iterator();
        while(iter.hasNext()) {
            String name = (String)iter.next();
            String serviceImpl = (String)services.get(name);
            register(name, serviceImpl, factoryId);
        }
	}
	
	/**
	 * initialize the repository. 
	 *
	 */
	private void initialize() {
		//first, try to load the buffalo services from properties
		
		loadDefaultProperties();
		
		// try to load from Spring
		loadFromSpring();

	}
	
	/**
	 * Load services definition from default properties
	 *
	 */
	private void loadDefaultProperties() {
		InputStream is = this.getClass().getResourceAsStream(this.serviceConfigProperties);
		if(is != null) {
			LOG.info("Found properties config, load service from properties: " + this.serviceConfigProperties);
			Properties defaultProperties = new Properties();
			try {
				defaultProperties.load(is);
				registerAll(defaultProperties, ServiceFactory.DEFAULT);
			} catch (IOException e) {
				System.err.println("Error when reading " + DEFAULT_SERVICES + ", ommited");
				e.printStackTrace();
			}
			LOG.info("Load service from properties finished.");
		}
		
	}

	/**
	 * Load from spring config bean
	 *
	 */
	private void loadFromSpring() {
		
		if (this.context == null) return;
		if (!springAvailable()) return;
		
		LOG.info("Found spring, load services from spring");
		Map serviceNames = SpringUtil.getConfigedBeanNames(this.context);
		
		registerAll(serviceNames, ServiceFactory.SPRING);
		LOG.info("Load service from spring finished.");
	}
	
	/**
	 * Check if the spring web avaliable.
	 * @return true if available
	 */
	private boolean springAvailable() {
		boolean available = false;
		try {
			Class.forName(SPRING_IDENTIFIER);
			available = true;
		} catch (ClassNotFoundException e) {
			available = false;
		}
		
		return available;
		
	}
}
