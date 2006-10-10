package net.buffalo.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

public class SynchronizableMap {
	
	public static class SessionMap extends HashMap implements Map {
		private static final long serialVersionUID = 8849613155483922952L;
		public Object put(Object key, Object value) {
			RequestContext.getContext().getHttpSession().setAttribute((String) key, value);
			return super.put(key, value);
		}
	}
	
	public static class ApplicationMap extends HashMap implements Map {
		private static final long serialVersionUID = -9080167602412829310L;

		public Object put(Object key, Object value) {
			RequestContext.getContext().getServletContext().setAttribute((String) key, value);
			return super.put(key, value);
		}
	}
	
	public static class CookieMap extends HashMap implements Map {
		private static final long serialVersionUID = 701658626071273946L;
		public Object put(Object key, Object value) {
			if (!(value instanceof Cookie)) throw new IllegalArgumentException("Should be a cookie!");
 			RequestContext.getContext().getHttpResponse().addCookie((Cookie) value);
			return super.put(key, value);
		}
	}
}