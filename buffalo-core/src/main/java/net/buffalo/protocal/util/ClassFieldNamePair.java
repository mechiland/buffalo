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
 * $Id: ClassFieldNamePair.java,v 1.1 2006/10/01 13:58:20 mechiland Exp $
 */
package net.buffalo.protocal.util;

public class ClassFieldNamePair {
	private final Class type;
	private final String fieldName;

	public ClassFieldNamePair(Class type, String fieldName) {
		this.type = type;
		this.fieldName = fieldName;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		ClassFieldNamePair that = (ClassFieldNamePair) obj;

		return this.type.equals(that.type)
				&& this.fieldName.equals(that.fieldName);
	}

	public int hashCode() {
		return this.type.hashCode() + this.fieldName.hashCode();
	}
}
