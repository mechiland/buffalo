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
 * $Id: RequestContextTest.java,v 1.2 2006/09/30 12:32:18 mechiland Exp $
 */ 
package net.buffalo.request;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

public class RequestContextTest extends TestCase {
	
	private MockServletContext mockServletContext;
	private MockHttpServletRequest mockRequest;
	private HttpSession mockHttpSession;
	private MockHttpServletResponse mockResponse;

	protected void setUp() throws Exception {
		super.setUp();
		mockServletContext = new MockServletContext();
		mockRequest = new MockHttpServletRequest(mockServletContext);
		mockHttpSession = mockRequest.getSession();
		mockResponse = new MockHttpServletResponse();
	}
	
	public void testShouldGetRequestContextHolder() throws Exception {
		RequestContext context = new RequestContext(new HashMap());
		RequestContext.setContext(context);
		assertEquals(context, RequestContext.getContext());
	}
	
	public void testShouldGetTheMapWhenCreateUsingUtil() throws Exception {

		Cookie cookie = new Cookie("c_name", "c_value");
		mockServletContext.setAttribute("a", "appValue");
		mockRequest.addParameter("parameterName", "parameterValue");
		mockRequest.setCookies(new Cookie[]{cookie});
		mockHttpSession.setAttribute("s", "sessionValue");
		
		RequestContextUtil.createRequestContext(mockServletContext,	mockRequest, mockResponse);
		RequestContext context = RequestContext.getContext();
		
		assertNotNull(context);
		assertEquals(mockServletContext, context.getServletContext());
		assertEquals("appValue", context.getApplication().get("a"));
		assertEquals(1, context.getSession().size());
		assertEquals("sessionValue", context.getSession().get("s"));
		assertEquals(cookie, RequestContext.getContext().getCookie().get("c_name"));
		
		assertEquals(mockRequest, context.getHttpRequest());
		assertEquals(mockHttpSession, context.getHttpSession());
		assertEquals(1, context.getParameter().size());
		assertEquals(mockResponse, context.getHttpResponse());
	}
	
	public void testUpdateRequestVariables() throws Exception {
		RequestContextUtil.createRequestContext(mockServletContext,	mockRequest, mockResponse);
		RequestContext context = RequestContext.getContext();
		context.getSession().put("s_key", "s_value");
		assertEquals("s_value", mockHttpSession.getAttribute("s_key"));
		context.getApplication().put("a_key", "a_value");
		assertEquals("a_value", mockServletContext.getAttribute("a_key"));
		Cookie cookie = new Cookie("c_name", "c_value");
		context.getCookie().put(cookie.getName(), cookie);
		assertEquals(cookie, mockResponse.getCookies()[0]);
	}
	
	public void testShouldDeleteContextValueWhenDeleteFromMap() throws Exception {
		mockRequest.setCookies(new Cookie[]{new Cookie("cookie", "cookieValue")});
		RequestContextUtil.createRequestContext(mockServletContext, mockRequest, mockResponse);
		RequestContext.getContext().getSession().put("name", "value");
		RequestContext.getContext().getApplication().put("app", "appValue");
		
		assertEquals("value", RequestContext.getContext().getSession().get("name"));
		assertEquals("value", mockRequest.getSession().getAttribute("name"));
		RequestContext.getContext().getSession().remove("name");
		assertNull(RequestContext.getContext().getSession().get("name"));
		assertNull(mockRequest.getSession().getAttribute("name"));
		
		RequestContext.getContext().getCookie().remove("cookie");
		RequestContext.getContext().getApplication().remove("app");
		
		assertNull(RequestContext.getContext().getApplication().get("app"));
		assertNull(mockServletContext.getAttribute("app"));
		assertNull(RequestContext.getContext().getCookie().get("cookie"));
		Cookie cookie = mockResponse.getCookies()[0];
		assertEquals(0, cookie.getMaxAge());
	}
	
	public void testShouldRequestTheSameSession() throws Exception {
		RequestContextUtil.createRequestContext(mockServletContext, mockRequest, mockResponse);
		HttpSession session = RequestContext.getContext().getHttpSession();
		assertTrue(mockRequest.getSession() == session);
	}
}
