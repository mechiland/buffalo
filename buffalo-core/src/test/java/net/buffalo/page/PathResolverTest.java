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
 * $Id: PathResolverTest.java,v 1.1 2006/01/07 03:25:53 mechiland Exp $
 */ 
package net.buffalo.page;

import junit.framework.TestCase;
import net.buffalo.view.PathResolver;
import net.buffalo.view.impl.BeautifulPathResolver;

public class PathResolverTest extends TestCase  {
	
	String path1 = "/view/jsp//home//";
	String path2 = "/buffalo/loginService";
	String path3 = "/upload/forSomePath";
	
	PathResolver resolver = new BeautifulPathResolver(path1);
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(PathResolverTest.class);
	}

	/*
	 * Test method for 'net.buffalo.page.PathResolver.getRequestService()'
	 */
	public void testGetRequestService() {
		String reqService = resolver.getRequestService();
		assertEquals("The service ", "view", reqService);
	}

	/*
	 * Test method for 'net.buffalo.page.PathResolver.getParameters()'
	 */
	public void testGetParameters() {
		String[] params = resolver.getParameters();
		assertEquals("The total length of params", 2, params.length);
		assertEquals("Param #0", "jsp", params[0]);
		assertEquals("Param #1", "home", params[1]);
	}

}
