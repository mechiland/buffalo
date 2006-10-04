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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.buffalo.request.RequestContext;
import net.buffalo.service.ServiceInvocationException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AllowOriginalHostOnlyInterceptor implements MethodInterceptor{
	
	private static final Log LOG = LogFactory.getLog(AllowOriginalHostOnlyInterceptor.class);
	
	public Object invoke(MethodInvocation method) throws Throwable {
		
		HttpServletRequest request = RequestContext.getContext().getHttpRequest();

		Cookie[] cookies = request.getCookies();
		
		if (cookies == null || cookies.length <=0) {
			LOG.warn("Some rantankerous user invoke this service, refused. " + method.getClass());
			throw new ServiceInvocationException("Permission error!");
		}
		
		return method.proceed();
	}
}
