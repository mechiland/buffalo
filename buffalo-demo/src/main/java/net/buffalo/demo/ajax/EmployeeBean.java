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

import java.util.ArrayList;
import java.util.List;

/**
 * @author michael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmployeeBean
{
    public static List employees = new ArrayList();
    public static List employees() {
        
        if (employees.size() > 0) return employees;
        
        employees.add(new EmployeeBean("1", "Greg", "Murray"));
        employees.add(new EmployeeBean("2", "Greg", "Murphy"));
        employees.add(new EmployeeBean("3", "George", "Murphy"));
        employees.add(new EmployeeBean("4", "George", "Murray"));
        employees.add(new EmployeeBean("5", "Peter", "Jones"));
        employees.add(new EmployeeBean("6", "Amber", "Jones"));
        employees.add(new EmployeeBean("7", "Amy", "Jones"));
        employees.add(new EmployeeBean("8", "Bee", "Jones"));
        employees.add(new EmployeeBean("9", "Beth", "Johnson"));
        employees.add(new EmployeeBean("10", "Cindy", "Johnson"));
        employees.add(new EmployeeBean("11", "Cindy", "Murphy"));
        employees.add(new EmployeeBean("12", "Duke", "Hazerd"));
        
        return employees;
    }

    public EmployeeBean(String s, String s1, String s2)
    {
        id = s;
        firstName = s1;
        lastName = s2;
    }

    public String getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    private String id;
    private String firstName;
    private String lastName;
}
