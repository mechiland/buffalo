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
 * $Id: RequestContext.java,v 1.2 2006/09/07 07:49:10 mechiland Exp $
 */ 
package net.buffalo.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestContext {
	
	public static final String SESSION = "net.buffalo.request.RequestContext.session";
	public static final String APPLICATION = "net.buffalo.request.RequestContext.application";
	public static final String COOKIE = "net.buffalo.request.RequestContext.cookie";
	public static final String PARAMETER = "net.buffalo.request.RequestContext.parameter";
	
	public static final String HTTP_REQUEST = "net.buffalo.request.RequestContext.http_request";
	public static final String HTTP_RESPONSE = "net.buffalo.request.RequestContext.http_response";
	public static final String HTTP_SESSION = "net.buffalo.request.RequestContext.http_session";
	public static final String SERVLET_CONTEXT = "net.buffalo.request.RequestContext.servlet_context";
	
	private static ThreadLocal requestContext = new RequestContextThreadLocal();
	
	private Map context;
	
	public RequestContext(Map context) {
		this.context = context;
	}
	
	public void setSession(Map sessionMap) {
		put(SESSION, sessionMap);
	}
	
	public Map getSession() {
		return (Map)context.get(SESSION);
	}
	
	public void setCookie(Map cookieMap) {
		put(COOKIE, cookieMap);
	}
	
	public Map getCookie() {
		return (Map)context.get(COOKIE);
	}
	
	public void setParameter(Map parameterMap) {
		put(PARAMETER, parameterMap);
	}
	
	public Map getParameter() {
		return (Map) context.get(PARAMETER);
	}
	
	public void setHttpRequest(HttpServletRequest request) {
		put(HTTP_REQUEST, request);
	}
	
	public HttpServletRequest getHttpRequest() {
		return (HttpServletRequest)context.get(HTTP_REQUEST);
	}
	
	public void setServletContext(ServletContext servletContext) {
		put(SERVLET_CONTEXT, servletContext);
	}
	
	public ServletContext getServletContext() {
		return (ServletContext) context.get(SERVLET_CONTEXT);
	}
	
	public void setHttpResponse(HttpServletResponse response) {
		put(HTTP_RESPONSE, response);
	}
	
	public HttpServletResponse getHttpResponse() {
		return (HttpServletResponse) context.get(HTTP_RESPONSE);
	}
	
	public HttpSession getHttpSession() {
		return getHttpRequest().getSession(true);
	}
	
	public void setApplication(Map applicationMap) {
		put(APPLICATION, applicationMap);
	}
	
	public Map getApplication() {
		return (Map) context.get(APPLICATION);
	}
	
	private void put(String key, Object value) {
		context.put(key, value);		
	}

	private static class RequestContextThreadLocal extends ThreadLocal {
		protected Object initialValue() {
			return new RequestContext(new HashMap());
		}
	}
	
	public static RequestContext getContext() {
		
		RequestContext context = (RequestContext) requestContext.get();
		
		if (context == null) {
			context = new RequestContext(new HashMap());
			setContext(context);
		}
		
		return context;
	}

	public static void setContext(RequestContext context) {
		requestContext.set(context);
	}
}
