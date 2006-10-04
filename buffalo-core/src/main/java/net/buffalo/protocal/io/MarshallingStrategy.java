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
 * $Id: MarshallingStrategy.java,v 1.1 2006/09/29 16:01:35 mechiland Exp $
 */ 
package net.buffalo.protocal.io;

import net.buffalo.protocal.BuffaloCall;
import net.buffalo.protocal.converters.ConverterLookup;

public interface MarshallingStrategy {

	void marshal(Object obj, ConverterLookup converterLookup, StreamWriter streamWriter);

	BuffaloCall unmarshal(StreamReader reader, ConverterLookup converterLookup);
	
}
