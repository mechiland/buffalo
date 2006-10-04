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
 */ 
package net.buffalo.demo.ajax;

import java.util.LinkedList;
import java.util.List;

/**
 */
public class J2EEAjaxService {
    
    public static final int API_SIZE = 20;
    
    public List filterEmployees(String id) {
        List filteredEmployees = new LinkedList();
        List employees = EmployeeBean.employees();
        
        id = id.toLowerCase();
        for (int i = 0; i < employees.size(); i++) {
            EmployeeBean employeebean = (EmployeeBean)employees.get(i);
            if((employeebean.getFirstName().toLowerCase().startsWith(id) 
                    || employeebean.getLastName().toLowerCase().startsWith(id))
                    && !id.equals("")) {
                filteredEmployees.add(employeebean);
            }

        }
        
        return filteredEmployees;
    }
    
    public String[] filterJavaAPI(String input, int size) {
    	
        String[] list = new JavaAPISearcher().filter(input, size);
        return list;
    }
    
}
