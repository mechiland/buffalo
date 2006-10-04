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
 * $Id: BuffaloServiceConfigurer.java,v 1.1 2006/01/07 03:26:37 mechiland Exp $
 */ 
package net.buffalo.service;

import java.util.Map;

/**
 * Config class for spring or other IoC containers. 
 * 
 * <p>In fact, this is a 'marker' bean, which tells buffalo how the services 
 * configed in the spring. Buffalo will check the spring context if there
 * are beans that has this type, and check the 'services' property.</p>
 * 
 * <p>Just put this bean in spring config xml: </p>
 * 
 *  <pre><code>
 *  &lt;bean name="buffaloConfigBean1" class="net.buffalo.service.spring.BuffaloServiceConfigurer"&gt;
		&lt;property name="services"&gt;
            &lt;map&gt;
                &lt;entry key="testSpringService1"&gt;
                    &lt;ref bean="testBean"/&gt;
                &lt;/entry&gt;
            &lt;/map&gt;
        &lt;/property&gt;
	&lt;/bean&gt;
 *  </code></pre>
 * <p>Buffalo will check the config automaticaly.</p>
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public class BuffaloServiceConfigurer {
	
	private Map services;
	
	/**
	 * Default cotr.
	 *
	 */
	public BuffaloServiceConfigurer() {
		
	}
	
	public BuffaloServiceConfigurer(Map services) {
		setServices(services);
	}
	
	/**
	 * Get the configed services
	 * @return configed services.
	 */
	public Map getServices() {
		return services;
	}
	
	/**
	 * set serivces. for IoC use.
	 * @param services 
	 */
	public void setServices(Map services) {
		this.services = services;
	}
	
	
}
