package net.buffalo.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

public class SynchronizableMap {
	
	public static class SessionMap extends HashMap implements Map {
		private static final long serialVersionUID = 8849613155483922952L;
		public Object put(Object key, Object value) {
			if (get(key) != null) return get(key);
			RequestContext.getContext().getHttpSession().setAttribute((String) key, value);
			return super.put(key, value);
		}
		public Object remove(Object key) {
			RequestContext.getContext().getHttpSession().removeAttribute((String) key);
			return super.remove(key);
		}
		
	}
	
	public static class ApplicationMap extends HashMap implements Map {
		private static final long serialVersionUID = -9080167602412829310L;

		public Object put(Object key, Object value) {
			if (get(key) != null) return get(key);
			RequestContext.getContext().getServletContext().setAttribute((String) key, value);
			return super.put(key, value);
		}
		
		public Object remove(Object key) {
			RequestContext.getContext().getServletContext().removeAttribute((String) key);
			return super.remove(key);
		}
	}
	
	public static class CookieMap extends HashMap implements Map {
		
		private static final long serialVersionUID = 701658626071273946L;
		
		public Object put(Object key, Object value) {
			if (get(key) != null) return get(key);
			if (!(value instanceof Cookie)) throw new IllegalArgumentException("Should be a cookie!");
 			RequestContext.getContext().getHttpResponse().addCookie((Cookie) value);
			return super.put(key, value);
		}
		
		public Object remove(Object key) {
			Cookie[] cookies = RequestContext.getContext().getHttpRequest().getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (cookie.getName().equals(key)) {
						cookie.setMaxAge(0);
						RequestContext.getContext().getHttpResponse().addCookie(cookie);
					}
				}
			}
			return super.remove(key);
		}
	}
}
