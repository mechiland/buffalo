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
 * $Id$
 */ 
package net.buffalo.service.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class GetWebRequestFilter implements Filter{
	private static ThreadLocal userThread = new ThreadLocal();

	public void init(FilterConfig arg0) throws ServletException {
		
	}

	public  void doFilter(ServletRequest request, ServletResponse arg1, FilterChain chain) 
				throws IOException, ServletException {
		userThread.set(request);
		chain.doFilter(request, arg1);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest)userThread.get();
	}

	public void destroy() {
		
	}
	
}
