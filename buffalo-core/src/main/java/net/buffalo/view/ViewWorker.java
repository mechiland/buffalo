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
 * $Id: ViewWorker.java,v 1.2 2006/09/30 08:45:43 mechiland Exp $
 */ 
package net.buffalo.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.request.AbstractRequestWorker;
import net.buffalo.request.RequestWorker;
import net.buffalo.request.ValidationException;

/**
 * View worker, deal with the view request. 
 * This is also the entry of a tiny web framework.
 * 
 * @author michael
 * @version 1.2
 */
public class ViewWorker extends AbstractRequestWorker implements RequestWorker {

	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		throw new UnsupportedOperationException("This worker has not been implemented yet!");
	}

	public void validate(HttpServletRequest request, HttpServletResponse response) throws ValidationException {
		
	}

}
