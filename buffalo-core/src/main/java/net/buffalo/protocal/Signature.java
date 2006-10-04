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
 * $Id: Signature.java,v 1.3 2006/09/30 15:53:24 mechiland Exp $
 */ 
package net.buffalo.protocal;

public class Signature {
	
	private final Class objectType;
	private final String methodName;
	private final Class[] parameters;

	public Signature(Class objectType, String methodName, Class[] parameters) {
		this.objectType = objectType;
		this.methodName = methodName;
		this.parameters = parameters;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		
		Signature that = (Signature) obj;
		if (!this.objectType.equals(that.objectType))
			return false;
		if (!this.methodName.equals(that.methodName))
			return false;
		if (this.parameters.length != that.parameters.length)
			return false;
		
		for (int i = 0; i < parameters.length; i++) {
			if (!this.parameters[i].equals(that.parameters[i])) {
				return false;
			}
		}
		
		return true;
	}

	public int hashCode() {
		int hashCode=0;
		for (int i = 0; i < parameters.length; i++) {
			hashCode += parameters[i].hashCode();
		}
		return objectType.hashCode() + methodName.hashCode() + hashCode;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(objectType);
		buffer.append("(");
		for (int i = 0; i < parameters.length; i++) {
			buffer.append(parameters[i].getName());
			buffer.append(",");
		}
		buffer.append(")");
		return buffer.toString();
	}
	
	
}
