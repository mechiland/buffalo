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
 * $Id: BuffaloInvoker.java,v 1.6 2006/09/30 15:53:23 mechiland Exp $
 */
package net.buffalo.service.invoker;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.protocal.BuffaloCall;
import net.buffalo.protocal.BuffaloProtocal;
import net.buffalo.protocal.Signature;
import net.buffalo.service.ServiceInvocationException;

public class BuffaloInvoker implements ServiceInvoker {
	
	private Map methodCache;
	private static BuffaloInvoker instance;
	
	private BuffaloInvoker() {
		this.methodCache = new HashMap();
	}
	
	public static BuffaloInvoker getInstance() {
		if (instance == null) {
			instance = new BuffaloInvoker();
		}
		return instance;
	}
	
	/**
	 * Invoke the service. Note: this method is for appserver
	 * @param service
	 * @param inputStream
	 * @param outputStream
	 */
	public void invoke(Object service, InputStream inputStream, Writer writer) {
		BuffaloCall call = BuffaloProtocal.getInstance().unmarshall(inputStream);
		Signature signature = new Signature(service.getClass(), 
				call.getMethodName(), call.getArgumentTypes());
		
		Method method = null;
		if (methodCache.get(signature) != null) {
			method = (Method) methodCache.get(signature);
		} else {
			method = lookupMethod(service, call, signature);
		}

		if (method == null) {
			throw new ServiceInvocationException(("cannot find the method " + call + " for " 
					+ service.getClass().getName()));
		}

		Object result = null;
		try {
			result = method.invoke(service, call.getArguments());
		} catch (IllegalArgumentException e) {
			throw new ServiceInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new ServiceInvocationException(e);
		} catch (InvocationTargetException e) {
			result = e.getTargetException();
		}

		BuffaloProtocal.getInstance().marshall(result, writer);
	}
	
	public void invoke(Object service, Reader reader, Writer writer) {
		BuffaloCall call = BuffaloProtocal.getInstance().unmarshall(reader);
		Signature signature = new Signature(service.getClass(), 
				call.getMethodName(), call.getArgumentTypes());
		
		Method method = null;
		if (methodCache.get(signature) != null) {
			method = (Method) methodCache.get(signature);
		} else {
			method = lookupMethod(service, call, signature);
		}

		if (method == null) {
			throw new ServiceInvocationException(("cannot find the method " + call + " for " 
					+ service.getClass().getName()));
		}

		Object result = null;
		try {
			result = method.invoke(service, call.getArguments());
		} catch (IllegalArgumentException e) {
			throw new ServiceInvocationException(e);
		} catch (IllegalAccessException e) {
			throw new ServiceInvocationException(e);
		} catch (InvocationTargetException e) {
			result = e.getTargetException();
		}

		BuffaloProtocal.getInstance().marshall(result, writer);
	}

	private Method lookupMethod(Object service, BuffaloCall call, Signature signature) {
		Method[] methods = service.getClass().getMethods();
		List matchedResults = new ArrayList();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().equals(call.getMethodName())) {
				Class[] parameterTypes = method.getParameterTypes();
				int matched = 0, weight=0;
				if (parameterTypes.length == call.getArguments().length) {
					for (int j = 0; j < parameterTypes.length; j++) {
						int matchWeight = parameterAssignable(parameterTypes[j], call.getArguments()[j].getClass());
						if (matchWeight > 0) {
							matched++;
							weight += matchWeight;
						}
					}
				}
				if (matched == parameterTypes.length) {
					methodCache.put(signature, method);
					matchedResults.add(new MatchedResult(weight,method));
				}
			}
		}
		
		if (matchedResults.size() == 0) {
			return null;
		} else if (matchedResults.size() == 1) {
			methodCache.put(signature, ((MatchedResult)matchedResults.get(0)).getMethod());
			return ((MatchedResult)matchedResults.get(0)).getMethod();
		} else {
			Collections.sort(matchedResults);
			methodCache.put(signature, ((MatchedResult)matchedResults.get(0)).getMethod());
			return ((MatchedResult)matchedResults.get(0)).getMethod();
		}
		
	}
	
	private int parameterAssignable(Class targetType, Class sourceType) {
		if (targetType.equals(sourceType)) return 6;
		if (targetType.isPrimitive()) {
			if (getWrapperClass(targetType).equals(sourceType)) return 5;
			else if (Number.class.isAssignableFrom(getWrapperClass(targetType)) && 
					 Number.class.isAssignableFrom(sourceType)) {
				return 4;
			}
		}
		if (targetType.isAssignableFrom(sourceType)) return 3;
		
		return 0;
	}

	private Class getWrapperClass(Class primitiveClass) {
		Map map = new HashMap();
		map.put(int.class, Integer.class);
		map.put(double.class, Double.class);
		return (Class) map.get(primitiveClass);
	}

}
