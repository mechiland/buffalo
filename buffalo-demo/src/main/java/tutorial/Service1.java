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
package tutorial;

import java.math.BigDecimal;
import java.util.Map;

import net.buffalo.demo.simple.User;

/**
 * @author michael
 *
 * 
 */
public class Service1 {
    public String hello(String name) {
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello, " + name;
    }
    
    public BigDecimal big() {
        return new BigDecimal(12345678.9987876);
    }
    
    public Map hash(User m){
        System.out.println(m);
        
        return null;
    }
}
