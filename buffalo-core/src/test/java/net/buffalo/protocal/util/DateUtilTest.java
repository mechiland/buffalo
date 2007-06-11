/*
 * Copyright 2004-2007 Michael Chen and buffalo contributors.
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
 * $Id: DateUtilTest.java $
 */ 

package net.buffalo.protocal.util;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class DateUtilTest extends TestCase {
    
    public void testConvertToUTCString() throws Exception {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2007, 5, 1, 14, 23, 20);
        Date day = c.getTime();
        assertEquals("20070601T142320Z", DateUtil.toUTCString(day.getTime()));
    }
    
    public void testConvertToDate() throws Exception {
        String date = "20061231T095720Z";
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(2006, 11, 31, 9, 57, 20);
        assertEquals(c.getTime(), DateUtil.fromUTCString(date));
    }
    
}
