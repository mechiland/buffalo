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
 * $Id: Page.java,v 1.1 2006/01/07 03:27:37 mechiland Exp $
 */ 
package net.buffalo.demo.page;

/**
 * @author michael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Page {
    private String script;
    private String html;
    
    public Page(){}
    
    /**
     * @param script
     * @param html
     */
    public Page(String script, String html) {
        super();
        this.script = script;
        this.html = html;
    }
    /**
     * @return Returns the html.
     */
    public String getHtml() {
        return html;
    }
    /**
     * @param html The html to set.
     */
    public void setHtml(String html) {
        this.html = html;
    }
    /**
     * @return Returns the script.
     */
    public String getScript() {
        return script;
    }
    /**
     * @param script The script to set.
     */
    public void setScript(String script) {
        this.script = script;
    }
}
