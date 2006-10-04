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
 * $Id: MatchedResult.java,v 1.1 2006/09/30 15:53:23 mechiland Exp $
 */ 
package net.buffalo.service.invoker;

import java.lang.reflect.Method;

public class MatchedResult implements Comparable {
	private final int weight;
	private final Method method;

	public MatchedResult(int weight, Method method) {
		this.weight = weight;
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}

	public int getWeight() {
		return weight;
	}

	public int compareTo(Object o) {
		MatchedResult that = (MatchedResult) o;
		if (this.weight > that.weight) {
			return -1;
		} else if (this.weight < that.weight) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
