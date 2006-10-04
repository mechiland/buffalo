/*
 * Copyright 2002-2004 Michael Chen (mechiland at gmail dot com).
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
 * $Id: AbstractRequestWorker.java,v 1.3 2006/09/30 08:45:40 mechiland Exp $
 */ 
package net.buffalo.request;


/**
 * 
 * Abstract request worker, deal with the common tasks.
 * 
 * @author michael
 * @version 1.2
 * @since 1.2alpha2
 */
public abstract class AbstractRequestWorker implements RequestWorker {
	
	/**
	 * return the request worker relative path, for the worker use.
	 * For example, client send a request: /appRoot/bfapp/buffalo/simpleService, 
	 * the relativePath is: simpleService
	 * 
	 * @return the relative path
	 */
	protected String getWorkerRelativePath() {
		
		String pathInfo = RequestContext.getContext().getHttpRequest().getPathInfo();
		String[] terms = pathInfo.split("/");
		String prefix = "/" + terms[1]+"/";
		
		String relative = pathInfo.substring(prefix.length());
		
		return relative;		
		
	}
	
	/**
	 * returns the worker name.
	 * @return the worker name, may be 'buffalo', 'view' or 'upload'
	 */
	protected String getWorkerName() {
		
		String pathInfo = RequestContext.getContext().getHttpRequest().getPathInfo();
		
		String[] terms = pathInfo.split("/");
		
		return terms[1];
		
	}
	
	/**
	 * Get request parameter
	 * @param paramName the param name
	 * @return the parameter value
	 */
	protected String getParameter(String paramName) {
		return (String) RequestContext.getContext().getParameter().get(paramName);
	}

}
