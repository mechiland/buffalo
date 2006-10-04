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
 * $Id: ServiceHolder.java,v 1.1 2006/01/07 03:27:17 mechiland Exp $
 */ 
package net.buffalo.service.defaults;

/**
 * Service holder, just hold the service infomation
 * 
 * @author michael
 *
 */
public final class ServiceHolder {
	
	private String serviceId;
	private String serviceName;
	private String factoryId;
	
	/**
	 * create a service holder
	 * 
	 * @param serviceId the service id
	 * @param serviceName the service name
	 * @param factoryId the factory id, such as 'default' or 'spring'
	 */
	public ServiceHolder(String serviceId, String serviceName, String factoryId) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.factoryId = factoryId;
	}
	
	public String getFactoryId() {
		return factoryId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(super.toString());
		buffer.append("{serviceId=");
		buffer.append(this.serviceId);
		buffer.append(",serviceName=");
		buffer.append(this.serviceName);
		buffer.append(",factoryId=");
		buffer.append(this.factoryId);
		buffer.append("}");
		
		return buffer.toString();
	}
	
}
