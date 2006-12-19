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
 * $Id: RequestContextUtil.java,v 1.2 2006/09/08 04:18:39 mechiland Exp $
 */ 
package net.buffalo.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestContextUtil {
	
	public static void createRequestContext(ServletContext servletContext, HttpServletRequest request, 
			HttpServletResponse response) {
		RequestContext context = new RequestContext(new HashMap());
		context.setServletContext(servletContext);
		context.setHttpRequest(request);
		context.setHttpResponse(response);
		RequestContext.setContext(context);
		context.setApplication(createApplicationMap(servletContext));
		context.setSession(createSessionMap(request));
		context.setCookie(createCookieMap(request));
		context.setParameter(request.getParameterMap());
	}

	private static Map createCookieMap(HttpServletRequest request) {
		SynchronizableMap.CookieMap map = new SynchronizableMap.CookieMap();
		Cookie[] cookies = request.getCookies();
		map.copyCookies(cookies);
		return map;
	}

	private static Map createSessionMap(HttpServletRequest request) {
		SynchronizableMap.SessionMap map = new SynchronizableMap.SessionMap();
		HttpSession httpSession = request.getSession();
		if (httpSession == null) {
			httpSession = request.getSession(true);
		}
		map.copySessionVariables(httpSession);
		return map;
	}

	private static Map createApplicationMap(ServletContext servletContext) {
		SynchronizableMap.ApplicationMap map = new SynchronizableMap.ApplicationMap();
		map.copyServletContextVariables(servletContext);
		return map;
	}

}
