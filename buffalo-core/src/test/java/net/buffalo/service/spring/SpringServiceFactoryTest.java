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
 * $Id: SpringServiceFactoryTest.java,v 1.1 2006/01/07 03:27:41 mechiland Exp $
 */ 
package net.buffalo.service.spring;

import net.buffalo.service.NoSuchServiceException;
import net.buffalo.service.TestBean;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import junit.framework.TestCase;

public class SpringServiceFactoryTest extends TestCase {
	
	SpringServiceFactory ssf = null;
	
	protected void setUp() throws Exception {
		MockServletContext sc = new MockServletContext("");
		XmlWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setServletContext(sc);
		wac.setConfigLocations(new String[]{"/net/buffalo/service/spring/applicationContext.xml"});
		wac.refresh();
		String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
		sc.setAttribute(key, wac);
		
		ssf = new SpringServiceFactory(sc);
		
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(SpringServiceFactoryTest.class);
	}

	public void testGetService() {
		Object service = ssf.getService("testSpringService1", "buffaloConfigBean1");
		assertTrue(service instanceof TestBean);
	}
	
	public void testGetServiceThrowException() {
		try {
			ssf.getService("noSuchService", "buffaloConfigBean1");
			fail("shoule throw NoSuchServiceException");
		} catch (NoSuchServiceException ex) {
			assertTrue(ex.toString().indexOf("noSuchService") > -1);
		}
	}

}
