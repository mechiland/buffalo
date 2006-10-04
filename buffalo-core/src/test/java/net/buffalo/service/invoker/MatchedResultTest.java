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
 * $Id: MatchedResultTest.java,v 1.1 2006/09/30 15:53:24 mechiland Exp $
 */ 
package net.buffalo.service.invoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class MatchedResultTest extends TestCase {
	public void testSortAlgrithm() throws Exception {
		MatchedResult matchedResult1 = new MatchedResult(1,null);
		MatchedResult matchedResult2 = new MatchedResult(10,null);
		
		List l = new ArrayList();
		l.add(matchedResult1);
		l.add(matchedResult2);
		
		Collections.sort(l);
		
		assertEquals(10, ((MatchedResult)l.get(0)).getWeight());
		
	}
}
