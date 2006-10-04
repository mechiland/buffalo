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
 * $Id: Province.java,v 1.1 2006/01/07 03:26:51 mechiland Exp $
 */ 
package net.buffalo.demo.simple;

/**
 * @author michael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Province {
    private String name;
    private String[] cityList;
    public Province() {
        
    }
    
    public Province(String name, String cityList) {
        this.name = name;
        this.cityList = cityList.split("\\|");
    }
    
    
    /**
     * @return Returns the cityList.
     */
    public String[] getCityList() {
        return cityList;
    }
    /**
     * @param cityList The cityList to set.
     */
    public void setCityList(String[] cityList) {
        this.cityList = cityList;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
