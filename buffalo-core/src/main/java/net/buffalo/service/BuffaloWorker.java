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
 * $Id: BuffaloWorker.java,v 1.10 2006/09/30 12:06:28 mechiland Exp $
 */ 
package net.buffalo.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.request.AbstractRequestWorker;
import net.buffalo.request.RequestContext;
import net.buffalo.request.RequestWorker;
import net.buffalo.request.ValidationException;
import net.buffalo.service.invoker.BuffaloInvoker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Buffalo worker, deal with the buffalo call
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public class BuffaloWorker extends AbstractRequestWorker implements RequestWorker {
	
	private static final Log LOGGER = LogFactory.getLog(BuffaloWorker.class);
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoking buffalo worker");
		}
		
		String requestService = getWorkerRelativePath();
		ServiceRepository repository = ServiceRepositoryUtil.getServiceRepository(
				RequestContext.getContext().getServletContext());
		
		Object service = repository.get(requestService);
		response.setHeader("content-type", "text/xml;charset=utf-8");
		try {
			BuffaloInvoker.getInstance().invoke(service, request.getInputStream(), response.getWriter());
		} catch (Throwable ex) {
			LOGGER.error("An exception occured when invoking a service: ", ex);
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			StringBuffer faultString = new StringBuffer();
			faultString.append("An exception occured when invoking a service. \n");
			faultString.append(writer.toString());
			throw new ServiceInvocationException(faultString.toString(), ex);
		}
	}
	
	public void validate() throws ValidationException {
		if (!RequestContext.getContext().getHttpRequest().getMethod().equals("POST")) {
			throw new ValidationException("Buffalo worker support POST only!");
		}
	}

	public void validate(HttpServletRequest request, HttpServletResponse response) throws ValidationException {
		if (!request.getMethod().equals("POST")) {
			throw new ValidationException("Buffalo worker support POST only!");
		}
	}

}
