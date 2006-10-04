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
 * $Id: DefaultServiceRepositoryTest.java,v 1.1 2006/01/07 03:27:10 mechiland Exp $
 */ 
package net.buffalo.service;

import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import junit.framework.TestCase;

public class DefaultServiceRepositoryTest extends TestCase {
	
	String serivceProp = "/net/buffalo/service/buffalo-service.properties";
	String noSerivceProp = "/net/buffalo/service/buffalo-no-service.properties";
	
	DefaultServiceRepository dsr = null;
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(DefaultServiceRepositoryTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetFromProperties() {
		dsr = new DefaultServiceRepository(null, serivceProp);
		Object service = dsr.get("testDefaultService1");
		assertTrue(service instanceof TestBean);
		
		try {
			service = dsr.get("noSuchService");
		} catch (NoSuchServiceException ex) {
			assertTrue(ex.toString().indexOf("noSuchService") > -1);
		}
		
	}
	
	public void testGetFromSpring() throws Exception {
		MockServletContext sc = new MockServletContext("");
		XmlWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setServletContext(sc);
		wac.setConfigLocations(new String[]{"/net/buffalo/service/spring/applicationContext.xml"});
		wac.refresh();
		String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
		sc.setAttribute(key, wac);
		
		dsr = new DefaultServiceRepository(sc, noSerivceProp);
		
		Object service = dsr.get("testSpringService1");
		assertTrue(service instanceof TestBean);
	}
	
	public void testMixedGet() throws Exception {
		MockServletContext sc = new MockServletContext("");
		XmlWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setServletContext(sc);
		wac.setConfigLocations(new String[]{"/net/buffalo/service/spring/applicationContext.xml"});
		wac.refresh();
		String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
		sc.setAttribute(key, wac);
		
		dsr = new DefaultServiceRepository(sc, serivceProp);
		
		Object service = dsr.get("testSpringService1");
		assertTrue(service instanceof TestBean);
		
		service = dsr.get("testDefaultService2");
		assertTrue(service instanceof TestBean);
	}

}
