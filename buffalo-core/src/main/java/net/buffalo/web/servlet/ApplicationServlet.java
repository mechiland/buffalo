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
 * $Id: ApplicationServlet.java,v 1.5 2006/10/01 13:58:25 mechiland Exp $
 */

package net.buffalo.web.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.request.RequestContext;
import net.buffalo.request.RequestContextUtil;
import net.buffalo.request.RequestWorker;
import net.buffalo.request.ValidationException;
import net.buffalo.service.BuffaloWorker;
import net.buffalo.service.DefaultServiceRepository;
import net.buffalo.service.ServiceRepository;
import net.buffalo.view.ViewWorker;
import net.buffalo.web.RequestUtils;
import net.buffalo.web.upload.UploadWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Buffalo application Servlet, the central servlet for all the requests.
 * 
 * @author michael
 * @version 1.2
 */
public class ApplicationServlet extends HttpServlet {

	private static final Log LOG = LogFactory.getLog(ApplicationServlet.class);

	private static final String LOCALE_COOKIE_NAME = "net.buffalo.web.locale";

	public String getServletInfo() {
		return "Buffalo Application Gateway Servlet";
	}

	/**
	 * Initialize the service, including the service object.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		initServiceRepository();
	}

	/**
	 * initialize the service repository
	 * 
	 */
	private void initServiceRepository() {
		
		if (getServletContext().getAttribute(ServiceRepository.WEB_CONTEXT_KEY) == null) {
			LOG.info("initialize the service repository");
			ServiceRepository repository = new DefaultServiceRepository(getServletContext());
			getServletContext().setAttribute(ServiceRepository.WEB_CONTEXT_KEY, repository);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestContextUtil.createRequestContext(getServletContext(), request, response);
		String pathInfo = request.getPathInfo();
		
		LOG.debug("request path info: " + pathInfo);
		RequestWorker worker = null;
		if (pathInfo == null || pathInfo.equals("/")) {
			worker = new ViewWorker();
		} else if (pathInfo.startsWith("/view/")) {
			worker = new ViewWorker();
		} else if (pathInfo.startsWith("/buffalo/")) {
			worker = new BuffaloWorker();
		} else if (pathInfo.startsWith("/upload/")) {
			worker = new UploadWorker();
		} else {
			throw new ServletException("Cannot find the request worker!");
		}
		
		try {
			worker.validate(request, response);
		} catch (ValidationException ex) {
			throw new ServletException("Service validation error", ex);
		}
		
		worker.processRequest(request, response);
		RequestContextUtil.updateRequestContext(getServletContext(), request, response);
	}

	protected Locale getLocaleFromRequest()
			throws ServletException {
		Cookie cookie = (Cookie) RequestContext.getContext().getCookie().get(LOCALE_COOKIE_NAME);

		if (cookie != null)
			return RequestUtils.getLocale(cookie.getValue());

		return RequestContext.getContext().getHttpRequest().getLocale();
	}
}