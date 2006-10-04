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
 * $Id: BeautifulPathResolver.java,v 1.1 2006/01/07 03:27:42 mechiland Exp $
 */ 
package net.buffalo.view.impl;

import java.util.ArrayList;
import java.util.List;

import net.buffalo.view.PathResolver;

/**
 * Make a beautiful Path Resolver.
 * Beautiful path is like this: bfapp/view/jsp/home
 * 
 * @author michael
 *
 */
public class BeautifulPathResolver implements PathResolver {

	/**
	 * path seperator
	 */
	private static final String PARAMETER_DELIMETER = "/";
	
	private String path;
	
	private String requestService;
	private String[] parameters;
	
	public BeautifulPathResolver(String path) {
		if (path == null) throw 
			new IllegalArgumentException("The path should not be null");
		
		this.path = path;
		
		String[] splited = this.path.split(PARAMETER_DELIMETER);
		List wordList = new ArrayList();
		for (int i = 0; i < splited.length; i++) {
			String word = splited[i].trim();
			if (word.equals("") || 
					word.equals(PARAMETER_DELIMETER)) {
				continue;
			}
			wordList.add(word);
		}
		
		this.requestService = (String) (wordList.get(0));
		
		List paramsList = new ArrayList();
		for (int i = 1; i < wordList.size(); i++) {
			paramsList.add(wordList.get(i));
		}
		this.parameters = (String[])paramsList.toArray(
				new String[paramsList.size()]);
	}

	public String getRequestService() {
		return this.requestService;
	}

	public String[] getParameters() {
		return this.parameters;
	}

}
