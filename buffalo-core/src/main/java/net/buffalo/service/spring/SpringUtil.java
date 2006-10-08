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
 * $Id: SpringUtils.java,v 1.1 2006/01/07 03:27:02 mechiland Exp $
 */ 
package net.buffalo.service.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import net.buffalo.service.BuffaloServiceConfigurer;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Helper class for spring service factory.
 * 
 * @author michael
 *
 */
public class SpringUtil {

	/**
	 * get configed buffalo bean names from servlet context 
	 * 
	 * @param context the servlet contex
	 * @return the map, with form servicdId - serviceBeanName
	 */
	public static Map getConfigedBeanNames(ServletContext context) {
		Map services = new HashMap();
		if (context == null) return null;
		WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(context);
		if (appCtx == null) {
			// the appContext does not exist.
			return services;
		}
		
		String[] buffaloConfigBeanNames = appCtx.getBeanNamesForType(BuffaloServiceConfigurer.class);
		
		for (int i = 0; i < buffaloConfigBeanNames.length; i++) {
			String beanName = buffaloConfigBeanNames[i];
			BuffaloServiceConfigurer configBean = (BuffaloServiceConfigurer)appCtx.getBean(beanName);
			Map singleServices = configBean.getServices();
			Iterator iter = singleServices.keySet().iterator();
			while (iter.hasNext()) {
				String serviceName = (String)iter.next();
				services.put(serviceName, beanName);
			}
			
		}
		
		return services;
	}

}
