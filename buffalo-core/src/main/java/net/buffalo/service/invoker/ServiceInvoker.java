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
 * $Id: ServiceInvoker.java,v 1.2 2006/09/30 08:45:41 mechiland Exp $
 */
package net.buffalo.service.invoker;

import java.io.Reader;
import java.io.Writer;

/**
 * buffalo service invoker.
 * 
 * @author michael
 * @since 1.2alpha2
 */
public interface ServiceInvoker {

	/**
	 * Invoke the object with the request from the input stream.
	 * @param service the service to invoke
	 * @param reader the input reader
	 * @param writer the output writer
	 */
	public void invoke(Object service, Reader reader, Writer writer) throws Throwable;
	
}
