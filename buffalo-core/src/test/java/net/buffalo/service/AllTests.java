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
 * $Id: AllTests.java,v 1.2 2006/03/15 15:26:21 mechiland Exp $
 */ 
package net.buffalo.service;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for net.buffalo.service");
		//$JUnit-BEGIN$
		suite.addTestSuite(ServiceRepositoryTest.class);
		suite.addTestSuite(DefaultServiceRepositoryTest.class);
		suite.addTestSuite(ServiceExceptionTest.class);
		//$JUnit-END$
		return suite;
	}

}
