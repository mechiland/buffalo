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
 * $Id: BuffaloCall.java,v 1.6 2006/09/30 15:53:24 mechiland Exp $
 */ 
package net.buffalo.protocal;

public class BuffaloCall {

	private String methodName;
	private Object[] arguments;
	private Class[] argumentTypes;

	public BuffaloCall(String methodName, Object[] arguments) {
		this.methodName = methodName;
		this.arguments = arguments;
		
		Class[] types = new Class[this.arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			types[i] = arguments[i].getClass();
		}
		this.argumentTypes = types;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public Object[] getArguments() {
		return this.arguments;
	}
	
	public Class[] getArgumentTypes() {
		return argumentTypes;
	}
	
	public String toString() {
		if (arguments.length == 0) return methodName + "()";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arguments.length; i++) {
			buffer.append(", ");
			buffer.append(argumentTypes[i].getName()+"["+arguments[i]+"]");
		}
		return methodName+"("+buffer.toString().substring(2)+")";
	}
	
}
