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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestContextUtil {
	
	public static void createRequestContext(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) {
		RequestContext context = new RequestContext(new HashMap());
		context.setApplication(createApplicationMap(servletContext));
		context.setSession(createSessionMap(request));
		context.setCookie(createCookieMap(request));
		context.setParameter(request.getParameterMap());
		
		context.setHttpRequest(request);
		context.setHttpResponse(response);
		context.setServletContext(servletContext);
		
		RequestContext.setContext(context);
	}

	private static Map createCookieMap(HttpServletRequest request) {
		Map map = new HashMap();
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null) return map;
 		
		for (int i = 0; i < cookies.length; i++) {
			map.put(cookies[i].getName(), cookies[i]);
		}
		
		return map;
	}

	private static Map createSessionMap(HttpServletRequest request) {
		Map map = new HashMap();
		
		HttpSession httpSession = request.getSession(true);
		Enumeration attributeNames = httpSession.getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			String attr = (String) attributeNames.nextElement();
			map.put(attr, httpSession.getAttribute(attr));
		}
		
		return map;
	}

	private static Map createApplicationMap(ServletContext servletContext) {
		Map map = new HashMap();
		Enumeration attributeNames = servletContext.getAttributeNames();
		while(attributeNames.hasMoreElements()) {
			String attr = (String) attributeNames.nextElement();
			map.put(attr, servletContext.getAttribute(attr));
		}
		return map;
	}

	public static void updateRequestContext(ServletContext servletContext, 
								HttpServletRequest request, HttpServletResponse response) {
		RequestContext context = RequestContext.getContext();
		
		// application
		for (Iterator iter = context.getApplication().keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			servletContext.setAttribute(key, context.getApplication().get(key));
		}
		
		// session
		for (Iterator iter = context.getSession().keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			request.getSession().setAttribute(key, context.getSession().get(key));
		}
		
		// cookie
		for (Iterator iter = context.getCookie().keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (!cookies[i].getName().equals(key)) {
						response.addCookie((Cookie) context.getCookie().get(key));
					}
				}				
			}
		}
	}
}
