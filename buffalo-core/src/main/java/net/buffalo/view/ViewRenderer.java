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
 * $Id: ViewRenderer.java,v 1.1 2006/01/07 03:27:14 mechiland Exp $
 */ 
package net.buffalo.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.view.impl.BeautifulPathResolver;

public class ViewRenderer {

	public void render(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String info = request.getPathInfo();
		PathResolver resolver = new BeautifulPathResolver(info);
		
		String service = resolver.getRequestService();
		String[] params = resolver.getParameters();
		
		if (service.equals("view")) {
			
			new JspViewRender().render(params, request, response);
		}
		
	}
}
