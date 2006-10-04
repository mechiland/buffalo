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
 * $Id: JavaAPISearcher.java,v 1.1 2006/01/07 03:26:32 mechiland Exp $
 */ 
package net.buffalo.demo.ajax;

import java.util.ArrayList;

/**
 * The Java API Searcher Demo
 * @author michael
 */
public class JavaAPISearcher {
    
    private static String[] API_LIST = ClassDump.classesList();     
    
    public String[] getApiList() {
        return API_LIST;
    }
    
    public String[] filter(String input, int size) {
    	ArrayList filterList = new ArrayList();
        for (int i = 0; i < API_LIST.length; i++) {
        	if (i > size) break;
            String clazz = (String)API_LIST[i];
            if(clazz.startsWith(input) && !input.equals("")) {
				filterList.add(clazz);
            }
        }
        
        return (String[])filterList.toArray(new String[filterList.size()]);
    }
    
    
}
