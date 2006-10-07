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
 * $Id: ComplexObjectTest.java,v 1.2 2006/10/01 16:10:46 mechiland Exp $
 */ 
package net.buffalo.protocal;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import net.buffalo.protocal.converters.DefaultConverterLookup;
import net.buffalo.protocal.converters.map.ObjectConverter;
import net.buffalo.protocal.io.DefaultMarshallingContext;
import net.buffalo.protocal.io.FastStreamWriter;
import net.buffalo.protocal.io.MarshallingContext;
import net.buffalo.protocal.io.StreamWriter;
import junit.framework.TestCase;

public class ComplexObjectTest extends TestCase {
	public void testAAA() throws Exception {
		ObjectConverter converter = new ObjectConverter();
		User user = new User("girl",null, "password", null, "Michael");
		List interests = new ArrayList();
		interests.add("Football");
		interests.add("Music");
		interests.add("Cook");
		user.setInterests(interests);
		user.setRoles(new String[]{"SA", "Architect"});
		
		StringWriter stringWriter = new StringWriter();
		StreamWriter streamWriter = new FastStreamWriter(stringWriter);
		MarshallingContext context = new DefaultMarshallingContext(new DefaultConverterLookup(), streamWriter);
		converter.marshal(user, context, streamWriter);
		assertEquals("<map><type>net.buffalo.protocal.User</type><string>username</string><string>Michael</string>" +
				"<string>password</string><string>password</string><string>gendor</string><string>girl</string>" +
				"<string>interests</string><list><type>java.util.ArrayList</type><length>3</length>" +
				"<string>Football</string><string>Music</string><string>Cook</string></list><string>roles</string>" +
				"<list><type>[string</type><length>2</length><string>SA</string><string>Architect</string></list></map>",
				stringWriter.getBuffer().toString());
	}
}
